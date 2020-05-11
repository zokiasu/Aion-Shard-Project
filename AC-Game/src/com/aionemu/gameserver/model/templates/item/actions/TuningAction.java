package com.aionemu.gameserver.model.templates.item.actions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.controllers.observer.ItemUseObserver;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TUNE_RESULT;																		
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

import com.aionemu.commons.utils.Rnd;

import com.aionemu.gameserver.model.gameobjects.state.CreatureState;

/**
 * @author Rolandas
 * @rework yayaya
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TuningAction")
public class TuningAction extends AbstractItemAction {

    @XmlAttribute
    UseTarget target;

    @Override
    public boolean canAct(Player player, Item parentItem, Item targetItem) {
		

        if (player.isInState(CreatureState.RESTING)) { return false; }		
		
        if (target.equals(UseTarget.WEAPON) && !targetItem.getItemTemplate().isWeapon()) {
            return false;
        }
        if (target.equals(UseTarget.ARMOR) && !targetItem.getItemTemplate().isArmor()) {
            return false;
        }
        return targetItem.getRandomCount() < targetItem.getItemTemplate().getRandomBonusCount() && !targetItem.isEquipped();
    }

    @Override
    public void act(final Player player, final Item parentItem, final Item targetItem) {
        final int parentItemId = parentItem.getItemId();
        final int parentObjectId = parentItem.getObjectId();
        final int parentNameId = parentItem.getNameId();
        PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItemId, 5000, 0, 0), true);
        final ItemUseObserver observer = new ItemUseObserver() {
            @Override
            public void abort() {
                player.getController().cancelTask(TaskId.ITEM_USE);
                player.removeItemCoolDown(parentItem.getItemTemplate().getUseLimits().getDelayId());
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_ITEM_CANCELED(new DescriptionId(parentNameId)));
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentObjectId, parentItemId, 0, 2, 0), true);
                player.getObserveController().removeObserver(this);
            }
        };
        player.getObserveController().attach(observer);
        player.getController().addTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                player.getObserveController().removeObserver(observer);
                PacketSendUtility.broadcastPacket(player, new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentObjectId, parentItemId, 0, 1, 1), true);
                if (!player.getInventory().decreaseByObjectId(parentObjectId, 1)) {	
                    return;
                }
                int rndCount = targetItem.getRandomCount();
                if (rndCount >= targetItem.getItemTemplate().getRandomBonusCount() || targetItem.isEquipped()) {					
					return;
				}
                targetItem.setRandomStats(null);
                targetItem.setBonusNumber(0);
                targetItem.setRandomCount(++rndCount);
                targetItem.setOptionalSocket(Rnd.get(0, targetItem.getItemTemplate().getOptionSlotBonus()));
                targetItem.setRndBonus();
                targetItem.setPersistentState(PersistentState.UPDATE_REQUIRED);
                PacketSendUtility.sendPacket(player, new SM_TUNE_RESULT(player, targetItem.getObjectId(), parentItemId, targetItem.getItemId()));
				PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, targetItem));
				PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1401639, new DescriptionId(parentNameId)));
            }
        }, 5000));
    }
}
