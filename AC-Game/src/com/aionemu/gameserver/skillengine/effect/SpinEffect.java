package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SpellStatus;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpinEffect")
public class SpinEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        effect.setIsPhysicalState(true);
        effect.addToEffectedController();
        effect.getEffected().getEffectController().removeParalyzeEffects();
    }

    @Override
    public void calculate(Effect effect) {
        if (effect.getEffected().getEffectController().hasPhysicalStateEffect()) {
            return;
        }
        super.calculate(effect, StatEnum.SPIN_RESISTANCE, SpellStatus.SPIN);
    }

    @Override
    public void startEffect(Effect effect) {
        final Creature effected = effect.getEffected();
        effected.getController().cancelCurrentSkill();
        effected.getMoveController().abortMove();
        effected.getEffectController().setAbnormal(AbnormalState.SPIN.getId());
        effect.setAbnormal(AbnormalState.SPIN.getId());
    }

    @Override
    public void endEffect(Effect effect) {
        effect.setIsPhysicalState(false);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.SPIN.getId());
    }
}
