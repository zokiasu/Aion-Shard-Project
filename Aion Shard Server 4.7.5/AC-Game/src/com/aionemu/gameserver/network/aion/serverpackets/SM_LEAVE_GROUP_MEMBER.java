package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Lyahim
 */
public class SM_LEAVE_GROUP_MEMBER extends AionServerPacket {

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(0x00);
        writeC(0x00);
        writeD(0x3F);
        writeD(0x00);
        writeH(0x00);
    }
}
