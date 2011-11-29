/*******************************************************************************
* Copyright (c) 2011 EBM WebSourcing (PetalsLink)
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/egal/epl-v10.html
*
* Contributors:
* Mickael Istria, EBM WebSourcing (PetalsLink) - initial API and implementation
*******************************************************************************/
package org.eclipse.bpel.editor.assign.mapper;

import org.eclipse.bpel.editor.assign.contributors.IAssignCategory;
import org.eclipse.bpel.editor.assign.contributors.variablepart.BPELVariableTreeNode;
import org.eclipse.bpel.editor.assign.contributors.variablepart.MessageTypeTreeNode;
import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Copy;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.bpel.ui.details.tree.PartTreeNode;
import org.eclipse.bpel.ui.details.tree.XSDTreeNode;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.nebula.widgets.treemapper.ISemanticTreeMapperSupport;
import org.eclipse.xsd.XSDFeature;
import org.eclipse.xsd.XSDTypeDefinition;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class BPELAssignSemanticTreeMappingSupport implements ISemanticTreeMapperSupport<Copy, ITreeNode, ITreeNode> {

	private IAssignCategory[] srcCategories;
	private IAssignCategory[] targetCategories;
	private TreeViewer srcTree;
	private TreeViewer targetTree;

	/**
	 * @param assign 
	 * @param leftContentProvider
	 * @param rightContentProvider
	 */
	public BPELAssignSemanticTreeMappingSupport(IAssignCategory[] srcCategories, IAssignCategory[] targetCategories) {
		this.srcCategories = srcCategories;
		this.targetCategories = targetCategories;
	}
	
	/**
	 * Set references to the trees in the mapper
	 */
	public void setTrees(TreeViewer srcTree, TreeViewer targetTree) {
		this.srcTree = srcTree;
		this.targetTree = targetTree;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.treemapper.ISemanticTreeMapperSupport#createSemanticMappingObject(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Copy createSemanticMappingObject(ITreeNode leftItem,	ITreeNode rightItem) {
		if (!canCreateMapping(leftItem, rightItem)) {
			return null;
		}
		Copy res = BPELFactory.eINSTANCE.createCopy();
		{
			// left
			From from = BPELFactory.eINSTANCE.createFrom();
			res.setFrom(from);
			fillFrom(leftItem, from);
		}
		{
			// right
			To to = BPELFactory.eINSTANCE.createTo();
			res.setTo(to);
			fillTo(rightItem, to);
		}
		return res;
	}

	/**
	 * @param leftItem
	 * @param rightItem
	 * @return
	 */
	private boolean canCreateMapping(ITreeNode leftItem, ITreeNode rightItem) {
		// What about PartTreeNode to PartTreeNode 
		if (leftItem instanceof XSDTreeNode && rightItem instanceof XSDTreeNode) {
			XSDTypeDefinition leftType = ((XSDFeature) ((XSDTreeNode)leftItem).getModelObject()).getType();
			XSDTypeDefinition rightType = ((XSDFeature) ((XSDTreeNode)rightItem).getModelObject()).getType();
			while (leftType != null) {
				if (leftType.equals(rightType)) {
					return true;
				} else {
					leftType = leftType.getBaseType();
				}
			}
		}
		return 
			!(leftItem instanceof BPELVariableTreeNode || leftItem instanceof MessageTypeTreeNode || leftItem instanceof PartTreeNode) &&
			!(rightItem instanceof BPELVariableTreeNode || rightItem instanceof MessageTypeTreeNode || rightItem instanceof PartTreeNode);
	}

	/**
	 * @param leftModel
	 * @param from
	 */
	private void fillFrom(ITreeNode node, From from) {
		if (node == null) {
			return;
		}
		for (IAssignCategory cat : srcCategories) {
			if (cat.categoryHandlesTreeNode(node)) {
				cat.applyModificationsTo(node, from);
				return;
			}
		}
	}
	
	/**
	 * @param leftModel
	 * @param from
	 */
	private void fillTo(ITreeNode node, To to) {
		if (node == null) {
			return;
		}
		for (IAssignCategory cat : targetCategories) {
			if (cat.categoryHandlesTreeNode(node)) {
				cat.applyModificationsTo(node, to);
				return;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.treemapper.ISemanticTreeMapperSupport#resolveLeftItem(java.lang.Object)
	 */
	@Override
	public ITreeNode resolveLeftItem(Copy semanticMappingObject) {
		From from = semanticMappingObject.getFrom();
		for (IAssignCategory cat : srcCategories) {
			if (cat.isCategoryForModel(from)) {
				return cat.getTreeItemForModel(from);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.nebula.treemapper.ISemanticTreeMapperSupport#resolveRightItem(java.lang.Object)
	 */
	@Override
	public ITreeNode resolveRightItem(Copy semanticMappingObject) {
		To to = semanticMappingObject.getTo();
		for (IAssignCategory cat : targetCategories) {
			if (cat.isCategoryForModel(to)) {
				return cat.getTreeItemForModel(to);
			}
		}
		return null;
	}


}
