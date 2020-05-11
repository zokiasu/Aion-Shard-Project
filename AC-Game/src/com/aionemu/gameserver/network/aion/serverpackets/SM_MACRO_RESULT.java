package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author xavier
 */
public class SM_MACRO_RESULT extends AionServerPacket {

    public static SM_MACRO_RESULT SM_MACRO_CREATED = new SM_MACRO_RESULT(0x00);
    public static SM_MACRO_RESULT SM_MACRO_DELETED = new SM_MACRO_RESULT(0x01);
    private int code;

    public SM_MACRO_RESULT(int code) {
        this.code = code;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(code);
    }
}
