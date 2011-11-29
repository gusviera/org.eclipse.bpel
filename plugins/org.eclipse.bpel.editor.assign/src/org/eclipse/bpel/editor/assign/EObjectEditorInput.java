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

import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class EObjectEditorInput extends URIEditorInput {

	private EObject eObject;

	public EObjectEditorInput(EObject eObject) {
		super(EcoreUtil.getURI(eObject));
		this.eObject = eObject;
	}
	
	/**
	 * @return the eObject
	 */
	public EObject getEObject() {
		return eObject;
	}

}
