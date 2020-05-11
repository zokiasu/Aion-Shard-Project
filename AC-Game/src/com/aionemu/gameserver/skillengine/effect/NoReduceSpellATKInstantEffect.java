package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NoReduceSpellATKInstantEffect")
public class NoReduceSpellATKInstantEffect extends DamageEffect {

    @XmlAttribute
    protected boolean percent;

    @Override
    public void calculate(Effect effect) {
        if (!super.calculate(effect, null, null)) {
            return;
        }

        int valueWithDelta = value + delta * effect.getSkillLevel();
        if (percent) {
            valueWithDelta = (int) (valueWithDelta / 100f * effect.getEffected().getLifeStats().getMaxHp());
        }
        int critAddDmg = this.critAddDmg2 + this.critAddDmg1 * effect.getSkillLevel();

        AttackUtil.calculateMagicalSkillResult(effect, valueWithDelta, null, getElement(), false, true, true, getMode(), this.critProbMod2, critAddDmg, shared, false);
    }
}
