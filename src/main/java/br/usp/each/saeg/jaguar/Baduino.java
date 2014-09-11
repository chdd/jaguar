package br.usp.each.saeg.jaguar;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.bind.JAXB;

import org.eclipse.jdt.core.Signature;
import org.jacoco.core.analysis.AbstractAnalyzer;
import org.jacoco.core.analysis.DataflowAnalyzer;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.analysis.dua.DuaCoverageBuilder;
import org.jacoco.core.analysis.dua.IDua;
import org.jacoco.core.analysis.dua.IDuaClassCoverage;
import org.jacoco.core.analysis.dua.IDuaMethodCoverage;
import org.jacoco.core.data.AbstractExecutionDataStore;

import br.usp.each.saeg.jaguar.builder.CodeForestXmlBuilder;
import br.usp.each.saeg.jaguar.model.codeforest.Requirement;
import br.usp.each.saeg.jaguar.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.model.core.requirement.DuaTestRequirement;

/**
 * This class store the coverage information received from Jacoco and generate a
 * rank of more suspicious test requirement based on one SFL Heuristic.
 * 
 * @author Henrique Ribeiro
 */
public class Baduino {

	private static final String XML_NAME = "baduino.xml";
	private HashMap<Integer, AbstractTestRequirement> testRequirements = new HashMap<Integer, AbstractTestRequirement>();
	private File classesDir;

	/**
	 * Construct the Jaguar object.
	 * 
	 * @param targetDir
	 *            the target dir created by eclipse
	 */
	public Baduino(File classesDir) {
		this.classesDir = classesDir;
	}

	/**
	 * Receive the coverage information and store it on Test Requiremtns.
	 * 
	 * @param executionData
	 *            the covarege data from Jacoco
	 * @param currentTestFailed
	 *            result of the test
	 * @throws IOException
	 * 
	 */
	public void collect(final AbstractExecutionDataStore executionData)
			throws IOException {
		DuaCoverageBuilder duaCoverageBuilder = new DuaCoverageBuilder();
		AbstractAnalyzer analyzer = new DataflowAnalyzer(executionData,
				duaCoverageBuilder);
		analyzer.analyzeAll(classesDir);
		collectDuaCoverage(duaCoverageBuilder);
	}

	private void collectDuaCoverage(DuaCoverageBuilder coverageVisitor) {
		for (IDuaClassCoverage clazz : coverageVisitor.getClasses()) {
			// System.out.println("class = " + clazz.getName());
			for (IDuaMethodCoverage method : clazz.getMethods()) {
				// System.out.println("metodoDesc = " + method.getDesc() +
				// "metodo");
				for (IDua dua : method.getDuas()) {
					updateRequirement(clazz, method, dua);
				}
			}
		}
	}

	private void updateRequirement(IDuaClassCoverage clazz,
			IDuaMethodCoverage method, IDua dua) {
		AbstractTestRequirement testRequirement = new DuaTestRequirement(
				clazz.getName(), dua.getDef(), dua.getUse(), dua.getTarget(),
				dua.getVar());
		AbstractTestRequirement foundRequirement = testRequirements
				.get(testRequirement.hashCode());

		if (foundRequirement == null) {
			testRequirement.setClassFirstLine(0);
			testRequirement.setMethodLine(dua.getDef());
			testRequirement.setMethodSignature(Signature.toString(
					method.getDesc(), method.getName(), null, false, true));
			testRequirement.setMethodId(method.getId());
			testRequirement.setCovered(dua.getStatus() == ICounter.FULLY_COVERED);
			testRequirements.put(testRequirement.hashCode(), testRequirement);
		} else {
			testRequirement = foundRequirement;
		}

	}

	// TODO javadoc
	/**
	 * 
	 * @param testRequirements
	 * @param projectDir
	 */
	public void generateXML(
			Collection<AbstractTestRequirement> testRequirements, File projectDir) {
		System.out.println("XML generation started.");
		CodeForestXmlBuilder xmlBuilder = createXmlBuilder(testRequirements);
		for (AbstractTestRequirement testRequirement : testRequirements) {
			xmlBuilder.addTestRequirement(testRequirement);
		}
		File xmlFile = new File(projectDir.getAbsolutePath()
				+ System.getProperty("file.separator") + XML_NAME);
		JAXB.marshal(xmlBuilder.build(), xmlFile);
		System.out.println("XML generation finished at: "
				+ xmlFile.getAbsolutePath());
	}

	private CodeForestXmlBuilder createXmlBuilder(
			Collection<AbstractTestRequirement> testRequirements) {
		CodeForestXmlBuilder xmlBuilder = new CodeForestXmlBuilder();
		xmlBuilder.project("fault localization");
		setType(testRequirements, xmlBuilder);
		return xmlBuilder;
	}

	private void setType(Collection<AbstractTestRequirement> testRequirements,
			CodeForestXmlBuilder xmlBuilder) {
		if (testRequirements.isEmpty()) {
			return;
		}

		if (AbstractTestRequirement.Type.LINE == testRequirements.iterator()
				.next().getType()) {
			xmlBuilder.requirementType(Requirement.Type.LINE);
		} else if (AbstractTestRequirement.Type.LINE == testRequirements
				.iterator().next().getType()) {
			xmlBuilder.requirementType(Requirement.Type.DUA);
		}
	}

	public HashMap<Integer, AbstractTestRequirement> getTestRequirements() {
		return testRequirements;
	}
	
}
