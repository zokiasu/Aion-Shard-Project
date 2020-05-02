package com.aionemu.gameserver.skillengine.task;

import com.aionemu.gameserver.configs.main.CraftConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CRAFT_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CRAFT_UPDATE;
import com.aionemu.gameserver.services.craft.CraftService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Antraxx
 * @author Kamikaze
 */
public class MorphingTask extends CraftingTask {

    /**
     * MorphingTask
     *
     * @param requestor
     * @param responder
     * @param recipeTemplates
     */
    public MorphingTask(Player requestor, StaticObject responder, RecipeTemplate recipeTemplates) {
        super(requestor, responder, recipeTemplates, 0, 0);
        this.maxSuccessValue = 50;
        this.maxFailureValue = 75;
    }

    @Override
    public void start() {
        onInteractionStart();
        task = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (!validateParticipants()) {
                    stop(true);
                }
                boolean stopTask = onInteraction();
                if (stopTask) {
                    stop(false);
                }
            }
        }, CraftConfig.CRAFT_TIMERMORPH_DELAY, CraftConfig.CRAFT_TIMERMORPH_PERIOD);
    }

    @Override
    protected void analyzeInteraction() {
    }

    @Override
    protected void onFailureFinish() {
        PacketSendUtility.sendPacket(requestor, new SM_CRAFT_UPDATE(recipeTemplate.getSkillid(), itemTemplate, currentSuccessValue, currentFailureValue, 6));
        PacketSendUtility.broadcastPacket(requestor, new SM_CRAFT_ANIMATION(requestor.getObjectId(), 0, 0, 3), true);
    }

    @Override
    protected boolean onSuccessFinish() {
        PacketSendUtility.broadcastPacket(requestor, new SM_CRAFT_ANIMATION(requestor.getObjectId(), 0, 0, 2), true);
        PacketSendUtility.sendPacket(requestor, new SM_CRAFT_UPDATE(recipeTemplate.getSkillid(), itemTemplate, 0, 0, 5));
        CraftService.finishCrafting(requestor, recipeTemplate, critCount, 0);
        return true;
    }

    @Override
    protected void onInteractionFinish() {
        requestor.setCraftingTask(null);
    }

    @Override
    protected boolean onInteraction() {
        currentSuccessValue = maxSuccessValue;
        return onSuccessFinish();
    }

    @Override
    protected void onInteractionStart() {
        this.itemTemplate = DataManager.ITEM_DATA.getItemTemplate(recipeTemplate.getProductid());
        PacketSendUtility.sendPacket(requestor, new SM_CRAFT_UPDATE(recipeTemplate.getSkillid(), itemTemplate, maxSuccessValue, maxFailureValue, 0));
        PacketSendUtility.sendPacket(requestor, new SM_CRAFT_UPDATE(recipeTemplate.getSkillid(), itemTemplate, 0, 0, 1));
        PacketSendUtility.broadcastPacket(requestor, new SM_CRAFT_ANIMATION(requestor.getObjectId(), 0, recipeTemplate.getSkillid(), 0), true);
        PacketSendUtility.broadcastPacket(requestor, new SM_CRAFT_ANIMATION(requestor.getObjectId(), 0, recipeTemplate.getSkillid(), 1), true);
    }

    @Override
    protected void onInteractionAbort() {
        PacketSendUtility.sendPacket(requestor, new SM_CRAFT_UPDATE(recipeTemplate.getSkillid(), itemTemplate, 0, 0, 4));
        PacketSendUtility.broadcastPacket(requestor, new SM_CRAFT_ANIMATION(requestor.getObjectId(), 0, 0, 2), true);
        requestor.setCraftingTask(null);
    }
}
