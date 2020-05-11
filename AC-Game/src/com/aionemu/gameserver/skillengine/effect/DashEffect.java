package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.DashStatus;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.world.World;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DashEffect")
public class DashEffect extends DamageEffect {

    @Override
    public void applyEffect(Effect effect) {
        super.applyEffect(effect);
        final Player effector = (Player) effect.getEffector();

        // Move Effector to Effected
        Skill skill = effect.getSkill();
        World.getInstance().updatePosition(effector, skill.getX(), skill.getY(), skill.getZ(), skill.getH());
    }

    @Override
    public void calculate(Effect effect) {
        if (effect.getEffected() == null) {
            return;
        }
        if (!(effect.getEffector() instanceof Player)) {
            return;
        }

        if (!super.calculate(effect, DamageType.PHYSICAL)) {
            return;
        }

        final Player effector = (Player) effect.getEffector();

        Creature effected = effect.getEffected();
        effect.setDashStatus(DashStatus.DASH);

        if(effector.getTarget() == effected) {
            effect.getSkill().setTargetPosition(effected.getX(), effected.getY(), effected.getZ(), effected.getHeading());
        } else {
            effect.getSkill().setTargetPosition(effector.getTarget().getX(), effector.getTarget().getY(), effector.getTarget().getZ(), effector.getTarget().getHeading());
        }
    }
}
