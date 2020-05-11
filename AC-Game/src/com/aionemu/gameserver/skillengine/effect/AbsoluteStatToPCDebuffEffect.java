package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbsoluteStatToPCDebuff")
public class AbsoluteStatToPCDebuffEffect extends AbstractAbsoluteStatEffect {

    @Override
    public void applyEffect(Effect effect) {
        // TODO: One skill. Removable by mental healing magic or potion.
        // Relative values - what are they, kinda ADD func ?
    }
}
