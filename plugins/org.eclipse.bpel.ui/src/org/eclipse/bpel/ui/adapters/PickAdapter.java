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
package org.eclipse.bpel.ui.adapters;

import java.util.List;

import org.eclipse.bpel.model.BPELPackage;
import org.eclipse.bpel.ui.actions.editpart.CreateOnAlarmAction;
import org.eclipse.bpel.ui.actions.editpart.CreateOnMessageAction;
import org.eclipse.bpel.ui.adapters.delegates.MultiContainer;
import org.eclipse.bpel.ui.adapters.delegates.ReferenceContainer;
import org.eclipse.bpel.ui.editparts.OutlineTreeEditPart;
import org.eclipse.bpel.ui.editparts.SwitchEditPart;
import org.eclipse.gef.EditPart;


public class PickAdapter extends ContainerActivityAdapter {

	/* IContainer delegate */

	public IContainer createContainerDelegate() {
		MultiContainer omc = new MultiContainer();
		omc.add(new ReferenceContainer(BPELPackage.eINSTANCE.getPick_Messages()));
		omc.add(new ReferenceContainer(BPELPackage.eINSTANCE.getPick_Alarm()));
		return omc;
	}

	/* EditPartFactory */
	
	public EditPart createEditPart(EditPart context, Object model) {
		EditPart result = new SwitchEditPart();
		result.setModel(model);
		return result;
	}

	/* IOutlineEditPartFactory */
	
	public EditPart createOutlineEditPart(EditPart context, Object model) {
		EditPart result = new OutlineTreeEditPart();
		result.setModel(model);
		return result;
	}

	/* IEditPartActionContributor */
	
	public List getEditPartActions(final EditPart editPart) {
		List actions = super.getEditPartActions(editPart);

		actions.add(new CreateOnMessageAction(editPart));
		actions.add(new CreateOnAlarmAction(editPart));

		return actions;
	}
}