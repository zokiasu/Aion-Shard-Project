package com.aionemu.gameserver.ai2.eventcallback;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.event.AIEventType;

/**
 * Callback for {@link AIEventType#DIED} event
 *
 * @author SoulKeeper
 */
public abstract class OnDieEventCallback extends OnHandleAIGeneralEvent {

    @Override
    protected void onBeforeHandleGeneralEvent(AbstractAI obj, AIEventType eventType) {
        if (AIEventType.DIED == eventType) {
            onBeforeDie(obj);
        }
    }

    @Override
    protected void onAfterHandleGeneralEvent(AbstractAI obj, AIEventType eventType) {
        if (AIEventType.DIED == eventType) {
            onAfterDie(obj);
        }
    }

    public abstract void onBeforeDie(AbstractAI obj);

    public abstract void onAfterDie(AbstractAI obj);
}
