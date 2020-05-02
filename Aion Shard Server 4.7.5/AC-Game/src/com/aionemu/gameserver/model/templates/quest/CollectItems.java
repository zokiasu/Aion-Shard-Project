package com.aionemu.gameserver.model.templates.quest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MrPoke
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollectItems", propOrder = {"collectItem"})
public class CollectItems {

    @XmlElement(name = "collect_item")
    protected List<CollectItem> collectItem;

    /**
     * Gets the value of the collectItem property.
     * <p/>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the collectItem property.
     * <p/>
     * For example, to add a new item, do as follows:
     * <p/>
     * <pre>
     * getCollectItem().add(newItem);
     * </pre>
     * <p/>
     * Objects of the following type(s) are allowed in the list {@link CollectItem
     * }
     */
    public List<CollectItem> getCollectItem() {
        if (collectItem == null) {
            collectItem = new ArrayList<CollectItem>();
        }
        return this.collectItem;
    }
}
