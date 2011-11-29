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
package org.eclipse.bpel.editor.assign.contributors.variablepart;

import org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.Query;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.Messages;
import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wst.wsdl.Part;
import org.eclipse.xsd.XSDConcreteComponent;

/**
 * An AssignCategory presenting a tree from which the user can select any of: -
 * a Variable - a Part within a Variable - some XSD element within a Part within
 * a Variable.
 * 
 */

public class VariablePartAssignCategory extends AssignCategoryBase {

	Label fNameLabel;
	Text fNameText;

	VariableTreeContentProvider variableContentProvider;
	Shell shell;
	private EObject currentInput;

	/**
	 * @param variableProvider
	 */
	public VariablePartAssignCategory(VariableTreeContentProvider variableProvider) {
		this.variableContentProvider = variableProvider;
	}

	public boolean isPropertyCategory() {
		return false;
	}

	@Override
	protected Composite createDetailsControl(Composite parent, FormToolkit fWidgetFactory) {
		Composite res = fWidgetFactory.createComposite(parent);
		res.setLayout(new GridLayout(2, false));
		
		if (displayQuery()) {
			// area for query string and wizard button
			fNameLabel = fWidgetFactory.createLabel(res, Messages.VariablePartAssignCategory_Query__8);
			fNameText = fWidgetFactory.createText(res, "", SWT.BORDER); //$NON-NLS-1$
			fNameText.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
			fNameText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					if (currentInput != null) {
						Query query = null;
						if (currentInput instanceof From) {
							From from = (From)currentInput;
							query = from.getQuery();
						} else if (currentInput instanceof To) {
							To to = (To)currentInput;
							query = to.getQuery();
						}
						if (query == null) {
							query = BPELFactory.eINSTANCE.createQuery();
							query.setValue(fNameText.getText());
						}
						if (currentInput instanceof From) {
							From from = (From)currentInput;
							from.setQuery(query);
						} else if (currentInput instanceof To) {
							To to = (To)currentInput;
							to.setQuery(query);
						}
					}
				}
			});
		}

		return res;
	}

	/**
	 * @return
	 */
	private boolean displayQuery() {
		return ! isPropertyCategory();
	}

	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */

	@Override
	public boolean isCategoryForModel(EObject aModel) {
		if (aModel == null) {
			return false;
		}
		if (aModel instanceof From && ((From) aModel).getVariable() != null) {
			return isPropertyCategory() ? ((From) aModel).getProperty() != null : ((From) aModel).getProperty() == null;
		} else if (aModel instanceof To && ((To) aModel).getVariable() != null) {
			return isPropertyCategory() ? ((To) aModel).getProperty() != null : ((To) aModel).getProperty() == null;
		}
		return false;
	}

	@SuppressWarnings("nls")
	@Override
	public void setInput(EObject input) {
		this.currentInput = input;
		String text = null;
		if (input instanceof From) {
			Query query = ((From)input).getQuery();
			if (query != null) {
				text = query.getValue();
			}
		} else if (input instanceof To) {
			Query query = ((To)input).getQuery();
			if (query != null) {
				text = query.getValue();
			}
		}
		if (text != null) {
			fNameText.setText(text);
		} else {
			fNameText.setText("");
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createModelTreeContentProvider()
	 */
	@Override
	protected ModelTreeContentProvider createModelTreeContentProvider() {
		return variableContentProvider;
	}
	

	/**
	 * @param model
	 * @return
	 */
	private Query toQuery(Object element) {
		Query query = BPELFactory.eINSTANCE.createQuery();
		query.setQueryLanguage("xpath");
		query.setValue(XSDUtil.getXPath((VariableTreeContentProvider)getModelTreeContentProvider(), element, shell));
		return query;
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getLabelProvider()
	 */
	@Override
	public LabelProvider getLabelProvider() {
		return null; // Default provider
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#categoryHandlesTreeNode(org.eclipse.bpel.ui.details.tree.ITreeNode)
	 */
	@Override
	public boolean categoryHandlesTreeNode(ITreeNode treeNode) {
		while (treeNode != null) {
			if (treeNode instanceof BPELVariableTreeNode) {
				return true;
			}
			Object parent = getModelTreeContentProvider().getParent(treeNode);
			if (parent == ModelTreeContentProvider.UNKNOWN_PARENT) {
				return false;
			} else if (parent instanceof ITreeNode) {
				treeNode = (ITreeNode)parent;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#applyModificationsTo(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void applyModificationsTo(ITreeNode node, EObject modelElement) {
		Object selectedItem = node.getModelObject();
		if (modelElement instanceof From) {
			From from = (From)modelElement;
			if (selectedItem instanceof Variable) {
				from.setVariable((Variable)selectedItem);
			} else if (selectedItem instanceof Part) {
				from.setPart((Part)selectedItem);
			} else if (selectedItem instanceof XSDConcreteComponent) {
				from.setQuery(toQuery(node));
			}
		} else if (modelElement instanceof To) {
			To to = (To)modelElement;
			if (selectedItem instanceof Variable) {
				to.setVariable((Variable)selectedItem);
			} else if (selectedItem instanceof Part) {
				to.setPart((Part)selectedItem);
			} else if (selectedItem instanceof XSDConcreteComponent) {
				to.setQuery(toQuery(node));
			}
		}
		Object parent = getModelTreeContentProvider().getParent(node);
		if (parent instanceof ITreeNode) {
			applyModificationsTo((ITreeNode)parent, modelElement);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getTreeItemForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ITreeNode getTreeItemForModel(EObject model) {
		if (model instanceof To) {
			To to = (To)model;
			return XSDUtil.getTreeNode((VariableTreeContentProvider)getModelTreeContentProvider(),
					to,
					to.getVariable(),
					to.getPart(),
					to.getQuery());
		} else if (model instanceof From) {
			From from = (From)model;
			return XSDUtil.getTreeNode((VariableTreeContentProvider)getModelTreeContentProvider(),
					from,
					from.getVariable(),
					from.getPart(),
					from.getQuery());
		} else {
			return null;
		}
		
	}
}
