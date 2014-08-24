package br.usp.each.saeg.jaguar.model.core.requirement;

public class LineTestRequirement extends AbstractTestRequirement {

	private final Integer lineNumber;

	public LineTestRequirement(String className, Integer lineNumber) {
		super();
		this.lineNumber = lineNumber;
		setClassName(className);
	}

	public Type getType() {
		return Type.LINE;
	}

	public Integer getLineNumber() {
		return lineNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getClassName() == null) ? 0 : getClassName().hashCode());
		result = prime * result + ((lineNumber == null) ? 0 : lineNumber.hashCode());
		return result;
	}

}
