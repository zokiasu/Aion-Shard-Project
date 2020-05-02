package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpAttackInstantEffect")
public class MpAttackInstantEffect extends EffectTemplate {

    @XmlAttribute
    protected boolean percent;

    @Override
    public void applyEffect(Effect effect) {
        int maxMP = effect.getEffected().getLifeStats().getMaxMp();
        int newValue = value;
        // Support for values in percentage
        if (percent) {
            newValue = ((maxMP * value) / 100);
        }
        effect.getEffected().getLifeStats().reduceMp(newValue);
    }
}
