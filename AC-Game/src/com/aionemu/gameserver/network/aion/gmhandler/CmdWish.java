package com.aionemu.gameserver.network.aion.gmhandler;

import gnu.trove.map.hash.TIntObjectHashMap;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

/**
 * @reworked Kill3r
 */
public class CmdWish extends AbstractGMHandler {

    public CmdWish(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        String[] p = params.split(" ");
        if (p.length != 2) {
            PacketSendUtility.sendMessage(admin, "NPC Spawn is not yet implemented");
            return;
        }

        String[] itemN = params.split(" ");
        TIntObjectHashMap<ItemTemplate> item = DataManager.ITEM_DATA.getItemData();

        String itemName = itemN[0];
        Integer itemcount = Integer.parseInt(itemN[1]);
        if(itemcount == 0){
            itemcount = 1;
        }

        for(ItemTemplate tt : item.valueCollection()){
            if(tt.getNamedesc().equalsIgnoreCase(itemName)){
                long count = ItemService.addItem(t, tt.getTemplateId(), itemcount);
                if (count == 0) {
                    PacketSendUtility.sendMessage(admin, "You successfully gave " + itemcount + "x " + tt.getTemplateId() + " : [item:" + tt.getTemplateId() + "] to " + t.getName() + ".");
                } else {
                    PacketSendUtility.sendMessage(admin, "Item couldn't be added");
                }
            }
        }

    }
}
