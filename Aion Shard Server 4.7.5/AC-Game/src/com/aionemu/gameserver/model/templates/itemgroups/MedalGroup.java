package com.aionemu.gameserver.model.templates.itemgroups;

import com.aionemu.gameserver.model.templates.rewards.MedalItem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luzien
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MedalGroup")
public class MedalGroup extends BonusItemGroup {

    @XmlElement(name = "item")
    protected List<MedalItem> items;

    public List<MedalItem> getItems() {
        if (items == null) {
            items = new ArrayList<MedalItem>();
        }
        return this.items;
    }

    @Override
    public ItemRaceEntry[] getRewards() {
        return getItems().toArray(new ItemRaceEntry[0]);
    }
}
