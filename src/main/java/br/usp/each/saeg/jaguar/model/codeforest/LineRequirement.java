package br.usp.each.saeg.jaguar.model.codeforest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "lineRequirement")
public class LineRequirement extends Requirement {

	@Override
	public Type getType() {
		return Type.LINE;
	}

}
