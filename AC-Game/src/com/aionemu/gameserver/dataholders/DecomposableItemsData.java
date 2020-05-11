package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.item.DecomposableItemInfo;
import com.aionemu.gameserver.model.templates.item.ExtractedItemsCollection;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author antness
 */
@XmlRootElement(name = "decomposable_items")
@XmlAccessorType(XmlAccessType.FIELD)
public class DecomposableItemsData {

    @XmlElement(name = "decomposable")
    private List<DecomposableItemInfo> decomposableItemsTemplates;
    private TIntObjectHashMap<List<ExtractedItemsCollection>> decomposableItemsInfo = new TIntObjectHashMap<List<ExtractedItemsCollection>>();

    /**
     * @param u
     * @param parent
     */
    void afterUnmarshal(Unmarshaller u, Object parent) {
        decomposableItemsInfo.clear();
        for (DecomposableItemInfo template : decomposableItemsTemplates) {
            decomposableItemsInfo.put(template.getItemId(), template.getItemsCollections());
        }
    }

    public int size() {
        return decomposableItemsInfo.size();
    }

    public List<ExtractedItemsCollection> getInfoByItemId(int itemId) {
        return decomposableItemsInfo.get(itemId);
    }
}
