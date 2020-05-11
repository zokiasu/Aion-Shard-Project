package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.controllers.observer.AttackShieldObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HealType;

/**
 * @author kecimis
 */
public class ConvertHealEffect extends ShieldEffect {

    @XmlAttribute
    protected HealType type;
    @XmlAttribute(name = "hitpercent")
    protected boolean hitPercent;

    @Override
    public void startEffect(final Effect effect) {
        int skillLvl = effect.getSkillLevel();
        int valueWithDelta = value + delta * skillLvl;
        int hitValueWithDelta = hitvalue + hitdelta * skillLvl;

        AttackShieldObserver asObserver = new AttackShieldObserver(hitValueWithDelta, valueWithDelta, this.percent, this.hitPercent, effect, this.hitType, getType(), this.hitTypeProb, 0, 0, this.type, 0, 0);

        effect.getEffected().getObserveController().addAttackCalcObserver(asObserver);
        effect.setAttackShieldObserver(asObserver, position);
        effect.getEffected().getEffectController().setUnderShield(true);
    }

    /**
     * shieldType 0: convertHeal 1: reflector 2: normal shield 8: protect
     *
     * @return
     */
    public int getType() {
        return 0;
    }
}
