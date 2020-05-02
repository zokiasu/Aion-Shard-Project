package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.LOG;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DelayedSpellAttackInstantEffect")
public class DelayedSpellAttackInstantEffect extends DamageEffect {

    @XmlAttribute
    protected int delay;

    @Override
    public void applyEffect(final Effect effect) {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (effect.getEffector().isEnemy(effect.getEffected())) {
                    calculateAndApplyDamage(effect);
                }
            }
        }, delay);
    }

    private void calculateAndApplyDamage(Effect effect) {
        int skillLvl = effect.getSkillLevel();
        int valueWithDelta = value + delta * skillLvl;
        ActionModifier modifier = getActionModifiers(effect);
        int critAddDmg = this.critAddDmg2 + this.critAddDmg1 * effect.getSkillLevel();
        AttackUtil.calculateMagicalSkillResult(effect, valueWithDelta, modifier, getElement(), true, true, false, getMode(), this.critProbMod2, critAddDmg, shared, true);
        effect.getEffected().getController().onAttack(effect.getEffector(), effect.getSkillId(), TYPE.DELAYDAMAGE, effect.getReserved1(), true, LOG.PROCATKINSTANT);
        effect.getEffector().getObserveController().notifyAttackObservers(effect.getEffected());
    }
}
