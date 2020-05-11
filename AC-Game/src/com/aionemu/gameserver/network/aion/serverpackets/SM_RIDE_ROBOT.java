package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.World;

/**
 * @author Ever'
 */
public class SM_RIDE_ROBOT extends AionServerPacket {

    private int player;
    private int robotId;

    public SM_RIDE_ROBOT(int player, int robotId) {
        this.player = player;
        this.robotId = robotId;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(player);
        writeD(robotId);
    }
}
