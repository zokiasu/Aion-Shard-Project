package com.aionemu.gameserver.skillengine.periodicaction;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author antness
 */
public class HpUsePeriodicAction extends PeriodicAction {

    @XmlAttribute(name = "value")
    protected int value;
    @XmlAttribute(name = "delta")
    protected int delta;
    @XmlAttribute(name = "ratio")
    protected boolean ratio;

    @Override
    public void act(Effect effect) {
        Creature effected = effect.getEffected();
        int requiredHp = value;
        if (effected.getLifeStats().getCurrentHp() < requiredHp) {
            effect.endEffect();
            return;
        }

        if(ratio){
            int maxHp = effected.getGameStats().getMaxHp().getCurrent();
            requiredHp = (int) (maxHp * (value / 100f));
        }

        effected.getLifeStats().reduceHp(requiredHp, effected);
    }
}
