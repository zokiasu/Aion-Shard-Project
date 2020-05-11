package com.aionemu.gameserver.controllers.observer;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.MathUtil;

/**
 * @author nrg
 */
public abstract class DialogObserver extends ActionObserver {

    private Player responder;
    private Creature requester;
    private int maxDistance;

    public DialogObserver(Creature requester, Player responder, int maxDistance) {
        super(ObserverType.MOVE);
        this.responder = responder;
        this.requester = requester;
        this.maxDistance = maxDistance;
    }

    @Override
    public void moved() {
        if (!MathUtil.isIn3dRange(responder, requester, maxDistance)) {
            tooFar(requester, responder);
        }
    }

    /**
     * Is called when player is too far away from dialog serving object
     */
    public abstract void tooFar(Creature requester, Player responder);
}
