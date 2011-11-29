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

import java.util.List;

import org.eclipse.bpel.model.From;
import org.eclipse.bpel.model.To;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;



/**
 * An IDetailsSection representing a panel of widgets for one specifying one kind
 * of From/To contents.  Subclasses of AssignCategory can answer whether they apply
 * to the contents of a particular From or To, and they provide widgets specific to
 * that kind of contents.
 * 
 */
public interface IAssignCategory {

	/**
	 * 
	 * @return A composite that contains all the controls to manipulate an
	 * item provided by current IAssignCategory
	 */
	public Control getDetailsControl(Composite parent, FormToolkit widgetFactory);
	
	/**
	 * Set the input object for the UI. Generally deals with databinding, setValue,
	 * and listeners.
	 * <b>IMPORTANT<b> This methods ASSERTS that the model parameter
	 * is provided by the current category.
	 * @param model 
	 */
	public void setInput(EObject model);
	
	/**
	 * Returns true if the state in the toOrFrom object can be represented by this category.
	 * If isFrom is true, toOrFrom will be a From object, otherwise it will be a To object.
	 * @param aModel the model object.
	 * @return true if this is the category for this model object, false otherwise.
	 */
	public boolean isCategoryForModel(EObject aModel);
	
	/**
	 * 
	 * @return a Content provider for the given category
	 */
	public ITreeContentProvider getModelTreeContentProvider();
	/**
	 * 
	 * @return a {@link LabelProvider} for items contributed by this category
	 * This label provider must return null in case the item is not provided
	 * by this category.
	 */
	public LabelProvider getLabelProvider();
	
	/**
	 * 
	 * @param treeNode
	 * @return Whether the provided {@link ITreeNode} is contributed by the current
	 * category
	 */
	public boolean categoryHandlesTreeNode(ITreeNode treeNode);
	
	/**
	 * Applies the modification to the containing model element (generally
	 * {@link To} or {@link From}) when creating a new mapping with the given element
	 * @param node the selected {@link ITreeNode} that represents the item
	 * @param modelElement The element to modify
	 */
	public void applyModificationsTo(ITreeNode node, EObject/*AbstractAssignBound*/ modelElement);
	
	/**
	 * @param EObject model (generally a {@link To} or a {@link From}
	 * @return the ITreeNode associated to the model item
	 */
	public ITreeNode getTreeItemForModel(EObject model);

	
}
