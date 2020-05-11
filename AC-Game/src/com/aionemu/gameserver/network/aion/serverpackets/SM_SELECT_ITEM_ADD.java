package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


public class SM_SELECT_ITEM_ADD extends AionServerPacket {

    private int uniqueItemId;
    private int index;

    public SM_SELECT_ITEM_ADD(int uniqueItemId, int index) {
        this.uniqueItemId = uniqueItemId;
        this.index = index;
    }

    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(this.uniqueItemId);
        writeD(0);
        writeC(this.index);
    }
}
