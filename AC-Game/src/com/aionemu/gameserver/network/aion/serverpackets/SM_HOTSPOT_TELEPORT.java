package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


/**
 * @author Ever, GiGatR00n, Kill3r
 */
public class SM_HOTSPOT_TELEPORT extends AionServerPacket {

    int playerObjectId;
    int teleportGoal;
    int id;
    int unk;
    int cooldown;
    
    public SM_HOTSPOT_TELEPORT(Player player, int telegoal, int id) {
        this.playerObjectId = player.getObjectId();
        this.teleportGoal = telegoal;
        this.id = id;
    }

    public SM_HOTSPOT_TELEPORT(Player player, int telegoal, int id, int cooldown) {
        this.playerObjectId = player.getObjectId();
        this.teleportGoal = telegoal;
        this.id = id;
        this.cooldown = cooldown;
    }

    public SM_HOTSPOT_TELEPORT(Player player, int id) {
        this.playerObjectId = player.getObjectId();
        this.id = id;
    }    
    
    public SM_HOTSPOT_TELEPORT(int unk, int id) {
        this.unk = unk;
        this.id = id;
    }

    @Override
    protected void writeImpl(AionConnection con) {
        PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(id);
        switch (id) {
            case 0:
                writeD(unk);
                break;
            case 1://Start Teleportation
                writeD(playerObjectId);
                writeD(teleportGoal);
                break;
            case 2://Cancel Current Teleportation
                writeD(playerObjectId);
                break;
            case 3://Send after 10 seconds for Teleport Confirmation
                writeD(playerObjectId);
                writeD(teleportGoal);
                writeD(cooldown);
        }
    }
}
