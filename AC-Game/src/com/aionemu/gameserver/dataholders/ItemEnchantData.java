package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.item.ItemEnchantTemplate;
import gnu.trove.map.hash.TIntObjectHashMap;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "enchant_templates")
public class ItemEnchantData {

    @XmlElement(name = "enchant_template", required = true)
    protected List<ItemEnchantTemplate> enchantTemplates;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @XmlTransient
    private TIntObjectHashMap<ItemEnchantTemplate> authorizes = new TIntObjectHashMap();

    void afterUnmarshal(@SuppressWarnings("unused") Unmarshaller u, @SuppressWarnings("unused") Object parent) {
        for (ItemEnchantTemplate it : this.enchantTemplates) {
            getEnchantMap().put(it.getId(), it);
        }
    }

    private TIntObjectHashMap<ItemEnchantTemplate> getEnchantMap() {
        return this.authorizes;
    }

    public ItemEnchantTemplate getEnchantTemplate(int id) {
        return (ItemEnchantTemplate) this.authorizes.get(id);
    }

    public int size() {
        return this.authorizes.size();
    }
}
