package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.geoEngine.collision.CollisionIntention;
import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_UPDATE;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.DashStatus;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.geo.GeoService;

/**
 * @author Sarynth, modified Bobobear
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MoveBehindEffect")
public class MoveBehindEffect extends DamageEffect {

    @Override
    public void applyEffect(Effect effect) {
        super.applyEffect(effect);
    }

    @Override
    public void calculate(Effect effect) {
        if (effect.getEffected() == null) {
            return;
        }
        if (!(effect.getEffector() instanceof Player)) {
            return;
        }

        final Player effector = (Player) effect.getEffector();
        final Creature effected = effect.getEffected();

        double radian = Math.toRadians(MathUtil.convertHeadingToDegree(effected.getHeading()));
        float x1 = (float) (Math.cos(Math.PI + radian) * 1.3F);
        float y1 = (float) (Math.sin(Math.PI + radian) * 1.3F);
        float z = GeoService.getInstance().getZAfterMoveBehind(effected.getWorldId(), effected.getX() + x1, effected.getY() + y1, effected.getZ(), effected.getInstanceId());
        byte intentions = (byte) (CollisionIntention.PHYSICAL.getId() | CollisionIntention.DOOR.getId());
        Vector3f closestCollision = GeoService.getInstance().getClosestCollision(effector, effected.getX() + x1, effected.getY() + y1, z, false, intentions);

        //stop moving
        effected.getMoveController().abortMove();

        // Deselect targets
        PacketSendUtility.sendPacket(effector, new SM_TARGET_UPDATE(effector));

        // Move Effector to Effected
        effect.setDashStatus(DashStatus.MOVEBEHIND);
        World.getInstance().updatePosition(effector, closestCollision.getX(), closestCollision.getY(), closestCollision.getZ(), effected.getHeading());
        //set target position for SM_CASTSPELL_RESULT
        effect.getSkill().setTargetPosition(closestCollision.getX(), closestCollision.getY(), closestCollision.getZ(), effected.getHeading());

        if (!super.calculate(effect, DamageType.PHYSICAL)) {
            return;
        }
    }
}
