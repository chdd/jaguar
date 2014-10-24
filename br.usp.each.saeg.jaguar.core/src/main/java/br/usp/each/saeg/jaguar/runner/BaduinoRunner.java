package br.usp.each.saeg.jaguar.runner;

import java.io.File;


import org.junit.runner.JUnitCore;
import org.junit.runners.model.InitializationError;

import br.usp.each.saeg.jaguar.Baduino;
import br.usp.each.saeg.jaguar.infra.FileUtils;
import br.usp.each.saeg.jaguar.jacoco.JacocoTCPClient;

/**
 * @author Henrique Ribeiro
 * 
 */
public class BaduinoRunner {

	private final JUnitCore junit = new JUnitCore();

	private final File projectDir;
	private final File sourceDir;
	private final File testDir;

	public BaduinoRunner(File projectDir, File sourceDir, File testDir) throws InitializationError, ClassNotFoundException {
		this.projectDir = projectDir;
		this.sourceDir = sourceDir;
		this.testDir = testDir;
	}

	private void run() throws Exception {
		long time = System.currentTimeMillis();
		
		final Class<?>[] classes = FileUtils.findTestClasses(testDir);

		final Baduino baduino = new Baduino(sourceDir);
		final JacocoTCPClient tcpClient = new JacocoTCPClient();

		junit.run(classes);
		
		baduino.collect(tcpClient.read());
		
		tcpClient.closeSocket();
		baduino.generateXML(baduino.getTestRequirements().values(), projectDir);
		System.out.println("Time elapse: "+((System.currentTimeMillis()-time)/1000)+"s");
	}

	public static void main(String[] args) throws InitializationError, Exception {
		File projectPath = new File(args[0]);
		File sourcePath = new File(args[1]);
		File testPath = new File(args[2]);
		new BaduinoRunner(projectPath, sourcePath, testPath).run();
		System.out.println("End!");
		System.exit(0);
	}

}
