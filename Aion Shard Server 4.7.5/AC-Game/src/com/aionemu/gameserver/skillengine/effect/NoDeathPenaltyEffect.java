package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;

public class NoDeathPenaltyEffect extends BufEffect {

    @Override
    public void calculate(Effect effect) {
        effect.addSucessEffect(this);
    }

    @Override
    public void startEffect(Effect effect) {
        effect.setNoDeathPenalty(true);
    }
}
