package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HitType;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpAttackEffect")
public class MpAttackEffect extends AbstractOverTimeEffect {

    //TODO bosses are resistent to this?
    @Override
    public void onPeriodicAction(Effect effect) {
        int maxMP = effect.getEffected().getLifeStats().getMaxMp();
        int newValue = value;
        // Support for values in percentage
        if (this.hitType == HitType.FEAR) {
        	//TODO
        }
        if (percent) {
            newValue = (int) ((maxMP * value) / 100);
        }
        effect.getEffected().getLifeStats().reduceMp(newValue);
    }
}
