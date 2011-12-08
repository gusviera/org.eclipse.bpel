package com.ebmwebsourcing.petals.studio.easybpel;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.ebmwebsourcing.easybpel.model.bpel.tools.validator.BPELValidator;

/**
 * The activator class controls the plug-in life cycle
 */
public class EasyBPELPlugin extends AbstractUIPlugin {
	
	// The plug-in ID
	public static final String PLUGIN_ID = "com.ebmwebsourcing.petals.studio.easybpel"; //$NON-NLS-1$

	// The shared instance
	private static EasyBPELPlugin plugin;

	private BPELValidator validator;

	/**
	 * The constructor
	 */
	public EasyBPELPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static EasyBPELPlugin getDefault() {
		return plugin;
	}
	
}
