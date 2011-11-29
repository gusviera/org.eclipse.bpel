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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.bpel.editor.assign.contributors.FakeTreeNodesProvider.FakeTreeNode;
import org.eclipse.bpel.model.Assign;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class LiteralValueTreeProvider implements ITreeContentProvider {

	public static FakeTreeNode CREATE_LITERAL_NODE = new FakeTreeNode();
	public static FakeTreeNode LITERALS = new FakeTreeNode();
	
	private Assign assign;
	private Set<From> alreadyProcessedFrom;
	private List<ITreeNode> children;
	
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.assign = (Assign)newInput;
		this.alreadyProcessedFrom = new HashSet<From>();
		this.children = new ArrayList<ITreeNode>();
		children.add(CREATE_LITERAL_NODE);
	}
	
	@Override
	public Object[] getElements(Object input) {
		return new Object[] { LITERALS };
	}
	
	@Override
	public boolean hasChildren(Object parent) {
		return parent == LITERALS;
	}
	
	@Override
	public Object[] getChildren(Object parent) {
		for (Copy copy : assign.getCopy()) {
			From from = copy.getFrom();
			if (from.getLiteral() != null && !alreadyProcessedFrom.contains(from)) {
				children.add(children.size() - 1, new LiteralTreeNode(copy.getFrom()));
				alreadyProcessedFrom.add(from);
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
		if (element != LITERALS) {
			return LITERALS;
		} else {
			return null;
		}
	}

}
