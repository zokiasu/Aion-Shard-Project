package playercommands;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemPacketService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;
import com.aionemu.gameserver.utils.i18n.CustomMessageId;
import com.aionemu.gameserver.utils.i18n.LanguageHandler;

/**
 * @author Tago modified by Wakizashi and Ney, Maestros, Eloann
 */
public class cmd_enchant extends PlayerCommand {

    public cmd_enchant() {
        super("enchant");
    }

    @Override
    public void execute(Player player, String... params) {
        int enchant = 0;
        try {
            enchant = params[0] == null ? enchant : Integer.parseInt(params[0]);
        } catch (Exception ex) {
            onFail(player, "Fail");
            return;
        }
        enchant(player, enchant);
    }

    private void enchant(Player player, int enchant) {
        for (Item targetItem : player.getEquipment().getEquippedItemsWithoutStigmaOld()) {
            if (isUpgradeble(targetItem)) {
                int enchantLevel = 15, maxEnchant = targetItem.getItemTemplate().getMaxEnchantLevel();
                if (maxEnchant > 0 && enchantLevel > maxEnchant) {
                    enchantLevel = maxEnchant;
                }

                targetItem.setEnchantLevel(enchantLevel);

                if (targetItem.isEquipped()) {
                    player.getGameStats().updateStatsVisually();
                }
                ItemPacketService.updateItemAfterInfoChange(player, targetItem);
            }
        }

        PacketSendUtility.sendMessage(player, LanguageHandler.translate(CustomMessageId.ENCHANT_SUCCES) + enchant);
    }

    /**
     * Verify if the item is enchantble and/or socketble
     */
    public static boolean isUpgradeble(Item item) {
        if (item.getItemTemplate().isNoEnchant()) {
            return false;
        }
        if (item.getItemTemplate().isWeapon()) {
            return true;
        }
        if (item.getEnchantLevel() == 15) {
            return false;
        }
        if (item.getItemTemplate().isArmor()) {
            int at = item.getItemTemplate().getItemSlot();
            if (at == 1
                    || /* Main Hand */ at == 2
                    || /* Sub Hand */ at == 8
                    || /* Jacket */ at == 16
                    || /* Gloves */ at == 32
                    || /* Boots */ at == 2048
                    || /* Shoulder */ at == 4096
                    || /* Pants */ at == 131072
                    || /* Main Off Hand */ at == 262144) /* Sub Off Hand */ {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onFail(Player player, String message) {
        PacketSendUtility.sendMessage(player, "Syntax .enchant : \n" + "  Syntax .enchant <value>.\n" + LanguageHandler.translate(CustomMessageId.ENCHANT_INFO) + "\n"
                + LanguageHandler.translate(CustomMessageId.ENCHANT_SAMPLE));
    }
}
