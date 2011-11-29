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

import org.eclipse.bpel.editor.assign.Messages;
import org.eclipse.bpel.editor.assign.contributors.FakeTreeNodesProvider.FakeTreeNode;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;


/**
 * An AssignCategory representing opaque="yes" in a From object.
 * TODO: This should only appear for abstract processes
 */
public class OpaqueAssignCategory extends AssignCategoryBase {

	public static FakeTreeNode OPAQUE_TREE_NODE = new FakeTreeNode();
	
	@Override
	protected Control createDetailsControl(Composite parent, FormToolkit tk) {
		return tk.createLabel(parent, Messages.opaqueDescription);
	}
	
	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean isCategoryForModel (EObject aModel) {
		
		From from = BPELUtil.adapt(aModel, From.class);
		if (from == null) {
			return false;
		}		
		return Boolean.TRUE.equals(from.getOpaque());		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#setInput(org.eclipse.emf.ecore.EObject, org.eclipse.emf.edit.domain.EditingDomain)
	 */
	@Override
	public void setInput(EObject model) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.AssignCategoryBase#createModelTreeContentProvider()
	 */
	@Override
	protected ModelTreeContentProvider createModelTreeContentProvider() {
		FakeTreeNodesProvider provider = new FakeTreeNodesProvider();
		provider.addElement(OPAQUE_TREE_NODE);
		return provider;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getLabelProvider()
	 */
	@Override
	public LabelProvider getLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object item) {
				return Messages.opaque;
			}
		};
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#categoryHandlesTreeNode(org.eclipse.bpel.ui.details.tree.ITreeNode)
	 */
	@Override
	public boolean categoryHandlesTreeNode(ITreeNode treeNode) {
		return treeNode.equals(OPAQUE_TREE_NODE);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#applyModificationsTo(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void applyModificationsTo(ITreeNode node, EObject modelElement) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpel.editor.assign.contributors.IAssignCategory#getTreeItemForModel(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public ITreeNode getTreeItemForModel(EObject model) {
		return OPAQUE_TREE_NODE;
	}
	

}
