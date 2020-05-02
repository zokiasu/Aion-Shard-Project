package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.CreatureLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchHpMpEffect")
public class SwitchHpMpEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        CreatureLifeStats<? extends Creature> lifeStats = effect.getEffected().getLifeStats();
        int currentHp = lifeStats.getCurrentHp();
        int currentMp = lifeStats.getCurrentMp();

        lifeStats.increaseHp(TYPE.NATURAL_HP, currentMp - currentHp);
        lifeStats.increaseMp(TYPE.NATURAL_MP, currentHp - currentMp);
    }
}
