package com.aionemu.gameserver.model.templates.beritra;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Beritra")
public class BeritraTemplate
{
	@XmlAttribute(name = "id")
	protected int id;
	
	public int getId() {
		return this.id;
	}
}