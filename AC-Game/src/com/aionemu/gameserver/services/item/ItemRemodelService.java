package com.aionemu.gameserver.services.item;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.item.actions.ItemActions;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.services.trade.PricesService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

/**
 * @author Sarynth modified by Wakizashi
 */
public class ItemRemodelService {

    /**
     * @param player
     * @param keepItemObjId
     * @param extractItemObjId
     */
    public static void remodelItem(Player player, int keepItemObjId, int extractItemObjId) {
        Storage inventory = player.getInventory();
        Item keepItem = inventory.getItemByObjId(keepItemObjId);
        Item extractItem = inventory.getItemByObjId(extractItemObjId);

        long remodelCost = PricesService.getPriceForService(1000, player.getRace());

        if (keepItem == null || extractItem == null) { // NPE check.
            return;
        }

        // Check Player Level
		if (player.getLevel() < 10) {

            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT);
            return;
        }

        // Check Kinah
        if (player.getInventory().getKinah() < remodelCost) {
            PacketSendUtility.sendPacket(player,
                    SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));
            return;
        }

        // Check for using "Pattern Reshaper (Skin Remover)" (168100000)
        if (extractItem.getItemTemplate().getTemplateId() == 168100000) {
            if (!keepItem.isSkinnedItem()) {
                PacketSendUtility.sendMessage(player, "That item does not have a remodeled skin to remove.");
                return;
            }
            // Remove Money
            if (!player.getInventory().tryDecreaseKinah(remodelCost)) {
                return;
            }
            // Remove "Pattern Reshaper (Skin Remover)"
            player.getInventory().decreaseItemCount(extractItem, 1);

            // Revert item to ORIGINAL SKIN
            keepItem.setItemSkinTemplate(keepItem.getItemTemplate());

            // Remove dye color if item can not be dyed.
            if (!keepItem.getItemTemplate().isItemDyePermitted()) {
                keepItem.setItemColor(0);
            }

            // Notify Player
            ItemPacketService.updateItemAfterInfoChange(player, keepItem);
            PacketSendUtility.sendPacket(player,
                    SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_SUCCEED(new DescriptionId(keepItem.getItemTemplate().getNameId())));
            return;
        }
        // Check that types match.
        if (keepItem.getItemTemplate().getWeaponType() != extractItem.getItemSkinTemplate().getWeaponType()
                || (extractItem.getItemSkinTemplate().getArmorType() != ArmorType.CLOTHES && keepItem.getItemTemplate().getArmorType() != extractItem
                .getItemSkinTemplate().getArmorType()) || keepItem.getItemTemplate().getArmorType() == ArmorType.CLOTHES
                || keepItem.getItemTemplate().getItemSlot() != extractItem.getItemSkinTemplate().getItemSlot()) {
                	
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(new DescriptionId(keepItem
                    .getItemTemplate().getNameId()), new DescriptionId(extractItem.getItemSkinTemplate().getNameId())));
            return;
        }

        if (!keepItem.isRemodelable(player)) {
        	// "1300478" = The appearance of %0 cannot be modified.
            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300478, new DescriptionId(keepItem.getItemTemplate().getNameId())));
            return;
        }

        if (!extractItem.isRemodelable(player)) {
        	// "1300482" = You have failed to modify the appearance of the item as you could not remove the skin item %0.
            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300482, new DescriptionId(extractItem.getItemTemplate().getNameId())));
            return;
        }

        ItemTemplate skin = extractItem.getItemSkinTemplate();
        ItemActions actions = skin.getActions();
        if (extractItem.isSkinnedItem() && actions != null && actions.getRemodelAction() != null
                && actions.getRemodelAction().getExtractType() == 2) {
        	// "1300482" = You have failed to modify the appearance of the item as you could not remove the skin item %0.
            PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300482, new DescriptionId(extractItem.getItemTemplate().getNameId())));
            return;
        }
        // -- SUCCESS --

        // Remove Money
        player.getInventory().decreaseKinah(remodelCost);

        // Remove Item
        player.getInventory().decreaseItemCount(extractItem, 1);

        // REMODEL ITEM
        keepItem.setItemSkinTemplate(skin);

        // Transfer Dye
        keepItem.setItemColor(extractItem.getItemColor());

        // Notify Player
        ItemPacketService.updateItemAfterInfoChange(player, keepItem);
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(keepItem.getItemTemplate().getNameId())));
    }

    public static boolean commandPreviewRemodelItem(Player player, int itemId, int duration) {
        ItemTemplate template = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (template == null) {
            return false;
        }

        Equipment equip = player.getEquipment();
        if (equip == null) {
            return false;
        }

        for (Item item : equip.getEquippedItemsWithoutStigmaOld()) {
            if (item.getEquipmentSlot() == ItemSlot.MAIN_OFF_HAND.getSlotIdMask()
                    || item.getEquipmentSlot() == ItemSlot.SUB_OFF_HAND.getSlotIdMask()) {
                continue;
            }

            if (item.getItemTemplate().isWeapon()) {
                if (item.getItemTemplate().getWeaponType() == template.getWeaponType()
                        && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    systemPreviewRemodelItem(player, item, template, duration);
                    return true;
                }
            } else if (item.getItemTemplate().isArmor()) {
                if (item.getItemTemplate().getItemSlot() == template.getItemSlot()
                        && item.getItemSkinTemplate().getTemplateId() != itemId) {
                    systemPreviewRemodelItem(player, item, template, duration);
                    return true;
                }
            }
        }

        return false;
    }

    public static void systemPreviewRemodelItem(final Player player, final Item item,
                                                ItemTemplate template, int duration) {
        final ItemTemplate oldTemplate = item.getItemSkinTemplate();
        item.setItemSkinTemplate(template);

        PacketSendUtility.sendPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedForApparence()));
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new DescriptionId(item
                .getItemTemplate().getNameId())));

        PacketSendUtility.broadcastPacket(player,
                new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment()
                        .getEquippedItemsWithoutStigma()), true);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                item.setItemSkinTemplate(oldTemplate);
            }
        }, 50);

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                PacketSendUtility.sendPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedForApparence()));
                PacketSendUtility.broadcastPacket(player,
                        new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment()
                                .getEquippedItemsWithoutStigma()), true);
            }
        }, duration * 1000);
    }

    public static void systemRemodelItem(Player player, Item keepItem, ItemTemplate template) {
        keepItem.setItemSkinTemplate(template);
        ItemPacketService.updateItemAfterInfoChange(player, keepItem);
        PacketSendUtility.sendPacket(player, new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedForApparence()));
        PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(1300483, new Object[]{new DescriptionId(keepItem.getItemTemplate().getNameId())}));
    }
}
