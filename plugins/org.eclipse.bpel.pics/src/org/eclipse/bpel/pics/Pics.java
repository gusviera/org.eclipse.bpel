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
package org.eclipse.bpel.pics;

import java.io.InputStream;

import org.eclipse.bpel.pics.internal.PicsPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class Pics {

	private static Image getImage(String pathInBundle) {
		if (PicsPlugin.getDefault().getImageRegistry().get(pathInBundle) == null) {
			try {
				InputStream stream = PicsPlugin.getDefault().getBundle().getResource(pathInBundle).openStream();
				Image image = new Image(Display.getDefault(), stream);
				stream.close();
				PicsPlugin.getDefault().getImageRegistry().put(pathInBundle, image);
			} catch (Exception ex) {
				PicsPlugin.log(ex, IStatus.ERROR);
			}
		}
		return PicsPlugin.getDefault().getImageRegistry().get(pathInBundle);
	}

	/**
	 * @return
	 */
	public static Image getPencilIcon() {
		return getImage("icons/pencil.png");
	}

	/**
	 * @return
	 */
	public static Image getCalculatorIcon() {
		return getImage("icons/calculator_edit.png");
	}

	/**
	 * @return
	 */
	public static Image getDelete() {
		return getImage("icons/cross.png");
	}
}
