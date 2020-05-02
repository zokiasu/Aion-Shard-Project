package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.geoEngine.collision.CollisionIntention;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RIDE_ROBOT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_UPDATE;
import com.aionemu.gameserver.skillengine.model.DashStatus;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 * @author Bio
 * @reworked Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RandomMoveLocEffect")
public class RandomMoveLocEffect extends EffectTemplate {

    @XmlAttribute(name = "distance")
    private float distance;
    @XmlAttribute(name = "direction")
    private float direction;

    @Override
    public void applyEffect(Effect effect) {
        final Player effector = (Player) effect.getEffector();

        // Deselect targets
        PacketSendUtility.sendPacket(effector, new SM_TARGET_UPDATE(effector));

        Skill skill = effect.getSkill();
        World.getInstance().updatePosition(effector, skill.getX(), skill.getY(), skill.getZ(), skill.getH());
    }

    @Override
    public void calculate(Effect effect) {
        effect.addSucessEffect(this);
        if (((Player) effect.getEffector()).getRobotId() != 0) {
            if (effect.getSkill().getSkillId() == 3818 || effect.getSkill().getSkillId() == 3853){
                effect.setDashStatus(DashStatus.RANDOMMOVELOC);
            }else{
                effect.setDashStatus(DashStatus.ROBOTMOVELOC);
            }
        } else {
            effect.setDashStatus(DashStatus.RANDOMMOVELOC);
        }

        final Player effector = (Player) effect.getEffector();
        if(effect.getSkill().getSkillId() == 3818 || effect.getSkill().getSkillId() == 3853){
            effector.setRobotId(0);
            PacketSendUtility.broadcastPacketAndReceive(effector, new SM_RIDE_ROBOT(effector.getObjectId(), effector.getRobotId()));
        }

        // Move Effector backwards direction=1 or frontwards direction=0
        double radian = Math.toRadians(MathUtil.convertHeadingToDegree(effector.getHeading()));
        float x1 = (float) (Math.cos(Math.PI * direction + radian) * distance);
        float y1 = (float) (Math.sin(Math.PI * direction + radian) * distance);
        float targetZ = GeoService.getInstance().getZ(effector.getWorldId(), effector.getX() + x1, effector.getY() + y1, effector.getZ() + 1.5f, 0.2f, effector.getInstanceId());
        byte intentions = (byte) (CollisionIntention.PHYSICAL.getId() | CollisionIntention.DOOR.getId());

        Vector3f closestCollision = GeoService.getInstance().getClosestCollision(effector, effector.getX() + x1, effector.getY() + y1, targetZ, false, intentions);
        effect.getSkill().setTargetPosition(closestCollision.getX(), closestCollision.getY(), closestCollision.getZ(), effector.getHeading());
    }
}
