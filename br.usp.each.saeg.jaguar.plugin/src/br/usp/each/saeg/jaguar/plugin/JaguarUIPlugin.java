package br.usp.each.saeg.jaguar.plugin;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class JaguarUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "br.usp.each.saeg.jaguar.plugin"; //$NON-NLS-1$

	// The shared instance
	private static JaguarUIPlugin plugin;
	
	private static final IWorkbenchWindow[] NO_WINDOWS = new IWorkbenchWindow[0];
	
	/**
	 * The constructor
	 */
	public JaguarUIPlugin() {
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
	public static JaguarUIPlugin getDefault() {
		return plugin;
	}
	
    public static IWorkbenchWindow getActiveWorkbenchWindow() {
        return PlatformUI.getWorkbench().getActiveWorkbenchWindow();
    }

    public static IWorkbenchWindow[] getWorkbenchWindow() {
        IWorkbenchWindow[] windows = PlatformUI.getWorkbench().getWorkbenchWindows();
        if (ArrayUtils.isEmpty(windows)) {
            return NO_WINDOWS;
        }
        return windows;
    }

}
