package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpellAttackInstantEffect")
public class SpellAttackInstantEffect extends DamageEffect {

    @Override
    public void calculate(Effect effect) {
        final Creature effected = effect.getEffected();

        //hotfix for http://www.aionarmory.com/spell.aspx?id=2713 (Balaur Seeker)
        /*if (effect.getSkillId() == 2713) {
         if (effected instanceof Npc) {
         effect.getEffected().getEffectController().setAbnormal(AbnormalState.STUN.getId());
         } else if (effected instanceof Player) {
         return;
         }
         }*/
        if (effected instanceof Player) {
            //hotfix for http://www.aiondatabase.com/skill/19332
            if (effect.getSkillId() == 19332) {
                if (((Player) effected).getFlyState() > 0) {
                    return;
                }
            }
            //hotfix for http://www.aiondatabase.com/skill/18916 and http://www.aiondatabase.com/skill/18915
            for (Effect ef : effect.getEffected().getEffectController().getAbnormalEffects()) {
                if (ef.getSkillId() == 18916) {
                    if (effect.getSkillId() == 18913) {
                        return;
                    }
                }

                if (ef.getSkillId() == 18915) {
                    if (effect.getSkillId() == 18912) {
                        return;
                    }
                }
            }
        }
        super.calculate(effect, DamageType.MAGICAL);
    }
}
