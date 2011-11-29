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
package org.eclipse.bpel.editor.assign.contributors.literal;

import org.eclipse.bpel.editor.assign.Messages;
import org.eclipse.bpel.model.From;
import org.eclipse.bpel.pics.Pics;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
final class LiteralLabelProvider extends LabelProvider {
	@Override
	public Image getImage(Object item) {
		return Pics.getPencilIcon();
	}

	@Override
	public String getText(Object item) {
		if (item instanceof LiteralTreeNode) {
			return ((From) ((LiteralTreeNode)item).getModelObject()).getLiteral();
		} else if (item.equals(LiteralValueTreeProvider.CREATE_LITERAL_NODE)) {
			return Messages.newLiteralValue;
		} else if (item.equals(LiteralValueTreeProvider.LITERALS)) {
			return Messages.directValue;
		}
		return super.getText(item);
	}
}