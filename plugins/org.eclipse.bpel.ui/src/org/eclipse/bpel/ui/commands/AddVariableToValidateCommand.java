/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.bpel.ui.commands;

import java.util.List;

import org.eclipse.bpel.model.Variable;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;


/**
 * Adds a Variable to an Validate.
 */
public class AddVariableToValidateCommand extends AddToListCommand {

	public AddVariableToValidateCommand(EObject target, Variable newVariable) {
		super(target, newVariable, IBPELUIConstants.CMD_ADD_VALIDATE_VARIABLE);
	}

	protected List getList() {
		EList l = ModelHelper.getValidateVariables(target);
		return (l == null)? null : l;
	}

	protected void deleteList() {
		EList l = ModelHelper.getValidateVariables(target);
		if (l != null)
			l.clear();
	}
}
