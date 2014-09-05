package br.usp.each.saeg.jaguar.runner;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.Baduino;
import br.usp.each.saeg.jaguar.Jaguar;
import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.heuristic.impl.TarantulaHeuristic;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

/**
 * JUnit Runner for fault localization.
 * <p>
 * 
 * This runner will search recursively for JUnits tests in all classes within
 * the current directory. Then will run the tests, collecting coverage
 * information using a jacocoagent running as tcpserver. Finally, will print a
 * list of tests requirements order by suspiciousness.
 * <p>
 * 
 * It is mandatory to run using the following VM arguments:
 * -javaagent:{jacoco_agent_path}/jacocoagent.jar=output=tcpserver
 * 
 * @author Henrique Ribeiro
 * 
 */
public class BaduinoSuite extends Suite {

	private JacocoTCPClient tcpClient;
	private Baduino baduino;
	private File targetDir;

	/**
	 * Constructor.
	 * 
	 * @param clazz
	 *            * the class calling the runner
	 * @throws InitializationError
	 * @throws ClassNotFoundException
	 */
	public BaduinoSuite(final Class<?> clazz) throws InitializationError,
	ClassNotFoundException {
		super(clazz, FileUtils.findTestClasses(clazz));
		targetDir = FileUtils.findClassDir(clazz).getParentFile();
	}


	/**
	 * Return the heuristic based on the enum passed on the annotation
	 * {@link JaguarRunnerHeuristic}. If no heuristic is found,
	 * {@link TarantulaHeuristic} is used.
	 * 
	 * @param klass
	 *            class annotated
	 * @return the heuristic
	 * @throws InitializationError
	 */

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#run(org.junit.runner.notification.RunNotifier)
	 */
	@Override
	public void run(final RunNotifier notifier) {
		initializeBeforeTests();
		
		//notifier.addListener(new BaduinoRunListener(baduino, tcpClient));
		
		super.run(notifier);
		
		try {
			baduino.collect(tcpClient.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tearDown();

		baduino.generateXML(baduino.getTestRequirements().values(), FileUtils.findClassDir(this.getClass()));
	}

	private void initializeBeforeTests() {
		baduino = new Baduino(targetDir);
		try {
			tcpClient = new JacocoTCPClient(true);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void tearDown() {
		try {
			tcpClient.closeSocket();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
