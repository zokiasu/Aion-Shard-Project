package com.aionemu.gameserver.network.aion.gmhandler;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CmdTeleportToNamed extends AbstractGMHandler {

    public CmdTeleportToNamed(Player admin, String params) {
        super(admin, params);
        run();
    }

    public void run() {
        int npcId = 0;
        String message = "";
        try {
            npcId = Integer.valueOf(params);
        } catch (ArrayIndexOutOfBoundsException e) {
            onFail(admin, e.getMessage());
        } catch (NumberFormatException e) {
            String npcDesc = params;

            for (NpcTemplate template : DataManager.NPC_DATA.getNpcData().valueCollection()) {
                if (template.getDesc() != null && template.getDesc().equalsIgnoreCase(npcDesc)) {
                    TeleportService2.teleportToNpc(admin, template.getTemplateId());
                    message = "Teleporting to Npc: " + template.getTemplateId();
                    PacketSendUtility.sendMessage(admin, message);
                }
            }
        }

        if (npcId > 0) {
            if (!message.equals(""))
                message = "Teleporting to Npc: " + npcId + "\n" + message;
            else
                message = "Teleporting to Npc: " + npcId;
            PacketSendUtility.sendMessage(admin, message);
            TeleportService2.teleportToNpc(admin, npcId);
        }
    }

    /**
     * @param message
     */
    public void onFail(Player admin, String message) {
        PacketSendUtility.sendMessage(admin, "syntax //movetonpc <npc_id|npc name>");
    }
}