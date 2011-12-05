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
package org.eclipse.bpel.editor.assign.contributors.expression;

import org.eclipse.bpel.editor.assign.Messages;
import org.eclipse.bpel.model.AbstractAssignBound;
import org.eclipse.bpel.model.Expression;
import org.eclipse.bpel.pics.Pics;
import org.eclipse.bpel.ui.details.tree.ITreeNode;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class ExpressionLabelProvider extends LabelProvider {
	
	@Override
	public String getText(Object item) {
		if (item.equals(ExpressionValueTreeProvider.EXPRESSION)) {
			return Messages.expression;
		} else if (item.equals(ExpressionValueTreeProvider.CREATE_EXPRESSION_NODE)) {
			return Messages.createExpression;
		} else if (item instanceof ITreeNode) {
			ITreeNode node = (ITreeNode)item;
			if (node.getModelObject() instanceof AbstractAssignBound) {
				Expression exp = ((AbstractAssignBound) node.getModelObject()).getExpression();
				if (exp.getBody() != null) {
					return exp.getBody().toString();
				} else {
					return "[empty expression]";
				}
			}
		}
		return super.getText(item);
	}
	
	@Override
	public Image getImage(Object item) {
		if (item == ExpressionValueTreeProvider.EXPRESSION || item == ExpressionValueTreeProvider.CREATE_EXPRESSION_NODE) {
			return Pics.getCalculatorIcon();
		} else {
			return super.getImage(item);
		}
	}

}
