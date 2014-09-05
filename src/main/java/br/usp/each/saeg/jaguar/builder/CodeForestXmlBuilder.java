package br.usp.each.saeg.jaguar.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.usp.each.saeg.jaguar.heuristic.Heuristic;
import br.usp.each.saeg.jaguar.model.codeforest.Class;
import br.usp.each.saeg.jaguar.model.codeforest.DuaRequirement;
import br.usp.each.saeg.jaguar.model.codeforest.FaultClassification;
import br.usp.each.saeg.jaguar.model.codeforest.LineRequirement;
import br.usp.each.saeg.jaguar.model.codeforest.Method;
import br.usp.each.saeg.jaguar.model.codeforest.Package;
import br.usp.each.saeg.jaguar.model.codeforest.Requirement;
import br.usp.each.saeg.jaguar.model.codeforest.SuspiciousElement;
import br.usp.each.saeg.jaguar.model.codeforest.TestCriteria;
import br.usp.each.saeg.jaguar.model.core.requirement.AbstractTestRequirement;
import br.usp.each.saeg.jaguar.model.core.requirement.DuaTestRequirement;
import br.usp.each.saeg.jaguar.model.core.requirement.LineTestRequirement;

public class CodeForestXmlBuilder {

	private Integer methodPosition = 1;
	private String project;
	private Heuristic heuristic;
	private Requirement.Type requirementType;
	private Map<Integer, Package> packageMap = new HashMap<Integer, Package>();

	public CodeForestXmlBuilder() {
		super();
	}

	/**
	 * Set the project name.
	 */
	public void project(String project) {
		this.project = project;
	}

	/**
	 * Set the Heuristic used to calculate the suspicious value.
	 */
	public void heuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}

	/**
	 * Set the type of requirement (e.g Line, Node, Dua)
	 */
	public void requirementType(Requirement.Type requirementType) {
		this.requirementType = requirementType;
	}

	/**
	 * Add the test requirement to the code forest structure.
	 */
	public void addTestRequirement(AbstractTestRequirement testRequirement) {
		addRequirement(
				testRequirement,
				addMethod(testRequirement,
						addClass(testRequirement, addPackage(testRequirement))));
	}

	/**
	 * If the package does not exist, create. If it already exist, return it.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @return the package
	 */
	private Package addPackage(AbstractTestRequirement testRequirement) {
		String packageName = getPackageName(testRequirement.getClassName());
		Package currentPackage = packageMap.get(packageName.hashCode());
		if (currentPackage == null) {
			currentPackage = new Package();
			currentPackage.setName(packageName);
			packageMap.put(packageName.hashCode(), currentPackage);
		}
		return currentPackage;
	}

	/**
	 * If the class does not exist, create and add it to the given package. If
	 * it already exist, only add it to the package.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param pakkage
	 *            the package to add the class
	 * @return
	 */
	private Class addClass(AbstractTestRequirement testRequirement,
			Package pakkage) {
		String className = replaceSlashByDot(testRequirement.getClassName());
		Class currentClass = null;
		for (Class clazz : pakkage.getClasses()) {
			if (clazz.getName().equals(className)) {
				currentClass = clazz;
			}
		}

		if (currentClass == null) {
			currentClass = new Class();
			currentClass.setName(className);
			currentClass.setLocation(new Integer(testRequirement
					.getClassFirstLine()));
			pakkage.getClasses().add(currentClass);
		}
		return currentClass;
	}

	/**
	 * If the method does not exist, create and add it to the given class. If it
	 * already exist, only add it to the class.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param currentClass
	 *            the class to add the method.
	 * 
	 * @return return the method
	 */
	private Method addMethod(AbstractTestRequirement testRequirement,
			Class currentClass) {
		String methodName = testRequirement.getMethodSignature();
		Method currentMethod = null;
		for (Method method : currentClass.getMethods()) {
			if (method.getName().equals(methodName)) {
				currentMethod = method;
			}
		}

		if (currentMethod == null) {
			currentMethod = new Method();
			currentMethod.setName(methodName);
			currentMethod.setId(testRequirement.getMethodId());
			currentMethod.setPosition(methodPosition++);
			currentClass.getMethods().add(currentMethod);
		}
		currentMethod.setLocation(testRequirement.getMethodLine());
		return currentMethod;
	}

	/**
	 * Create a requirement and add it to the given method.
	 * 
	 * @param testRequirement
	 *            the test requirement holding the requirement info
	 * @param currentMethod
	 *            the method to add the requirement.
	 */
	private void addRequirement(AbstractTestRequirement testRequirement,
			Method currentMethod) {
		if (testRequirement instanceof DuaTestRequirement) {

			DuaTestRequirement duaRequirement = (DuaTestRequirement) testRequirement;
			DuaRequirement requirement = new DuaRequirement();

			requirement.setDef(duaRequirement.getDef());
			requirement.setUse(duaRequirement.getUse());
			requirement.setTarget(duaRequirement.getTarget());
			requirement.setVar(duaRequirement.getVar());
			requirement.setCovered(duaRequirement.getCovered());

			Integer firstDefLine = duaRequirement.getDef().iterator().next();
			requirement.setName(firstDefLine.toString());
			requirement.setLocation(firstDefLine);
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());

			currentMethod.getRequirements().add(requirement);

		} else if (testRequirement instanceof LineTestRequirement) {

			LineTestRequirement lineRequirement = (LineTestRequirement) testRequirement;
			LineRequirement requirement = new LineRequirement();

			requirement.setName(lineRequirement.getLineNumber().toString());
			requirement.setLocation(lineRequirement.getLineNumber());
			requirement.setSuspiciousValue(testRequirement.getSuspiciousness());

			currentMethod.getRequirements().add(requirement);
		}
	}

	/**
	 * Replace '\' by '.'. and remove last word (the class name).
	 * 
	 * @param className
	 *            class name including package
	 * @return only the package
	 */
	private String getPackageName(String className) {
		className = replaceSlashByDot(className);
		return StringUtils.substringBeforeLast(className, ".");
	}

	private String replaceSlashByDot(String className) {
		return className.replace('/', '.');
	}

	/**
	 * Create the object used to generate the CodeForest xml.
	 */
	public FaultClassification build() {

		for (Package currentPackage : packageMap.values()) {
			setSuspicous(currentPackage);
		}

		TestCriteria testCriteria = new TestCriteria();
		if (heuristic != null) {
			testCriteria.setHeuristicType(StringUtils.upperCase(StringUtils
					.removeEndIgnoreCase(heuristic.getClass().getSimpleName(),
							"heuristic")));
		}
		testCriteria.setRequirementType(requirementType);
		testCriteria.setPackages(packageMap.values());

		FaultClassification faultClassification = new FaultClassification();
		faultClassification.setProject(project);
		faultClassification.setTestCriteria(testCriteria);

		return faultClassification;
	}

	/**
	 * Set the suspicious value based on the children. The object will have its
	 * children maximum suspicious value.
	 * 
	 * @param element
	 */
	private void setSuspicous(SuspiciousElement element) {
		for (SuspiciousElement child : element.getChildren()) {
			setSuspicous(child);
			element.updateSupicousness(child.getSuspiciousValue(),
					child.getNumber());
		}
	}

}
