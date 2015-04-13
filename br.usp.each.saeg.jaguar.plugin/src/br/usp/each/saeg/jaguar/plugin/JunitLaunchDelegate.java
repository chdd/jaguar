package br.usp.each.saeg.jaguar.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Collections;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate2;
import org.eclipse.osgi.util.NLS;
import org.jacoco.agent.AgentJar;
import org.jacoco.core.runtime.AgentOptions;

import br.usp.each.saeg.jaguar.core.JaCoCoClient;
import br.usp.each.saeg.jaguar.core.Jaguar;
import br.usp.each.saeg.jaguar.core.heuristic.TarantulaHeuristic;

public class JunitLaunchDelegate implements ILaunchConfigurationDelegate2 {

  /** Launch mode for the launch delegates used internally. */
  public static final String DELEGATELAUNCHMODE = ILaunchManager.RUN_MODE;

  protected ILaunchConfigurationDelegate launchdelegate;

  protected ILaunchConfigurationDelegate2 launchdelegate2;
  
  public void setInitializationData(IConfigurationElement config,
	      String propertyName, Object data) throws CoreException {
	    final String launchtype = config.getAttribute("type"); //$NON-NLS-1$
	    launchdelegate = getLaunchDelegate(launchtype);
	    if (launchdelegate instanceof ILaunchConfigurationDelegate2) {
	      launchdelegate2 = (ILaunchConfigurationDelegate2) launchdelegate;
	    }
	  }

	  private ILaunchConfigurationDelegate getLaunchDelegate(String launchtype)
	      throws CoreException {
	    ILaunchConfigurationType type = DebugPlugin.getDefault().getLaunchManager()
	        .getLaunchConfigurationType(launchtype);
	    if (type == null) {
	      throw new CoreException(
	          JaguarStatus.UNKOWN_LAUNCH_TYPE_ERROR.getStatus(launchtype));
	    }
	    return type.getDelegates(Collections.singleton(DELEGATELAUNCHMODE))[0]
	        .getDelegate();
	  }
	@Override
	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		
		boolean isDataFlow = false;
		File sourceDir = new File(ProjectUtils.getCurrentSelectedProject().getLocation().toString());
		
		// Start agent server
		final Jaguar jaguar = new Jaguar(new TarantulaHeuristic(), sourceDir, isDataFlow);
		JaCoCoClient client = null;
		try {
			client = new JaCoCoClient(isDataFlow);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			client.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		// Delegate to run mode launcher
		final ILaunchConfiguration adjusted =  new AdjustedLaunchConfiguration(getArgument(6300), configuration);
		launchdelegate.launch(adjusted, DELEGATELAUNCHMODE, launch,
				new SubProgressMonitor(monitor, 1));
	
		monitor.done();
	}

	protected String getArgument(int serverPort) throws CoreException {
		final AgentOptions options = new AgentOptions();
		//		    options.setIncludes(preferences.getAgentIncludes());
		//		    options.setExcludes(preferences.getAgentExcludes());
		//		    options.setExclClassloader(preferences.getAgentExclClassloader());
		options.setOutput(AgentOptions.OutputMode.tcpclient);
		options.setPort(serverPort);
		return quote(options.getVMArgument(getAgentFile()));
	}

	protected String quote(String arg) {
		if (arg.indexOf(' ') == -1) {
			return arg;
		} else {
			return '"' + arg + '"';
		}
	}
	  
	protected File getAgentFile() throws CoreException {
		try {
			final URL agentfileurl = FileLocator.toFileURL(AgentJar.getResource());
			return new Path(agentfileurl.getPath()).toFile();
		} catch (IOException e) {
			throw new CoreException(JaguarStatus.NO_LOCAL_AGENTJAR_ERROR.getStatus(e));
		}
	}
	
	@Override
	public ILaunch getLaunch(ILaunchConfiguration configuration, String mode)
			throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean buildForLaunch(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(NLS.bind("Launching Jaguar", configuration.getName()), 2);
		if (monitor.isCanceled()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean finalLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preLaunchCheck(ILaunchConfiguration configuration,
			String mode, IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		return false;
	}

}
