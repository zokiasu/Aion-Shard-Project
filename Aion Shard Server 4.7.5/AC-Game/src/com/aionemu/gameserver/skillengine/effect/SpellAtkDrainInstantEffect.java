package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.LOG;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS.TYPE;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpellAtkDrainInstantEffect")
public class SpellAtkDrainInstantEffect extends DamageEffect {

    @XmlAttribute(name = "hp_percent")
    protected int hp_percent;
    @XmlAttribute(name = "mp_percent")
    protected int mp_percent;

    @Override
    public void applyEffect(Effect effect) {
        super.applyEffect(effect);
        if (hp_percent != 0) {
            effect.getEffector().getLifeStats().increaseHp(TYPE.HP, effect.getReserved1() * hp_percent / 100, effect.getSkillId(), LOG.SPELLATKDRAININSTANT);
        }
        if (mp_percent != 0) {
            effect.getEffector().getLifeStats().increaseMp(TYPE.ABSORBED_MP, effect.getReserved1() * mp_percent / 100, effect.getSkillId(), LOG.SPELLATKDRAININSTANT);
        }
    }

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, DamageType.MAGICAL);
    }
}
