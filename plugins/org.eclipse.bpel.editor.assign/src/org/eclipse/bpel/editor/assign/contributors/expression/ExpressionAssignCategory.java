/*******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.editor.assign.contributors.expression;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.bpel.editor.assign.BPELAssignPlugin;
import org.eclipse.bpel.editor.assign.Messages;
import org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase;
import org.eclipse.bpel.editor.assign.contributors.CopyBound;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.util.BPELConstants;
import org.eclipse.bpel.ui.adapters.IVirtualCopyRuleSide;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorContentProvider;
import org.eclipse.bpel.ui.details.providers.ExpressionEditorDescriptorLabelProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.expressions.IExpressionEditor;
import org.eclipse.bpel.ui.extensions.BPELUIRegistry;
import org.eclipse.bpel.ui.extensions.ExpressionEditorDescriptor;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;


/**
 * TODO: when you first set the model object into the details page, it should query the
 * sections to find out which one thinks it "owns" the model object.  If NONE of them
 * claim to own it, then we will use whatever combo selection is stored in the transient
 * CopyExtension.  The same procedure is followed when refreshing the contents of the
 * page.
 * 
 * When you select a *different* category in the combo, we must update the value in the
 * CopyExtension.  We should also replace the existing Copy with an *empty* Copy.
 * 
 * Categories should become responsible for storing the value into the model themselves.
 */
public class ExpressionAssignCategory extends AssignCategoryBase {


	protected Composite composite;
	
	protected Composite fParent;

	private CopyBound bound;
	private Map<IExpressionEditor, Composite> editorsToComposite;
	
	private Expression exp;

	private ComboViewer languageSelector;
	private IExpressionEditor currentEditor = null;

	public ExpressionAssignCategory(CopyBound copyBound) {
		this.bound = copyBound;
		this.editorsToComposite = new HashMap<IExpressionEditor, Composite>();
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	public boolean isCategoryForModel ( EObject aModel ) {
		IVirtualCopyRuleSide side = BPELUtil.adapt(aModel, IVirtualCopyRuleSide.class);
		if (side != null) {
			return side.getExpression() != null;
		}		
		return false;
	}



	@Override
	public void setInput(EObject model) {
		if (bound == CopyBound.FROM) {
			exp = ((From)model).getExpression();
		} else {
			exp = ((To)model).getExpression();
		}
		try {
			if (exp.getExpressionLanguage() != null) {
				languageSelector.setSelection(new StructuredSelection(BPELUIRegistry.getInstance().getExpressionEditorDescriptor(exp.getExpressionLanguage())));
			}
			if (currentEditor != null) {
				currentEditor.setModelObject(exp);
				if (exp.getBody() != null) {
					currentEditor.setEditorContent(exp.getBody().toString());
				} else {
					currentEditor.setEditorContent("");
				}
			}
		} catch (Exception ex) {
			BPELAssignPlugin.log(ex, Status.ERROR);
		}
	}
	
	
	@Override
	protected final Composite createDetailsControl(final Composite parent, final FormToolkit tk) {
		Composite res = tk.createComposite(parent);
		res.setLayout(new GridLayout(2, false));
		tk.createLabel(res, Messages.language);
		languageSelector = new ComboViewer(res);
		languageSelector.setContentProvider(new ExpressionEditorDescriptorContentProvider());
		languageSelector.setLabelProvider(new ExpressionEditorDescriptorLabelProvider());
		languageSelector.setInput(new Object());
		final Composite editorContainer = tk.createComposite(res);
		editorContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		final StackLayout stackLayout = new StackLayout();
		editorContainer.setLayout(stackLayout);
		languageSelector.addPostSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				ExpressionEditorDescriptor desc = (ExpressionEditorDescriptor) ((StructuredSelection)event.getSelection()).getFirstElement();
				String language = (desc != null ? desc.getExpressionLanguage() : null);
				try {
					currentEditor = BPELUIRegistry.getInstance().getExpressionEditor(language);
				} catch (Exception ex) {
					BPELAssignPlugin.log(ex, Status.ERROR);
				}
				
				if (stackLayout.topControl != null) {
					stackLayout.topControl.setVisible(false);
					stackLayout.topControl = null;
				}
				
				if (currentEditor != null) {
					if (editorsToComposite.get(currentEditor) == null) {
						Composite composite = tk.createComposite(editorContainer);
						composite.setLayout(new FillLayout());
						currentEditor.createControls(composite, tk);
						editorsToComposite.put(currentEditor, composite);
					}
					if (exp != null) {
						currentEditor.setModelObject(exp);
						currentEditor.setEditorContent(exp.getBody().toString());
					} else {
						currentEditor.setEditorContent("");
					}
					Composite newComposite = editorsToComposite.get(currentEditor);
					newComposite.setVisible(true);
					stackLayout.topControl = newComposite;
					editorContainer.layout(true);
					editorContainer.update();
					editorContainer.redraw();
				}
			}
		});
		
		return res;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createModelTreeContentProvider()
	 */
	@Override
	protected ITreeContentProvider createModelTreeContentProvider() {
		return new ExpressionValueTreeProvider(bound);
	}



	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getLabelProvider()
	 */
	@Override
	public LabelProvider getLabelProvider() {
		return new ExpressionLabelProvider();
	}



	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#categoryHandlesTreeNode(org.eclipse.bpel.ui.details.tree.ITreeNode)
	 */
	@Override
	public boolean categoryHandlesTreeNode(ITreeNode treeNode) {
		return treeNode.equals(ExpressionValueTreeProvider.CREATE_EXPRESSION_NODE)
			|| treeNode.equals(ExpressionValueTreeProvider.EXPRESSION)
			|| treeNode instanceof ExpressionTreeNode;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#applyModificationsTo(org.eclipse.bpel.ui.details.tree.ITreeNode, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void applyModificationsTo(ITreeNode node, EObject modelElement) {
		Expression exp = BPELFactory.eINSTANCE.createExpression();
		exp.setExpressionLanguage(BPELConstants.XMLNS_XPATH_EXPRESSION_LANGUAGE);
		exp.setBody("/an/xpath/expression");
		if (modelElement instanceof From) {
			((From)modelElement).setExpression(exp);
		} else if (modelElement instanceof To) {
			((To)modelElement).setExpression(exp);
		}
	}



	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getTreeItemForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ITreeNode getTreeItemForModel(EObject model) {
		for (Object item : getModelTreeContentProvider().getChildren(ExpressionValueTreeProvider.EXPRESSION)) {
			if (item instanceof ExpressionTreeNode && ((ExpressionTreeNode) item).getModelObject().equals(model)) {
				return (ExpressionTreeNode)item;
			}
		}
		return null;
	}
	
}

