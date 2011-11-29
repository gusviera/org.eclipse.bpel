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

import java.util.Collections;
import java.util.List;

import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ITreeContentProvider;
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

public abstract class AssignCategoryBase implements IAssignCategory {
	
	protected Control fComposite;
	private ITreeContentProvider contentProvider;
	
	/**
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#isCategoryForModel(org.eclipse.emf.ecore.EObject)
	 */
	public boolean isCategoryForModel(EObject model) {
		return false;
	}

	/**
	 * Return the composite that was used in createControls call.
	 * 
	 * @see org.eclipse.bpel.ui.properties.IAssignCategory#getComposite()
	 */	
	public Control getDetailsControl (Composite parent, FormToolkit widgetFactory) {
		if (fComposite == null) {
			fComposite = createDetailsControl(parent, widgetFactory);
		}
		return fComposite;		
	}

	/**
	 * Override this method.  Call this version if the subclass wants its own composite
	 * with margin and black border.
	 * @return The top-level created composite 
	 */
	protected abstract Control createDetailsControl(Composite parent, FormToolkit widgetFactory);

	
	/**
	 * This is just a workaround to keep the AssignCategory from changing too much.
	 * @param model the model object
	 */
	public abstract void setInput(EObject model);
	
	public ITreeContentProvider getModelTreeContentProvider() {
		if (this.contentProvider == null) {
			this.contentProvider = createModelTreeContentProvider();
		}
		return this.contentProvider;
	}

	/**
	 * @return
	 */
	protected abstract ITreeContentProvider createModelTreeContentProvider();
	
}
