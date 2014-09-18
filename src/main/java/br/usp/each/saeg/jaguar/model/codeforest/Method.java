package br.usp.each.saeg.jaguar.model.codeforest;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "class")
@XmlSeeAlso({DuaRequirement.class,LineRequirement.class})
public class Method extends SuspiciousElement {

	private Integer id;
	private Integer position;
	private Collection<Requirement> requirement = new ArrayList<Requirement>();

	@Override
	public Collection<Requirement> getChildren() {
		return getRequirement();
	}
	
	@XmlAttribute
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@XmlAttribute
	public Double getMethodsusp() {
		return suspiciousValue;
	}

//	@XmlElements({
//	    @XmlElement(name="line", type=LineRequirement.class),
//	    @XmlElement(name="dua", type=DuaRequirement.class)
//	})
	
	@XmlElement
	public Collection<Requirement> getRequirement() {
		return requirement;
	}
	
	public void setRequirement(Collection<Requirement> requirement) {
		this.requirement = requirement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((requirement == null) ? 0 : requirement.hashCode());
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
		Method other = (Method) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (requirement == null) {
			if (other.requirement != null)
				return false;
		} else if (!requirement.equals(other.requirement))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Method [id=" + id + ", position=" + position + ", requirementList=" + requirement + ", name=" + name
				+ ", number=" + number + ", location=" + location + ", suspiciousValue=" + suspiciousValue + "]";
	}

}
