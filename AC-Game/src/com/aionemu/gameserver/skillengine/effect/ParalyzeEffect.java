package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParalyzeEffect")
public class ParalyzeEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        if (!effect.getEffected().getEffectController().hasMagicalStateEffect()) {
            effect.addToEffectedController();
            effect.setIsMagicalState(true);
        }
    }

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, StatEnum.PARALYZE_RESISTANCE, null);
    }

    @Override
    public void startEffect(Effect effect) {
        final Creature effected = effect.getEffected();
        effected.getController().cancelCurrentSkill();
        effected.getMoveController().abortMove();
        effect.setAbnormal(AbnormalState.PARALYZE.getId());
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.PARALYZE.getId());
        //PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_TARGET_IMMOBILIZE(effect.getEffected()));

    }

    @Override
    public void endEffect(Effect effect) {
        effect.setIsMagicalState(false);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.PARALYZE.getId());
    }
}
