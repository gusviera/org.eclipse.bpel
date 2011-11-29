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
package org.eclipse.bpel.editor.assign.contributors.expression;

import org.eclipse.bpel.ui.details.tree.TreeNode;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class ExpressionTreeNode extends TreeNode {

	/**
	 * @param bound
	 * @param bound2
	 */
	public ExpressionTreeNode(Object bound) {
		super(bound, true);
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
		return false;
	}
	
}
