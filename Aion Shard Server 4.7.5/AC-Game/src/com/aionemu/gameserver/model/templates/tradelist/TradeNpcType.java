package com.aionemu.gameserver.model.templates.tradelist;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author namedrisk
 */
@XmlType(name = "npc_type")
@XmlEnum
public enum TradeNpcType {

    NORMAL(1),
    ABYSS(2),
    REWARD(4);
    private final int index;

    private TradeNpcType(int index) {
        this.index = index;
    }

    public int index() {
        return index;
    }
}
