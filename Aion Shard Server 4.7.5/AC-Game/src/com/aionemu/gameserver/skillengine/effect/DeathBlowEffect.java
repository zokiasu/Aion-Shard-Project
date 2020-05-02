package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author kecimis
 */
public class DeathBlowEffect extends DamageEffect {

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, DamageType.MAGICAL);
    }
}
