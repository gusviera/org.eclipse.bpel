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
package org.eclipse.bpel.ui.commands;

import java.util.List;

import org.eclipse.bpel.model.BPELFactory;
import org.eclipse.bpel.model.Correlation;
import org.eclipse.bpel.model.Correlations;
import org.eclipse.bpel.ui.IBPELUIConstants;
import org.eclipse.bpel.ui.util.ModelHelper;
import org.eclipse.emf.ecore.EObject;


/**
 * Adds a Correlation to an Invoke/Receive/Reply/OnMessage.
 */
public class AddCorrelationCommand extends AddToListCommand {

	public AddCorrelationCommand(EObject target, Correlation newCorrelation) {
		super(target, newCorrelation, IBPELUIConstants.CMD_ADD_CORRELATION);
	}

	protected List getList() {
		Correlations c = ModelHelper.getCorrelations(target);
		return (c == null)? null : c.getChildren();
	}

	protected void createList() {
		ModelHelper.setCorrelations(target, BPELFactory.eINSTANCE.createCorrelations());
	}
	
	protected void deleteList() {
		ModelHelper.setCorrelations(target, null);
	}
}