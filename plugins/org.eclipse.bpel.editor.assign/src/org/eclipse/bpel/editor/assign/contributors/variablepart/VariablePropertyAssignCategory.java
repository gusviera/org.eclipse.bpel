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



/**
 * An AssignCategory presenting a tree from which the user can select any of:
 *  - a Variable (though it will mysteriously switch to VariablePartAssignCategory ..)
 *  - a Property of a Variable
 *  - some XSD element within the type of a Property of a Variable.
 */
public class VariablePropertyAssignCategory extends VariablePartAssignCategory {
	
	/**
	 * @param leftVariableProvider
	 */
	public VariablePropertyAssignCategory(VariableTreeContentProvider variableProvider) {
		super(variableProvider);
	}

	@Override
	public boolean isPropertyCategory() { 
		return true; 
	}
}
