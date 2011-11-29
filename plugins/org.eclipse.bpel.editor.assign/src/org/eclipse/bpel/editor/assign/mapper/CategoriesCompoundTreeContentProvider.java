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
package org.eclipse.bpel.editor.assign.mapper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpel.editor.assign.contributors.IAssignCategory;
import org.eclipse.bpel.ui.details.tree.TreeNode;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class CategoriesCompoundTreeContentProvider implements ITreeContentProvider {

	IAssignCategory[] categories;
	
	/**
	 * @param isCondensed
	 * @param isPropertyTree
	 * @param displayParticles
	 */
	public CategoriesCompoundTreeContentProvider(boolean isCondensed, boolean isPropertyTree, boolean displayParticles, IAssignCategory[] categories) {
		this.categories = categories;
	}
	
	private List<ITreeContentProvider> getProvidersFor(Object input) {
		List<ITreeContentProvider> providers = new ArrayList<ITreeContentProvider>();
		for (IAssignCategory cat : categories) {
			if (cat.categoryHandlesTreeNode((TreeNode)input)) {
				providers.add(cat.getModelTreeContentProvider());
			}
		}
		return providers;
	}
	
	@Override
	public Object[] getElements(Object input) {
		List<Object> res = new ArrayList<Object>();
		for (IAssignCategory cat : categories) {
			ITreeContentProvider provider = cat.getModelTreeContentProvider();
			if (provider != null) {
				for (Object item : provider.getElements(input)) {
					if (!res.contains(item)) {
						res.add(item);
					}
				}
			}
		}
		return res.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// DO NOT DISPOSE TREES. DISPOSE CATEGORIES INSTEAD
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		for (IAssignCategory cat : categories) {
			ITreeContentProvider provider = cat.getModelTreeContentProvider();
			if (provider != null) {
				provider.inputChanged(viewer, oldInput, newInput);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> res = new ArrayList<Object>();
		for (ITreeContentProvider provider : getProvidersFor(parentElement)) {
			for (Object item : provider.getChildren(parentElement)) {
				if (!res.contains(item)) {
					res.add(item);
				}
			}
		}
		return res.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
	 */
	@Override
	public Object getParent(Object element) {
		for (ITreeContentProvider provider : getProvidersFor(element)) {
			Object parent = provider.getParent(element);
			if (parent != null) {
				return parent;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		for (ITreeContentProvider provider : getProvidersFor(element)) {
			boolean hasChildren = provider.hasChildren(element);
			if (hasChildren) {
				return true;
			}
		}
		return false;
	}

}
