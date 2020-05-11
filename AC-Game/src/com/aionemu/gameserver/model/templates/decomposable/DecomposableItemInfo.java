package com.aionemu.gameserver.model.templates.decomposable;

import com.aionemu.gameserver.model.templates.item.ExtractedItemsCollection;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;


@XmlType(name = "DecomposableItem")
public class DecomposableItemInfo {

    @XmlAttribute(name = "item_id")
    private int itemId;
    @XmlElement(name = "items")
    private List<ExtractedItemsCollection> itemsCollections;

    public int getItemId() {
        return this.itemId;
    }

    public List<ExtractedItemsCollection> getItemsCollections() {
        return this.itemsCollections;
    }
}
