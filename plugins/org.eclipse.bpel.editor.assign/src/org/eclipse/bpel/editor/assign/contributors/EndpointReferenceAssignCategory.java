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
package org.eclipse.bpel.editor.assign.contributors;

import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

/**
 * An AssignCategory for editing an EndpointReference.  This is a special type of
 * literal (though it is recognized by the deserializer, and represented in the model
 * as an EndpointReference object rather than as a literal).
 */
public class EndpointReferenceAssignCategory extends AssignCategoryBase {


	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean isCategoryForModel (EObject aModel) {
		From from = BPELUtil.adapt(aModel, From.class);
		return from != null && from.getServiceRef() != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createClient(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	@Override
	protected Composite createDetailsControl(Composite parent, FormToolkit widgetFactory) {
		// TODO: Delegate to the endpoint handler to create the widgets
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#setInput(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void setInput(EObject model) {
		// TODO: Delegate to the endpoint handler to create the widgets
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createModelTreeContentProvider()
	 */
	@Override
	protected ModelTreeContentProvider createModelTreeContentProvider() {
		// TODO impl
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getLabelProvider()
	 */
	@Override
	public LabelProvider getLabelProvider() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#categoryHandlesTreeNode(org.eclipse.bpel.ui.details.tree.ITreeNode)
	 */
	@Override
	public boolean categoryHandlesTreeNode(ITreeNode treeNode) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#applyModificationsTo(org.eclipse.bpel.ui.details.tree.ITreeNode, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void applyModificationsTo(ITreeNode node, EObject modelElement) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getTreeItemForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ITreeNode getTreeItemForModel(EObject model) {
		// TODO Auto-generated method stub
		return null;
	}

}
