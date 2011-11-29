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

import org.eclipse.bpel.editor.assign.contributors.IAssignCategory;
import org.eclipse.bpel.ui.details.providers.ModelTreeLabelProvider;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 * TODO Make it a compound label provider that delegates to the contributor
 */
public class CategoriesCompoundModelTreeLabelProvider extends LabelProvider {
	
	private ModelTreeLabelProvider modelLabelProvider = new ModelTreeLabelProvider();
	private IAssignCategory[] categories;
	
	public CategoriesCompoundModelTreeLabelProvider(IAssignCategory[] contributors) {
		this.categories = contributors;
	}
	
	@Override
	public String getText(Object item) {
		for (IAssignCategory cat : categories) {
			if (cat.categoryHandlesTreeNode((ITreeNode)item)) {
				LabelProvider labelProvider = cat.getLabelProvider();
				if (labelProvider != null) {
					return labelProvider.getText(item);
				}
			}
		}
		return modelLabelProvider.getText(item);
	}
	
	@Override
	public Image getImage(Object item) {
		for (IAssignCategory cat : categories) {
			if (cat.categoryHandlesTreeNode((ITreeNode)item)) {
				LabelProvider labelProvider = cat.getLabelProvider();
				if (labelProvider != null) {
					return labelProvider.getImage(item);
				}
			}
		}
		return modelLabelProvider.getImage(item);
	}

}
