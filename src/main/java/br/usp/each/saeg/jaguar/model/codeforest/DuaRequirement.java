package br.usp.each.saeg.jaguar.model.codeforest;

import java.util.Set;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "duaRequirement")
public class DuaRequirement extends Requirement {

	private Set<Integer> def;
	private Set<Integer> use;
	private Set<Integer> target;
	private String var;
	private Boolean covered;
	
	@Override
	public Type getType() {
		return Type.DUA;
	}

	@XmlAttribute
	public Set<Integer> getDef() {
		return def;
	}

	public void setDef(Set<Integer> def) {
		this.def = def;
	}

	@XmlAttribute
	public Set<Integer> getUse() {
		return use;
	}

	public void setUse(Set<Integer> use) {
		this.use = use;
	}

	@XmlAttribute
	public Set<Integer> getTarget() {
		return target;
	}

	public void setTarget(Set<Integer> target) {
		this.target = target;
	}

	@XmlAttribute
	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@XmlAttribute
	public Boolean getCovered() {
		return covered;
	}

	public void setCovered(Boolean covered) {
		this.covered = covered;
	}
	
	
	
}
