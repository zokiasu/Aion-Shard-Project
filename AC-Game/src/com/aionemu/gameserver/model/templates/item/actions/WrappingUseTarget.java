package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "WrappingUseTarget")
@XmlEnum
public enum WrappingUseTarget {

    ACCESSORY,
    ARMOR,
    WEAPON;

    private WrappingUseTarget() {
    }

    public String value() {
        return name();
    }

    public static WrappingUseTarget fromValue(String v) {
        return valueOf(v);
    }
}
