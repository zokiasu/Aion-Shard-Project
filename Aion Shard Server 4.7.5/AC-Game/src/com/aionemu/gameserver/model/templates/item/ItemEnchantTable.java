package com.aionemu.gameserver.model.templates.item;

import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.aionemu.gameserver.model.stats.calc.functions.StatFunction;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ItemEnchantTable")
public class ItemEnchantTable {

	@XmlAttribute(name = "id")
    private int id;
	@XmlAttribute(name = "type")
    private String type;
	@XmlAttribute(name = "part")
    private String part;
    @XmlElement(name = "item_enchant", required = false)
    private List<ItemEnchantBonus> item_enchant;
    @SuppressWarnings({"rawtypes", "unchecked"})
    @XmlTransient
    private TIntObjectHashMap<List<StatFunction>> enchants = new TIntObjectHashMap();

    public List<StatFunction> getStats(int level) {
        for (ItemEnchantBonus ib : getItemEnchant()) {
        	if (ib.getLevel() != level)
        		continue;
        	
        	return (List<StatFunction>) ib.getModifiers();
        }
        return null;
    }

    public List<ItemEnchantBonus> getItemEnchant() {
        return this.item_enchant;
    }

    public int getId() {
        return this.id;
    }
    
    public String getType() {
    	return this.type;
    }
    
    public String getPart() {
    	return this.part;
    }
    
}
