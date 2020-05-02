package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author kecimis
 */
public class SanctuaryEffect extends EffectTemplate {

    /* (non-Javadoc)
     * @see com.aionemu.gameserver.skillengine.effect.EffectTemplate#applyEffect(com.aionemu.gameserver.skillengine.model.Effect)
     */
    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void startEffect(Effect effect) {
        //TODO
    }

    @Override
    public void endEffect(Effect effect) {
        //TODO
    }
}
