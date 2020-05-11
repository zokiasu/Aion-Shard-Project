package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Maestros
 */
public class SM_DRAWING_TOOL extends AionServerPacket {

    private int unk2;
    private int action;
    private byte[] data;

    public SM_DRAWING_TOOL(int unk2, int action, byte[] data) {
        this.unk2 = unk2;
        this.action = action;
        this.data = data;
    }

    public SM_DRAWING_TOOL(byte[] data, @SuppressWarnings("unused") int action, int unk2) {
        this.action = 1;
        this.data = data;
        this.unk2 = unk2;
    }

    public SM_DRAWING_TOOL(byte[] data) {
        this.action = 1;
        this.data = data;
    }

    @Override
    protected void writeImpl(AionConnection aionconnection) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(action);
        if (action != 1) {
            writeC(unk2);
        }
        writeD(data.length);
        writeB(data);
    }
}
