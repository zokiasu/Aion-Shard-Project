package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.CraftConfig;
import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.EnchantService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author ATracer
 * @author Antraxx
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtractAction")
public class ExtractAction extends AbstractItemAction {

    private static final Logger log = LoggerFactory.getLogger(DecomposeAction.class);

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
        if (targetItem == null) { // no item selected.
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ITEM_COLOR_ERROR);
            return false;
        }
        return true;
    }

    @Override
    public void act(final Player player, final Item parentItem, final Item targetItem) {
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), CraftConfig.EXTRACT_CAST_DELAY, 0, 0), true);
        player.getController().cancelTask(TaskId.ITEM_USE);
        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_DECOMPOSE_ITEM_CANCELED(targetItem.getNameId()));
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 3, 0), true);
                player.getObserveController().removeObserver(this);
            }
        };
        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                player.getObserveController().removeObserver(observer);
                boolean result = EnchantService.breakItem(player, targetItem, parentItem);
                if (result) {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_DECOMPOSE_ITEM_SUCCEED(targetItem.getNameId()));
                } else {
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_DECOMPOSE_ITEM_FAILED(targetItem.getNameId()));
                }
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, result ? 1 : 2, 0), true);
                log.info("[ITEMDELETE] Player: " + player.getName() + ", Item: " + parentItem.getItemId() + "(" + parentItem.getItemCount() + ") doing Extraction");
            }
        }, CraftConfig.EXTRACT_CAST_DELAY));

    }
}
