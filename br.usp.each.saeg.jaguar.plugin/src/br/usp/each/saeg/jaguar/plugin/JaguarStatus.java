package br.usp.each.saeg.jaguar.plugin;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.osgi.util.NLS;

/**
 * Status objects used by the core plug-in.
 */
public final class JaguarStatus {

  public final int code;

  public final int severity;

  public final String message;

  private JaguarStatus(int code, int severity, String message) {
    this.code = code;
    this.severity = severity;
    this.message = message;
  }

  public IStatus getStatus() {
    String m = NLS.bind(message, Integer.valueOf(code));
    return new Status(severity, JaguarUIPlugin.PLUGIN_ID, code, m, null);
  }

  public IStatus getStatus(Throwable t) {
    String m = NLS.bind(message, Integer.valueOf(code));
    return new Status(severity, JaguarUIPlugin.PLUGIN_ID, code, m, t);
  }

  public IStatus getStatus(Object param1, Throwable t) {
    String m = NLS.bind(message, Integer.valueOf(code), param1);
    return new Status(severity, JaguarUIPlugin.PLUGIN_ID, code, m, t);
  }

  public IStatus getStatus(Object param1, Object param2, Throwable t) {
    String m = NLS.bind(message, new Object[] { Integer.valueOf(code), param1,
        param2 });
    return new Status(severity, JaguarUIPlugin.PLUGIN_ID, code, m, t);
  }

  public IStatus getStatus(Object param1) {
    String m = NLS.bind(message, Integer.valueOf(code), param1);
    return new Status(severity, JaguarUIPlugin.PLUGIN_ID, code, m, null);
  }

  /**
   * Status indicating that it was not possible to obtain a local version of the
   * runtime agent file.
   */
  public static final JaguarStatus NO_LOCAL_AGENTJAR_ERROR = new JaguarStatus(
      5000, IStatus.ERROR, CoreMessages.StatusNO_LOCAL_AGENTJAR_ERROR_message);

  /**
   * The requested launch type is not known.
   */
  public static final JaguarStatus UNKOWN_LAUNCH_TYPE_ERROR = new JaguarStatus(
      5002, IStatus.ERROR, CoreMessages.StatusUNKOWN_LAUNCH_TYPE_ERROR_message);

  /**
   * The execution data file can not be created.
   */
  public static final JaguarStatus EXEC_FILE_CREATE_ERROR = new JaguarStatus(
      5004, IStatus.ERROR, CoreMessages.StatusEXEC_FILE_CREATE_ERROR_message);

  /**
   * Error while reading coverage data file.
   */
  public static final JaguarStatus EXEC_FILE_READ_ERROR = new JaguarStatus(
      5005, IStatus.ERROR, CoreMessages.StatusEXEC_FILE_READ_ERROR_message);

  /**
   * Error while reading coverage data file.
   */
  public static final JaguarStatus AGENT_CONNECT_ERROR = new JaguarStatus(
      5006, IStatus.ERROR, CoreMessages.StatusAGENT_CONNECT_ERROR_message);

  /**
   * Error while analyzing a bundle of class file.
   */
  public static final JaguarStatus BUNDLE_ANALYSIS_ERROR = new JaguarStatus(
      5007, IStatus.ERROR, CoreMessages.StatusBUNDLE_ANALYSIS_ERROR_message);

  /**
   * Error while extracting coverage session.
   */
  public static final JaguarStatus EXPORT_ERROR = new JaguarStatus(5008,
      IStatus.ERROR, CoreMessages.StatusEXPORT_ERROR_message);

  /**
   * Error while importing external coverage session.
   */
  public static final JaguarStatus IMPORT_ERROR = new JaguarStatus(5009,
      IStatus.ERROR, CoreMessages.StatusIMPORT_ERROR_message);

  /**
   * Error while starting the agent server.
   */
  public static final JaguarStatus AGENTSERVER_START_ERROR = new JaguarStatus(
      5011, IStatus.ERROR, CoreMessages.StatusAGENTSERVER_START_ERROR_message);

  /**
   * Error while stopping the agent server.
   */
  public static final JaguarStatus AGENTSERVER_STOP_ERROR = new JaguarStatus(
      5012, IStatus.ERROR, CoreMessages.StatusAGENTSERVER_STOP_ERROR_message);

  /**
   * Error while dumping coverage data.
   */
  public static final JaguarStatus EXECDATA_DUMP_ERROR = new JaguarStatus(
      5013, IStatus.ERROR, CoreMessages.StatusEXECDATA_DUMP_ERROR_message);

  /**
   * Error while requesting an execution data dump.
   */
  public static final JaguarStatus DUMP_REQUEST_ERROR = new JaguarStatus(
      5014, IStatus.ERROR, CoreMessages.StatusDUMP_REQUEST_ERROR_message);

  /**
   * No coverage data file has been created during a coverage launch. This
   * status is used to issue an error prompt.
   */
  public static final JaguarStatus NO_COVERAGE_DATA_ERROR = new JaguarStatus(
      5101, IStatus.ERROR, CoreMessages.StatusNO_COVERAGE_DATA_ERROR_message);

}
