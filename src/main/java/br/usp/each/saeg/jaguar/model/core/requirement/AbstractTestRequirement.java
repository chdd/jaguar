package br.usp.each.saeg.jaguar.model.core.requirement;

public abstract class AbstractTestRequirement implements Comparable<AbstractTestRequirement> {

	protected String className;
	private Integer classFirstLine;

	private Integer methodId;
	private Integer methodLine;
	private String methodSignature;
	
	private int cef = 0;
	private int cep = 0;
	private Double suspiciousness; 

	public enum Type{
		LINE, DUA;
	}
	
	public abstract Type getType();
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getClassFirstLine() {
		return classFirstLine;
	}

	public void setClassFirstLine(Integer classFirstLine) {
		this.classFirstLine = classFirstLine;
	}

	public Integer getMethodId() {
		return methodId;
	}

	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}

	public Integer getMethodLine() {
		return methodLine;
	}

	public void setMethodLine(Integer methodLine) {
		this.methodLine = methodLine;
	}

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public void increaseFailed(){
		cef++;
	}
	
	public int getCef() {
		return cef;
	}

	public void increasePassed(){
		cep++;
	}

	public int getCep() {
		return cep;
	}
	
	public double getSuspiciousness() {
		return suspiciousness;
	}

	public void setSuspiciousness(double suspiciousness) {
		this.suspiciousness = suspiciousness;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTestRequirement other = (AbstractTestRequirement) obj;
		if (cef != other.cef)
			return false;
		if (cep != other.cep)
			return false;
		if (classFirstLine == null) {
			if (other.classFirstLine != null)
				return false;
		} else if (!classFirstLine.equals(other.classFirstLine))
			return false;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (methodId == null) {
			if (other.methodId != null)
				return false;
		} else if (!methodId.equals(other.methodId))
			return false;
		if (methodLine == null) {
			if (other.methodLine != null)
				return false;
		} else if (!methodLine.equals(other.methodLine))
			return false;
		if (methodSignature == null) {
			if (other.methodSignature != null)
				return false;
		} else if (!methodSignature.equals(other.methodSignature))
			return false;
		if (suspiciousness == null) {
			if (other.suspiciousness != null)
				return false;
		} else if (!suspiciousness.equals(other.suspiciousness))
			return false;
		return true;
	}

	public int compareTo(AbstractTestRequirement o) {
		return this.suspiciousness.compareTo(o.getSuspiciousness());
	}

}
