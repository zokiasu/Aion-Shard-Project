package com.aionemu.gameserver.skillengine.action;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlType(name = "DamageType")
@XmlEnum
public enum DamageType {

    PHYSICAL,
    MAGICAL;

    public String value() {
        return name();
    }

    public static DamageType fromValue(String v) {
        return valueOf(v);
    }
}
