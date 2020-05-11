package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author GiGatR00n
 */
@XmlType(name = "ScrollItemLocationList")
public class ScrollItemLocationList {

	@XmlAttribute(name = "worldid")
	protected int worldid;
	
	@XmlAttribute(name = "desc")
	protected String desc;

	public final int getWorldId() {
		return worldid;
	}

	public final String getDesc() {
		return desc;
	}
}