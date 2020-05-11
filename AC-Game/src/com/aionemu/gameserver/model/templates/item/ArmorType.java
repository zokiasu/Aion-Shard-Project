package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author ATracer
 */
@XmlType(name = "armor_type")
@XmlEnum
public enum ArmorType {

    NO_ARMOR(new int[]{}),
    CHAIN(new int[]{6, 13}),
    CLOTHES(new int[]{4}),
    LEATHER(new int[]{5, 12}),
    PLATE(new int[]{18}),
    ROBE(new int[]{67, 70}),
    SHIELD(new int[]{7, 14}),
    ARROW(new int[]{}),
    WING(new int[]{}),
    PLUME(new int[]{});
    private int[] requiredSkills;

    private ArmorType(int[] requiredSkills) {
        this.requiredSkills = requiredSkills;
    }

    public int[] getRequiredSkills() {
        return requiredSkills;
    }

    /**
     * @return int
     */
    public int getMask() {
        return 1 << this.ordinal();
    }
}
