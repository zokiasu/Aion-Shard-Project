package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author LokiReborn
 */
@XmlType(name = "item_category")
@XmlEnum
public enum ItemCategory {

    MANASTONE,
    SPECIAL_MANASTONE,
    GODSTONE,
    ENCHANTMENT,
    FLUX,
    BALIC_EMOTION,
    BALIC_MATERIAL,
    RAWHIDE,
    SOULSTONE,
    RECIPE,
    GATHERABLE,
    GATHERABLE_BONUS,
    DROP_MATERIAL,
    SWORD,
    DAGGER,
    MACE,
    ORB,
    SPELLBOOK,
    GREATSWORD,
    POLEARM,
    STAFF,
    BOW,
    SHIELD,
    HARP,
    GUN,
    CANNON,
    KEYBLADE,
    JACKET,
    PANTS,
    SHARD,
    SHOES,
    GLOVES,
    SHOULDERS,
    NECKLACE,
    EARRINGS,
    RINGS,
    HELMET,
    BELT,
    SKILLBOOK,
    STIGMA,
    COINS,
    MEDALS,
    QUEST,
    KEY,
    TEMPERING,
    CRAFT_BOOST,
    COMBINATION,
    PLUME,
    NONE
}
