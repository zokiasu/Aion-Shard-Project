package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Rolandas
 */
@XmlType(name = "ApExtractTarget")
@XmlEnum
public enum ApExtractTarget {

    ACCESSORY,
    ARMOR,
    EQUIPMENT,
    WEAPON,
    WING,
    OTHER,
    ALL;

    public String value() {
        return name();
    }

    public static ApExtractTarget fromValue(String v) {
        return valueOf(v);
    }
}
