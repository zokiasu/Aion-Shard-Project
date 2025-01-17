package com.aionemu.gameserver.skillengine.task;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemQuality;

/**
 * @author ATracer
 * @author synchro2
 * @author Antraxx
 * @author Kamikaze
 */
public abstract class AbstractCraftTask extends AbstractInteractionTask {

    protected int maxSuccessValue = 40;
    protected int maxFailureValue = 60;
    protected int currentSuccessValue;
    protected int currentFailureValue;
    protected int skillLvlDiff;
    protected CraftCritType critType = CraftCritType.NONE;
    protected ItemQuality itemQuality;

    protected enum CraftCritType {

        NONE(0),
        INSTANT(1), // Junk - Gray
        BLUE(2), // Common - White
        PURPLE(3); // Superior - Green
        private int critId;

        private CraftCritType(int critId) {
            this.critId = critId;
        }

        public int getCritId() {
            return critId;
        }

        public int getPacketId() {
            return critId > 0 ? critId : 1;
        }
    }

    /**
     * AbstractCraftTask
     *
     * @param requestor
     * @param responder
     * @param skillLvlDiff
     */
    public AbstractCraftTask(Player requestor, VisibleObject responder, int skillLvlDiff) {
        super(requestor, responder);
        this.skillLvlDiff = skillLvlDiff;
    }

    @Override
    protected boolean onInteraction() {
        if (currentSuccessValue == maxSuccessValue) {
            return onSuccessFinish();
        }
        if (currentFailureValue == maxFailureValue) {
            onFailureFinish();
            return true;
        }

        analyzeInteraction();

        sendInteractionUpdate();
        return false;
    }

    /**
     * Perform interaction calculation
     */
    protected void analyzeInteraction() {
        int multi = Math.max(0, 33 - skillLvlDiff * 5);
        if (Rnd.get(100) > multi) {
            currentSuccessValue += Rnd.get(maxSuccessValue / (multi + 1) / 2, maxSuccessValue);
        } else {
            currentFailureValue += Rnd.get(maxFailureValue / (multi + 1) / 2, maxFailureValue);
        }
        if (currentSuccessValue >= maxFailureValue) {
            currentSuccessValue = maxFailureValue;
        } else if (currentFailureValue >= maxFailureValue) {
            currentFailureValue = maxFailureValue;
        }
    }

    protected abstract void sendInteractionUpdate();

    protected abstract boolean onSuccessFinish();

    protected abstract void onFailureFinish();
}
