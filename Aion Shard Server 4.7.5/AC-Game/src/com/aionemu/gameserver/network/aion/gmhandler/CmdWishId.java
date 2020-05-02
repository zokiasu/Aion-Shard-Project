package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.item.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.world.World;

public class CmdWishId extends AbstractGMHandler {

    public CmdWishId(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        Player t = admin;

        if (admin.getTarget() != null && admin.getTarget() instanceof Player)
            t = World.getInstance().findPlayer(Util.convertName(admin.getTarget().getName()));

        String[] p = params.split(" ");
        if (p.length != 2) {
            PacketSendUtility.sendMessage(admin, "not enough parameters");
            return;
        }

        Integer qty = Integer.parseInt(p[0]);
        Integer itemId = Integer.parseInt(p[1]);

        if (qty > 0 && itemId > 0) {
            if (DataManager.ITEM_DATA.getItemTemplate(itemId) == null) {
                PacketSendUtility.sendMessage(admin, "Item id is incorrect: " + itemId);
            } else {
                long count = ItemService.addItem(t, itemId, qty);
                if (count == 0) {
                    PacketSendUtility.sendMessage(admin, "You successfully gave " + qty + " x [item:" + itemId + "] to " + t.getName() + ".");
                } else {
                    PacketSendUtility.sendMessage(admin, "Item couldn't be added");
                }
            }
        }
    }

}
