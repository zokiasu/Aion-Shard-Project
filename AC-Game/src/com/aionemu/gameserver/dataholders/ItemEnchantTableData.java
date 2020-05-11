package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemCategory;
import com.aionemu.gameserver.model.templates.item.ItemEnchantTable;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "enchant_tables")
public class ItemEnchantTableData {

	@XmlElement(name = "enchant_table", required = true)
    protected List<ItemEnchantTable> enchantTables;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @XmlTransient
    private TIntObjectHashMap<ItemEnchantTable> enchants = new TIntObjectHashMap();

    void afterUnmarshal(Unmarshaller u, Object parent) {
        for (ItemEnchantTable it : this.enchantTables) {
            getEnchantMap().put(it.getId(), it);
        }
    }

    private TIntObjectHashMap<ItemEnchantTable> getEnchantMap() {
        return this.enchants;
    }

    public ItemEnchantTable getTableWeapon(ItemCategory cType) {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType().equalsIgnoreCase(cType.toString())) {
    			return (ItemEnchantTable) it;
    		} 
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTableArmor(ArmorType aType, ItemCategory cType) {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getPart() == null)
    			continue;
    		else if (aType == ArmorType.NO_ARMOR) 
    			continue;
            else if (cType == ItemCategory.SHARD)
                continue;
    		if (it.getType().equalsIgnoreCase(aType.toString()) && it.getPart().equalsIgnoreCase(cType.toString())) {
    			return (ItemEnchantTable) it;
    		} 
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTablePlume() {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType() != "PLUME") {
    			continue;
    		}
    		return (ItemEnchantTable) it;
    		
    	}
    	return null;
    }
    
    public ItemEnchantTable getTableAuthorize() {
    	for (ItemEnchantTable it : this.enchantTables){
    		if (it.getType() != "AUTHORIZE") {
    			continue;
    		}
    		return (ItemEnchantTable) it;
    		
    	}
    	return null;
    }

    public int size() {
        return this.enchants.size();
    }
}
