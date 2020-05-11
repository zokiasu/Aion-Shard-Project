package com.aionemu.gameserver.skillengine.model;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;

/**
 * @author Cheatkiller
 */
public class ChargeSkill extends Skill {

    public ChargeSkill(SkillTemplate skillTemplate, Player effector, int skillLevel, Creature firstTarget, ItemTemplate itemTemplate) {
        super(skillTemplate, effector, skillLevel, firstTarget, null);
    }

    @Override
    public void calculateSkillDuration() {
    }

    @Override
    public boolean useSkill() {
        if (!canUseSkill()) {
            return false;
        }
        effector.getObserveController().notifySkilluseObservers(this);
        effector.setCasting(this);
        startCast();
        effector.getObserveController().attach(conditionChangeListener);
        endCast();
        return true;
    }
}
