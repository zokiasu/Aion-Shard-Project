package com.aionemu.gameserver.dataholders.loadingutils.adapters;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * @author Luno
 */
public class NpcEquipmentList {

    @XmlElement(name = "item")
    @XmlIDREF
    public ItemTemplate[] items;
}
