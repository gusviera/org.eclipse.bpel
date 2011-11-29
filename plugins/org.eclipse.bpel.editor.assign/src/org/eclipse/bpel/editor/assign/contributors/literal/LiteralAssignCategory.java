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
package org.eclipse.bpel.editor.assign.contributors.literal;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.editor.assign.Messages;
import org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase;
import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;


/**
 * An AssignCategory where the user can type in a literal value (note: NOT an expression).
 * 
 * TODO: his could be an XML editor one day ...
 */

public class LiteralAssignCategory extends AssignCategoryBase {

	private static List<ITreeNode> LITERAL_LIST;
	static {
		LITERAL_LIST = new ArrayList<ITreeNode>();
		LITERAL_LIST.add(LiteralValueTreeProvider.LITERALS);
	}
	
	Text fLiteralText;
	private DataBindingContext dbc;
	

	@Override
	protected Composite createDetailsControl(Composite parent, FormToolkit fWidgetFactory) {
		Composite res = fWidgetFactory.createComposite(parent);
		res.setLayout(new GridLayout(1, false));
		fWidgetFactory.createLabel(res, Messages.value);
		fLiteralText = fWidgetFactory.createText(res, "", SWT.MULTI | SWT.BORDER);
		fLiteralText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		return res;
	}

	
	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	
	@Override
	public boolean isCategoryForModel (EObject aModel) {
		From from = BPELUtil.adapt(aModel, From.class);
		return (from != null && from.getLiteral() != null);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#setInput(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void setInput(EObject model) {
		if (model != null && fLiteralText != null) {
			if (dbc != null) {
				dbc.dispose();
			}
			dbc = new DataBindingContext();
			dbc.bindValue(
					SWTObservables.observeText(fLiteralText, SWT.Modify),
					EMFObservables.observeValue(model, BPELPackage.Literals.FROM__LITERAL));
		}
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createModelTreeContentProvider()
	 */
	@Override
	protected ITreeContentProvider createModelTreeContentProvider() {
		return new LiteralValueTreeProvider();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getLabelProvider()
	 */
	@Override
	public LabelProvider getLabelProvider() {
		return new LiteralLabelProvider();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#categoryHandlesTreeNode(org.eclipse.bpel.ui.details.tree.ITreeNode)
	 */
	@Override
	public boolean categoryHandlesTreeNode(ITreeNode treeNode) {
		return treeNode instanceof LiteralTreeNode
				|| treeNode.equals(LiteralValueTreeProvider.CREATE_LITERAL_NODE)
				|| treeNode.equals(LiteralValueTreeProvider.LITERALS);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#applyModificationsTo(org.eclipse.bpel.ui.details.tree.ITreeNode, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void applyModificationsTo(ITreeNode node, EObject modelElement) {
		((From)modelElement).setLiteral("<element>literal</element>");
	}


	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getTreeItemForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ITreeNode getTreeItemForModel(EObject model) {
		for (Object item : getModelTreeContentProvider().getChildren(LiteralValueTreeProvider.LITERALS)) {
			if (item instanceof LiteralTreeNode && ((LiteralTreeNode) item).getModelObject().equals(model)) {
				return (LiteralTreeNode)item;
			}
		}
		return null;
	}

}
