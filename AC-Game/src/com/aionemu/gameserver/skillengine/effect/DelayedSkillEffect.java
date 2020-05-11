package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAttribute;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author kecimis
 */
public class DelayedSkillEffect extends EffectTemplate {

	@XmlAttribute(name = "skill_id")
    protected int skilliD;


    @Override
    public void applyEffect(Effect effect){
        effect.addToEffectedController();
    }

    @Override
    public void startEffect(final Effect effect){
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                // apply effect
                if(effect.getEffected().getEffectController().hasAbnormalEffect(effect.getSkill().getSkillId())){
                    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skilliD);
                    Effect e = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), 0);
                    e.initialize();
                    e.applyEffect();
                }
            }
        }, effect.getEffectsDuration());
    }

    @Override
    public void endEffect(Effect effect){

    }
}
