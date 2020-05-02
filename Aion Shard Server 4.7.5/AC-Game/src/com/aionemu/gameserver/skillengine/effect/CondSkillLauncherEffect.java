package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.controllers.observer.ObserverType;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HealType;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CondSkillLauncherEffect")
public class CondSkillLauncherEffect extends EffectTemplate {

    @XmlAttribute(name = "skill_id")
    protected int skillId;
    @XmlAttribute
    protected HealType type;

    //TODO what if you fall? effect is not applied? what if you use skill that consume hp?
    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void endEffect(Effect effect) {
        effect.getEffected().getGameStats().endEffect(effect);
        ActionObserver observer = effect.getActionObserver(position);
        effect.getEffected().getObserveController().removeObserver(observer);
    }

    @Override
    public void startEffect(final Effect effect) {
        ActionObserver observer = new ActionObserver(ObserverType.ATTACKED) {
            @Override
            public void attacked(Creature creature) {
                if (!effect.getEffected().getEffectController().hasAbnormalEffect(skillId)) {
                    if (effect.getEffected().getLifeStats().getCurrentHp() <= (int) (value / 100f * effect.getEffected().getLifeStats().getMaxHp())) {
                        SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);
                        Effect e = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), 0);
                        e.initialize();
                        e.applyEffect();
                    }
                }
            }
        };
        effect.getEffected().getObserveController().addObserver(observer);
        effect.setActionObserver(observer, position);
    }
}
