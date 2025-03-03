package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author xTz
 */
public class SM_ICON_INFO extends AionServerPacket {

    private int buffId;
    private boolean display;

    public SM_ICON_INFO(int buffId, boolean display) {
        this.buffId = buffId;
        this.display = display;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(buffId);
        writeC(display ? 1 : 0);
    }
}
