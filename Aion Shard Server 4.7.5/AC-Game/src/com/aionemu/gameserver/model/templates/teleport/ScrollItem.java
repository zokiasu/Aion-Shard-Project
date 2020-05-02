package com.aionemu.gameserver.model.templates.teleport;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.templates.teleport.ScrollItemLocationList;

/**
 * @author GiGatR00n
 */
@XmlType(name = "ScrollItem")
public class ScrollItem {

	@XmlAttribute(name = "id")
	private int id;
	
	@XmlAttribute(name = "name")
	private String name;
	
	@XmlElement(name = "loc")
	private List<ScrollItemLocationList> LocationList;

    /**
     * @return the Scroll Template Id
     */
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}	
	
	public ScrollItemLocationList getLocDatabyId(int id) {
		
		if (LocationList != null) {
			return LocationList.get(id);
		}
		return null;
	}		
	
    /**
     * @return the Locations List of the specified Scroll
     */	
	public List<ScrollItemLocationList> getLocationList() {
		return LocationList;
	}
}
