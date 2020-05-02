package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.change.Func;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
import com.aionemu.gameserver.skillengine.model.Effect;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DispelBuffCounterAtkEffect")
public class DispelBuffCounterAtkEffect extends DamageEffect {

    @XmlAttribute
    protected int dpower;
    @XmlAttribute
    protected int power;
    @XmlAttribute
    protected int hitvalue;
    @XmlAttribute
    protected int hitdelta;
    @XmlAttribute(name = "dispel_level")
    protected int dispelLevel;
    private int i;
    private int finalPower;

    @Override
    public void applyEffect(Effect effect) {
        super.applyEffect(effect);
        effect.getEffected().getEffectController().dispelBuffCounterAtkEffect(i, dispelLevel, finalPower);
    }

    @Override
    public void calculate(Effect effect) {
        if (!super.calculate(effect, null, null)) {
            return;
        }

        Creature effected = effect.getEffected();
        int count = value + delta * effect.getSkillLevel();
        finalPower = power + dpower * effect.getSkillLevel();

        i = effected.getEffectController().calculateNumberOfEffects(dispelLevel);
        i = (i < count ? i : count);

        int newValue = 0;
        if (i == 1) {
            newValue = hitvalue;
        } else if (i > 1) {
            newValue = hitvalue + ((hitvalue / 2) * (i - 1));
        }

        int valueWithDelta = newValue + hitdelta * effect.getSkillLevel();

        ActionModifier modifier = getActionModifiers(effect);

        AttackUtil.calculateMagicalSkillResult(effect, valueWithDelta, modifier, getElement(), true, true, false, Func.ADD, 0, 0, shared, true); //critprob 0, dispels can not crit
    }
}
