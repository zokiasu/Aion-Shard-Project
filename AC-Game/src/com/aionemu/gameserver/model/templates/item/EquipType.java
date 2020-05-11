package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlType(name = "equipType")
@XmlEnum
public enum EquipType {

    ARMOR,
    WEAPON,
    STIGMA,
    NONE;

    public String value() {
        return name();
    }

    public static EquipType fromValue(String v) {
        return valueOf(v);
    }
}
