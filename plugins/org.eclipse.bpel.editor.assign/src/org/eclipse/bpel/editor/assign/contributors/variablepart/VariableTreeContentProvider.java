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
package org.eclipse.bpel.editor.assign.contributors.variablepart;

import java.util.ArrayList;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider;
import org.eclipse.bpel.ui.util.BPELUtil;
import org.eclipse.emf.ecore.EObject;


/**
 * Provides a tree of model objects representing some expansion of the underlying graph
 * of model objects whose roots are the Variables of a Process. 
 */
public class VariableTreeContentProvider extends ModelTreeContentProvider {

	boolean fDisplayParticles;
	
	/**
	 * Variable tree content provider.
	 * 
	 * @param displayParticles
	 */
	public VariableTreeContentProvider(boolean displayParticles) {
		super(false);
		this.fDisplayParticles = displayParticles;
	}

	/**
	 * @see org.eclipse.bpel.ui.details.providers.ModelTreeContentProvider#primGetElements(java.lang.Object)
	 */
	@Override
	public Object[] primGetElements(Object inputElement) {
		
		Variable[] vars = BPELUtil.getVisibleVariables((EObject)inputElement);		
		// TODO: can this code be moved to a filter?
		ArrayList<Object> result = new ArrayList<Object>( vars.length );
		for (Variable v : vars) {
			result.add(new BPELVariableTreeNode(v, isCondensed, fDisplayParticles));
		}				
		return result.toArray();
	}

}
