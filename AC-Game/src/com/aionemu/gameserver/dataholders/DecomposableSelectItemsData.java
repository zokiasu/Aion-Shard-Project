package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.templates.decomposable.DecomposableSelectItem;
import com.aionemu.gameserver.model.templates.decomposable.SelectItems;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


@XmlRootElement(name = "decomposable_selectitems")
@XmlAccessorType(XmlAccessType.FIELD)
public class DecomposableSelectItemsData {

    @XmlElement(name = "decomposable_selectitem", required = true)
    protected List<DecomposableSelectItem> selectItems;

    @SuppressWarnings({"unchecked", "rawtypes"})
    private HashMap<Integer, HashMap<PlayerClass, SelectItems>> selectItemData = new HashMap();

    /**
     * @param u
     * @param parent
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (Iterator i$ = this.selectItems.iterator(); i$.hasNext(); ) {
            DecomposableSelectItem item;
            item = (DecomposableSelectItem) i$.next();
            if (item.getItems() != null) {
                if (!this.selectItemData.containsKey(Integer.valueOf(item.getItemId()))) {
                    this.selectItemData.put(Integer.valueOf(item.getItemId()), new HashMap());
                }
                for (SelectItems its : item.getItems()) {
                    ((HashMap) this.selectItemData.get(Integer.valueOf(item.getItemId()))).put(its.getPlayerClass(), its);
                }
            }
        }

        this.selectItems.clear();
        this.selectItems = null;
    }

    @SuppressWarnings("rawtypes")
    public SelectItems getSelectItem(PlayerClass playerClass, int itemid) {
        if (this.selectItemData.containsKey(Integer.valueOf(itemid))) {
            if (((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).containsKey(playerClass)) {
                return (SelectItems) ((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).get(playerClass);
            }
            if (((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).containsKey(PlayerClass.ALL)) {
                return (SelectItems) ((HashMap) this.selectItemData.get(Integer.valueOf(itemid))).get(PlayerClass.ALL);
            }
            return null;
        }
        return null;
    }

    public int size() {
        return selectItemData.size();
    }

}
