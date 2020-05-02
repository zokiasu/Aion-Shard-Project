package com.aionemu.gameserver.model.templates.item;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnchantInfo")
public class ItemEnchantInfo {

    @XmlAttribute(name = "rnd_enchant")
    private int rnd_enchant;
    @XmlAttribute(name = "wake_level")
    private int wake_level;
    @XmlAttribute(name = "waken_id")
    private int waken_id;

    public int getAwakeLevel() {
        return this.wake_level;
    }

    public int getAwakenId() {
        return this.waken_id;
    }

    public int getRndEnchantLevel() {
        return this.rnd_enchant;
    }
}
