package com.aionemu.gameserver.dataholders;

import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTab;
import com.aionemu.gameserver.model.templates.arcadeupgrade.ArcadeTabItem;

import gnu.trove.map.hash.TIntObjectHashMap;

/**
 * @author Raziel
 */
@XmlRootElement(name = "arcadelist")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArcadeUpgradeData {

	@XmlElement(name = "tab")
	private List<ArcadeTab> arcadeTabTemplate;
	private TIntObjectHashMap<List<ArcadeTabItem>> arcadeItemList = new TIntObjectHashMap<>();

	/**
	 * @param u
	 * @param parent
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) {
		arcadeItemList.clear();
		for (ArcadeTab template : arcadeTabTemplate)
			arcadeItemList.put(template.getId(), template.getArcadeTabItems());
	}

	public int size() {
		return arcadeItemList.size();
	}

	public List<ArcadeTabItem> getArcadeTabById(int id) {
		return arcadeItemList.get(id);
	}

	public List<ArcadeTab> getArcadeTabs() {
		return arcadeTabTemplate;
	}
}
