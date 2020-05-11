package com.aionemu.gameserver.model.templates.arcadeupgrade;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Raziel
 */
@XmlType(name = "ArcadeTab")
public class ArcadeTab {

	@XmlAttribute(name = "id")
	private int id;
	@XmlElement(name = "item")
	private List<ArcadeTabItem> arcadeTabItem;

	public int getId() {
		return id;
	}

	public List<ArcadeTabItem> getArcadeTabItems() {
		return arcadeTabItem;
	}
}
