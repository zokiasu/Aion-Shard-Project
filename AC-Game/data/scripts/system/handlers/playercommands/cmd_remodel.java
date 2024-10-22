/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, ragarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package playercommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INVENTORY_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.services.item.ItemRemodelService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.PlayerCommand;

/**
 * @author Kashim
 */
public class cmd_remodel extends PlayerCommand {

    public cmd_remodel() {
        super("remodel");
    }

    public void executeCommand(Player admin, String[] params) {

        if (params.length < 1) {
            PacketSendUtility.sendMessage(admin, "Syntax: .remodel <itemid>\n");
            return;
        }

        if (params.length == 1) { // Use target
            int itemId = Integer.parseInt(params[0]);

            if(itemId == 100000999 || itemId == 100000997 || itemId == 100000998 || itemId == 100001167
              || itemId == 100001168  || itemId == 100000027  || itemId == 100000564  || itemId == 100001275
              || itemId == 100901365  || itemId == 100901364  || itemId == 100900806  || itemId == 100901182
              || itemId == 100900999  || itemId == 101301257  || itemId == 101301256  || itemId == 100000665
              || itemId == 100900727  || itemId == 100900594  || itemId == 100900618  || itemId == 100901140
              || itemId == 100000037  || itemId == 100001176  || itemId == 100000592  || itemId == 100000038
              || itemId == 100000039  || itemId == 100001309  || itemId == 101300532  || itemId == 101501303
              || itemId == 101501215  || itemId == 100901205  || itemId == 101501194  || itemId == 101501105
              || itemId == 101501104  || itemId == 101501103  || itemId == 101501096  || itemId == 101500763
              || itemId == 101500762  || itemId == 101500632  || itemId == 101500631  || itemId == 101500595
              || itemId == 100900281  || itemId == 100900594  || itemId == 100900805  || itemId == 100001727
              || itemId == 101500588  || itemId == 100901203  || itemId == 100900205  || itemId == 100901204
              || itemId == 100901304  || itemId == 101801015  || itemId == 100501011) {
                PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
                return;
            }

            if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
                PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
                return;
            }
            if (admin.getInventory().decreaseByItemId(186000221, 1)) {
                if (remodelItem(admin, itemId)) {
                    PacketSendUtility.sendMessage(admin, "Successfully remodelled an item of the player!");
                    PacketSendUtility.broadcastPacket(admin, new SM_UPDATE_PLAYER_APPEARANCE(admin.getObjectId(), admin.getEquipment().getEquippedItemsWithoutStigma()), true);
                } else {
                    PacketSendUtility.sendMessage(admin, "Was not able to remodel an item of the player!");
                }
            } else {
                PacketSendUtility.sendMessage(admin, "You do not meet the requirements !");
            }
        }
    }

    private boolean remodelItem(Player player, int itemId) {
        ItemTemplate template = DataManager.ITEM_DATA.getItemTemplate(itemId);
        if (template == null) {
            return false;
        }

        Equipment equip = player.getEquipment();
        if (equip == null) {
            return false;
        }

        for (Item item : equip.getEquippedItemsWithoutStigmaOld()) {
            if (item.getItemTemplate().isWeapon()) {
                if (item.getItemTemplate().getWeaponType() == template.getWeaponType()) {
                    ItemRemodelService.systemRemodelItem(player, item, template);
                    PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
                    DAOManager.getDAO(InventoryDAO.class).store(item, player);
                    return true;
                }
            } else if (item.getItemTemplate().isArmor()) {
                if (item.getItemTemplate().getItemSlot() == template.getItemSlot()) {
                    ItemRemodelService.systemRemodelItem(player, item, template);
                    PacketSendUtility.sendPacket(player, new SM_INVENTORY_UPDATE_ITEM(player, item));
                    DAOManager.getDAO(InventoryDAO.class).store(item, player);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void execute(Player player, String... params) {
        executeCommand(player, params);
    }
}
