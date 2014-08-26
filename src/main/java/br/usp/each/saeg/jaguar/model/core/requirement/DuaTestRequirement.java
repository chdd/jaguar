package br.usp.each.saeg.jaguar.model.core.requirement;

import java.util.Set;

public class DuaTestRequirement extends AbstractTestRequirement {

	private Set<Integer> def;
	private Set<Integer> use;
	private Set<Integer> target;
	private String var;

	public DuaTestRequirement(String className, Set<Integer> def, Set<Integer> use, Set<Integer> target, String var) {
		super();
		this.className = className;
		this.def = def;
		this.use = use;
		this.target = target;
		this.var = var;
	}
	
	public Type getType(){
		return Type.DUA;
	}

	public Set<Integer> getDef() {
		return def;
	}

	public void setDef(Set<Integer> def) {
		this.def = def;
	}

	public Set<Integer> getUse() {
		return use;
	}

	public void setUse(Set<Integer> use) {
		this.use = use;
	}

	public Set<Integer> getTarget() {
		return target;
	}

	public void setTarget(Set<Integer> target) {
		this.target = target;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;//super.hashCode() test dos hashcode iguais falhava
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((def == null) ? 0 : def.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((use == null) ? 0 : use.hashCode());
		result = prime * result + ((var == null) ? 0 : var.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)//!super.equals(obj) era isso e os testes passavam quando nao era para passar
			return false;
		if (getClass() != obj.getClass())
			return false;
		DuaTestRequirement other = (DuaTestRequirement) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (def == null) {
			if (other.def != null)
				return false;
		} else if (!def.equals(other.def))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (use == null) {
			if (other.use != null)
				return false;
		} else if (!use.equals(other.use))
			return false;
		if (var == null) {
			if (other.var != null)
				return false;
		} else if (!var.equals(other.var))
			return false;
		return true;
	}
}
