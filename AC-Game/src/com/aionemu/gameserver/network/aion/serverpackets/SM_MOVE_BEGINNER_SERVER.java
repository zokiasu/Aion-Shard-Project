package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


public class SM_MOVE_BEGINNER_SERVER extends AionServerPacket {

    private int currentServerId;
    private int newServerId;
    private int mapId;

    public SM_MOVE_BEGINNER_SERVER(int currentServer, int newServerId, int mapId) {
        this.currentServerId = currentServer;
        this.newServerId = newServerId;
        this.mapId = mapId;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(newServerId);
        writeD(currentServerId);
        writeC(0);
        writeD(mapId);
    }
}
