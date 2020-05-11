package com.aionemu.gameserver.ai2.eventcallback;

import com.aionemu.commons.callbacks.Callback;
import com.aionemu.commons.callbacks.CallbackResult;
import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.event.AIEventType;

/**
 * Callback that is broadcasted when general ai event occurs.
 *
 * @author SoulKeeper
 */
@SuppressWarnings("rawtypes")
public abstract class OnHandleAIGeneralEvent implements Callback<AbstractAI> {

    @Override
    public CallbackResult beforeCall(AbstractAI obj, Object[] args) {
        AIEventType eventType = (AIEventType) args[0];
        onBeforeHandleGeneralEvent(obj, eventType);
        return CallbackResult.newContinue();
    }

    @Override
    public CallbackResult afterCall(AbstractAI obj, Object[] args, Object methodResult) {
        AIEventType eventType = (AIEventType) args[0];
        onAfterHandleGeneralEvent(obj, eventType);
        return CallbackResult.newContinue();
    }

    @Override
    public Class<? extends Callback> getBaseClass() {
        return OnHandleAIGeneralEvent.class;
    }

    protected abstract void onBeforeHandleGeneralEvent(AbstractAI obj, AIEventType eventType);

    protected abstract void onAfterHandleGeneralEvent(AbstractAI obj, AIEventType eventType);
}
