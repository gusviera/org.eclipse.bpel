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
package org.eclipse.bpel.editor.assign;

import org.eclipse.bpel.model.Copy;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class CopyLabelProvider extends LabelProvider implements	IBaseLabelProvider {
	
	@Override
	public String getText(Object item) {
		Copy copy = (Copy)item;
		String src = null;
		if (copy.getFrom().getVariable() != null) {
			src = copy.getFrom().getVariable().getName();
		} else {
			src = "?";
		}
		String to = null;
		if (copy.getTo().getVariable() != null) {
			to = copy.getTo().getVariable().getName();
		} else {
			to = "?";
		}
		return src + " - " + to;
	}
	
}
