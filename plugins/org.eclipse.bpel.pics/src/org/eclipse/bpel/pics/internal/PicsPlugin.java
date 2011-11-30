package org.eclipse.bpel.pics.internal;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


/**
 * The activator class controls the plug-in life cycle
 */
public class PicsPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.bpel.editor.assign"; //$NON-NLS-1$

	// The shared instance
	private static PicsPlugin plugin;
	
	/**
	 * The constructor
	 */
	public PicsPlugin() {
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
	public static PicsPlugin getDefault() {
		return plugin;
	}
	
	/**
	 * Utility methods for logging exceptions.
	 * @param e
	 * @param severity
	 */
	public static void log(Throwable e, int severity) {
		IStatus status = null;
		if (e instanceof CoreException) {
			status = ((CoreException)e).getStatus();
		} else {
			String m = e.getMessage();
			status = new Status(severity, PLUGIN_ID, 0, m==null? "<no message>" : m, e); //$NON-NLS-1$
		}
		System.out.println(e.getClass().getName()+": "+status); //$NON-NLS-1$
		getDefault().getLog().log(status);
	}

}
