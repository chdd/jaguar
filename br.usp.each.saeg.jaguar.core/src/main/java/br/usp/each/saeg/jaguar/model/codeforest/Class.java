package br.usp.each.saeg.jaguar.model.codeforest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "class")
public class Class extends SuspiciousElement {

	private Collection<Method> methods = new ArrayList<Method>();

	@Override
	public Collection<Method> getChildren() {
		return getMethods();
	}
	
	@XmlElement(name = "method")
	public Collection<Method> getMethods() {
		return methods;
	}

	public void setMethods(Collection<Method> methods) {
		this.methods = methods;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((methods == null) ? 0 : methods.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Class other = (Class) obj;
		if (methods == null) {
			if (other.methods != null)
				return false;
		} else if (!methods.equals(other.methods))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Class [methodList=" + methods + ", name=" + name
				+ ", number=" + number + ", location=" + location
				+ ", suspiciousValue=" + suspiciousValue + "]";
	}


}
