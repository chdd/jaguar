package br.usp.each.saeg.jaguar.codeforest.model;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlAttribute;

public abstract class Requirement extends SuspiciousElement {
	
	public enum Type {
		LINE, DUA;
	}

	@XmlAttribute
	public abstract Type getType();
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<? extends SuspiciousElement> getChildren() {
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public String toString() {
		return "Requirement [name=" + name + ", number=" + number
				+ ", location=" + location + ", suspiciousValue="
				+ suspiciousValue + "]";
	}

}
