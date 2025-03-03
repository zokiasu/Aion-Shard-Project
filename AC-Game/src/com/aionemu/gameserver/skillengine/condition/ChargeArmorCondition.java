package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;

/**
 * @author Rolandas
 * @modified Cheatkiller
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeArmorCondition")
public class ChargeArmorCondition extends ChargeCondition {

    @Override
    public boolean validate(Skill env) {
        if (env.getEffector() instanceof Player) {
            Player effector = (Player) env.getEffector();
            for (Item item : effector.getEquipment().getEquippedItems()) {
                if (item.getItemTemplate().isArmor() && item.getConditioningInfo() != null) {
                    item.getConditioningInfo().updateChargePoints(-value);
                }
            }
        }
        return true;
    }
}
