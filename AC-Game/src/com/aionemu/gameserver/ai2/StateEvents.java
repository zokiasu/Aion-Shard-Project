package com.aionemu.gameserver.ai2;

import com.aionemu.gameserver.ai2.event.AIEventType;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * @author ATracer
 */
public enum StateEvents {

    CREATED_EVENTS(AIEventType.SPAWNED),
    DESPAWN_EVENTS(AIEventType.RESPAWNED, AIEventType.SPAWNED),
    DEAD_EVENTS(AIEventType.DESPAWNED, AIEventType.DROP_REGISTERED);
    private EnumSet<AIEventType> events;

    private StateEvents(AIEventType... aiEventTypes) {
        this.events = EnumSet.copyOf(Arrays.asList(aiEventTypes));
    }

    public boolean hasEvent(AIEventType event) {
        return events.contains(event);
    }
}
