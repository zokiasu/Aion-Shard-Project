package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Ever'
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RobotCheckCondition")
public class RobotCheckCondition extends Condition {

    @Override
    public boolean validate(Skill skill) {
        if (skill.getEffector() instanceof Player) {
            Player player = (Player) skill.getEffector();
            if (player.getEquipment().isKeybladeEquipped() && player.getRobotId() != 0) {
                return true;
            }

            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_SKILL_NO_ROBOT);
            return false;
        } else {
            return true;
        }
    }
}
