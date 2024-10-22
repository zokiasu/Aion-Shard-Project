package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.TuningAction;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author xTz
 */
public class CM_TUNE extends AionClientPacket {

    private int itemObjectId, tuningScrollId;

    public CM_TUNE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        itemObjectId = readD();
        tuningScrollId = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = getConnection().getActivePlayer();
        if (player == null) {
            return;
        }
        Storage inventory = player.getInventory();
        final Item item = inventory.getItemByObjId(itemObjectId);
        if (item == null) {
            return;
        }
        if (tuningScrollId != 0) {
            final Item tuningItem = inventory.getItemByObjId(tuningScrollId);
            if (tuningItem == null) {
                return;
            }
            TuningAction action = tuningItem.getItemSkinTemplate().getActions().getTuningAction();
            if (action != null && action.canAct(player, tuningItem, item)) {
                action.act(player, tuningItem, item);
            }
            return;
        }
        if (item.getOptionalSocket() != -1) {
            return;
        }
        final int itemId = item.getItemId();
        final ItemTemplate template = item.getItemTemplate();
        final int nameId = template.getNameId();
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), item.getObjectId(), itemId, 5000, 0, 0), true);
        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                player.removeItemCoolDown(template.getUseLimits().getDelayId());
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ITEM_CANCELED(new DescriptionId(nameId)));
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjectId, itemId, 0, 2, 0), true);
                player.getObserveController().removeObserver(this);
            }
        };
        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                if (item.getOptionalSocket() != -1) {
                    return;
                }
                player.getObserveController().removeObserver(observer);
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjectId, itemId, 0, 1, 1), true);

                item.setOptionalSocket(Rnd.get(0, item.getItemTemplate().getOptionSlotBonus()));
                item.setRndBonus();
                item.setPersistentState(PersistentState.UPDATE_REQUIRED);
                PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
                PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401626, new DescriptionId(nameId)));
            }
        }, 5000));

    }
}
