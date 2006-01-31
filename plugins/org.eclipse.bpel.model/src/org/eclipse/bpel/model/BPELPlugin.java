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
package org.eclipse.bpel.model;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * The {@link org.eclipse.core.runtime.Plugin} for the BPEL model.
 * <p>
 * The BPEL model must run 
 * within an Eclipse workbench, 
 * within an Eclipse headless workspace
 * or stand-alone outside of Eclipse.
 * To support this BPELPlugin extends {@link org.eclipse.emf.common.EMFPlugin}.
 */
public class BPELPlugin extends EMFPlugin 
{
	/**
	 * The singleton instance of the BPEL plugin.
	 */
	public static final BPELPlugin INSTANCE = new BPELPlugin();

	public static final String PLUGIN_ID = "org.eclipse.bpel.model"; //$NON-NLS-1$

	/**
	 * The one instance of the Eclipse plugin.  
	 * This is <code>null</code> if Eclipse is not running.
	 */
	private static Implementation plugin;

	public BPELPlugin()
	{
		super(new ResourceLocator[] {});
	}

	/**
	 * Returns an Eclipse plugin.
	 */
	public static Implementation getPlugin()
	{
		return plugin;
	}

	public ResourceLocator getPluginResourceLocator()
	{
		return plugin;
	}

	/**
	 * The actual implementation of the Eclipse <b>Plugin</b>.
	 */
	public static class Implementation extends EclipsePlugin
	{
		/**
	     * Creates an instance.
	     */
	    public Implementation()
	    {
			// Remember the static instance.
			plugin = this;
		}

		/**
		 * Returns the workspace instance.
		 */
		public static IWorkspace getWorkspace()
		{
			return ResourcesPlugin.getWorkspace();
		}		
	}

	/**
	 * @deprecated Use {@link BPELPlugin#INSTANCE}.
	 */
	public static BPELPlugin getDefault()
	{
		return INSTANCE;
	}
	
	/**
	 * @deprecated Use {@link ResourceLocator#getString(java.lang.String)}.
	 */
	public static String getMessageText(String msgId)
	{
		return INSTANCE.getString(msgId);
	}
	
	/**
	 * @deprecated Use {@link ResourceLocator#getString(java.lang.String, java.lang.Object[])}.
	 */
	public static String getMessageText(String msgId, Object arg)
	{	
		return INSTANCE.getString(msgId, new Object[]{ arg });	
	}
	
	/**
	 * @deprecated Use {@link ResourceLocator#getString(java.lang.String, java.lang.Object[])}.
	 */
	public static String getMessageText(String msgId, Object arg1, Object arg2)
	{	
		return INSTANCE.getString(msgId, new Object[]{ arg1, arg2 });	
	}
}