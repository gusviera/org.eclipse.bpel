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
package org.eclipse.bpel.ui.properties;

import org.eclipse.bpel.model.To;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.wst.common.ui.properties.internal.provisional.ISection;


/**
 * An IDetailsSection representing a panel of widgets for one specifying one kind
 * of From/To contents.  Subclasses of AssignCategory can answer whether they apply
 * to the contents of a particular From or To, and they provide widgets specific to
 * that kind of contents.
 * 
 * @see AssignImplSection.categories
 */
public interface IAssignCategory extends ISection {

	public boolean isHidden();
	
	/**
	 * Returns a label for the category (e.g. to show in a combo).
	 */
	public String getName();

	/**
	 * Returns true if the state in the toOrFrom object can be represented by this category.
	 * If isFrom is true, toOrFrom will be a From object, otherwise it will be a To object.
	 */
	public boolean isCategoryForModel(To toOrFrom);

	/**
	 * Used in the same way as BPELPropertySection.getUserContext(), except that this is
	 * normally called by AssignImplDetails rather than directly by the wrapped Command.
	 * 
	 * This is because an AssignCategory should not use itself as the detailsSection
	 * when it wraps a Command--it should use its ownerSection.
	 * 
	 * @see BPELPropertySection.getUserContext() 
	 */
	public Object getUserContext();
	
	/**
	 * Used in the same way as BPELPropertySection.getUserContext(), except that this is
	 * normally called by AssignImplDetails rather than directly by the wrapped Command.
	 * 
	 * This is because an AssignCategory should not use itself as the detailsSection
	 * when it wraps a Command--it should use its ownerSection.
	 * 
	 * @see BPELPropertySection.restoreUserContext(Object) 
	 */
	public void restoreUserContext(Object userContext);

	
	/**
	 * This is just a workaround to keep the AssignCategory from changing too much.
	 * TODO: get rid of these!
	 */
	public void setInput(EObject model);
}