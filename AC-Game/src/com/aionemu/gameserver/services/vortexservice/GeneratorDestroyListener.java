package com.aionemu.gameserver.services.vortexservice;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.aionemu.gameserver.services.VortexService;

/**
 * @author Source
 */
@SuppressWarnings("rawtypes")
public class GeneratorDestroyListener extends OnDieEventCallback {

    private final DimensionalVortex<?> vortex;

    public GeneratorDestroyListener(DimensionalVortex vortex) {
        this.vortex = vortex;
    }

    @Override
    public void onBeforeDie(AbstractAI obj) {
    }

    @Override
    public void onAfterDie(AbstractAI obj) {
        vortex.setGeneratorDestroyed(true);
        VortexService.getInstance().stopInvasion(vortex.getVortexLocationId());
    }
}
