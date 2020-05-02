package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Ever'
 */
public class SM_REQUEST_BEGINNER_SERVER extends AionServerPacket {

    private boolean isFirst = false;
    private int currentServer = 0;
    private int newServerId = 0;

    public SM_REQUEST_BEGINNER_SERVER(int currentServer, int newServerId, boolean first) {
        this.currentServer = currentServer;
        this.newServerId = newServerId;
        this.isFirst = first;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        Player player = con.getActivePlayer();
        writeD(newServerId);
        writeD(currentServer);
        writeD(player.getObjectId());
        if (isFirst) {
            writeD(NetworkConfig.GAMESERVER_ID);
        } else {
            writeD(newServerId);
        }
        writeD(0); //unk
        writeD(0); //unk
    }
}
