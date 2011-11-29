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
package org.eclipse.bpel.editor.assign.contributors.literal;

import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.details.tree.TreeNode;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class LiteralTreeNode extends TreeNode {

	/**
	 * @param modelObject
	 * @param isCondensed
	 */
	public LiteralTreeNode(From from) {
		super(from, true);
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
