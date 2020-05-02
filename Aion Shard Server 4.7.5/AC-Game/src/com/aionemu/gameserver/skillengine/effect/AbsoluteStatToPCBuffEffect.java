package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Rolandas
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbsoluteStatToPCBuff")
public class AbsoluteStatToPCBuffEffect extends AbstractAbsoluteStatEffect {

    @Override
    public void applyEffect(Effect effect) {
        // TODO: Not removable by potions and healing
    }
}
