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

import org.eclipse.osgi.util.NLS;

/**
 * @author Mickael Istria (EBM WebSourcing (PetalsLink))
 *
 */
public class Messages extends NLS {
	
	public static String details;
	public static String directValue;
	public static String expression;
	public static String mappingsOverview;
	public static String selectMappingToEdit;
	public static String opaque;
	public static String opaqueDescription;
	public static String newLiteralValue;
	public static String createExpression;
	public static String source;
	public static String target;
	public static String value;
	public static String language;
	public static String deleteMapping;
	public static String confirmDeletationOfMapping_title;
	public static String confirmDeletationOfMapping_desc;

	static {
		initializeMessages("messages", Messages.class);
	}
}
