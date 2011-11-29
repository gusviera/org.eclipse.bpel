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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.editor.assign.contributors.CopyBound;
import org.eclipse.bpel.editor.assign.contributors.FakeTreeNodesProvider.FakeTreeNode;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class ExpressionValueTreeProvider implements ITreeContentProvider {

	public static FakeTreeNode CREATE_EXPRESSION_NODE = new FakeTreeNode();
	public static FakeTreeNode EXPRESSION = new FakeTreeNode();
	
	private Assign assign;
	private Set<Object> alreadyProcessedBounds;
	private List<ITreeNode> children;
	private CopyBound bound;
	
	/**
	 * @param bound
	 */
	public ExpressionValueTreeProvider(CopyBound bound) {
		this.bound = bound;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.assign = (Assign)newInput;
		this.alreadyProcessedBounds = new HashSet<Object>();
		this.children = new ArrayList<ITreeNode>();
		children.add(CREATE_EXPRESSION_NODE);
	}
	
	@Override
	public Object[] getElements(Object input) {
		return new Object[] { EXPRESSION };
	}
	
	@Override
	public boolean hasChildren(Object parent) {
		return parent == EXPRESSION;
	}
	
	@Override
	public Object[] getChildren(Object parent) {
		for (Copy copy : assign.getCopy()) {
			Object bound = null;
			Expression exp = null;
			if (this.bound == CopyBound.FROM) {
				bound = copy.getFrom();
				exp = copy.getFrom().getExpression();
			} else {
				bound = copy.getTo();
				exp = copy.getTo().getExpression();
			}
			if (exp != null && !alreadyProcessedBounds.contains(bound)) {
				children.add(children.size() - 1, new ExpressionTreeNode(bound));
				alreadyProcessedBounds.add(bound);
			}
		}
		return children.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() { }

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		if (element != EXPRESSION) {
			return EXPRESSION;
		} else {
			return null;
		}
	}

}
