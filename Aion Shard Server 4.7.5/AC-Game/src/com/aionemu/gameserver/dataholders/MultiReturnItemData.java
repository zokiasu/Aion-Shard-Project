package com.aionemu.gameserver.dataholders;


import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.aionemu.gameserver.model.templates.teleport.ScrollItem;
import com.aionemu.gameserver.model.templates.teleport.ScrollItemLocationList;
import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author GiGatR00n
 */
@XmlRootElement(name = "item_multi_returns")
@XmlAccessorType(XmlAccessType.FIELD)
public class MultiReturnItemData {

	@XmlElement(name = "item")
	private List<ScrollItem> ItemList;
	
	private TIntObjectHashMap<List<ScrollItemLocationList>> ItemLocationList = new TIntObjectHashMap<>();

	void afterUnmarshal(Unmarshaller u, Object parent) {
		ItemLocationList.clear();
		for (ScrollItem template : ItemList)
			ItemLocationList.put(template.getId(), template.getLocationList());
	}

	public int size() {
		return ItemLocationList.size();
	}

	public ScrollItem getScrollItembyId(int id) {
		
		for (ScrollItem template : ItemList) {
			if (template.getId() == id) {
				return template;
			}
		}
		return null;
	}	
	
	public List<ScrollItem> getScrollItems() {
		return ItemList;
	}
}