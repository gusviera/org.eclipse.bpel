/*******************************************************************************
* Copyright (c) 2011 EBM WebSourcing (PetalsLink)
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* Mickael Istria, EBM WebSourcing (PetalsLink) - initial API and implementation
*******************************************************************************/
package org.eclipse.bpel.editor.assign.contributors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.details.tree.TreeNode;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class FakeTreeNodesProvider extends ModelTreeContentProvider {

	public static class FakeTreeNode extends TreeNode {

		/**
		 * @param modelObject
		 * @param isCondensed
		 */
		public FakeTreeNode() {
			super(null, true);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpel.ui.details.tree.TreeNode#getChildren()
		 */
		@Override
		public Object[] getChildren() {
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpel.ui.details.tree.TreeNode#hasChildren()
		 */
		@Override
		public boolean hasChildren() {
			// TODO Auto-generated method stub
			return false;
		}
	}

	private List<FakeTreeNode> elements;
	
	/**
	 * @param isCondensed
	 */
	public FakeTreeNodesProvider() {
		super(true);
		elements = new ArrayList<FakeTreeNodesProvider.FakeTreeNode>();
	}
	
	public void addElement(FakeTreeNode node) {
		this.elements.add(node);
	}

	@Override
	public Object[] primGetElements(Object input) {
		return this.elements.toArray();
	}
}
