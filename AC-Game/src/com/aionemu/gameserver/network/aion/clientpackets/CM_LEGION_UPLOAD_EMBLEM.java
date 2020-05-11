package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.LegionService;

/**
 * @author Simple
 */
public class CM_LEGION_UPLOAD_EMBLEM extends AionClientPacket {

    /**
     * Emblem related information *
     */
    private int size;
    private byte[] data;

    /**
     * @param opcode
     */
    public CM_LEGION_UPLOAD_EMBLEM(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        size = readD();
        data = new byte[size];
        data = readB(size);
    }

    @Override
    protected void runImpl() {
        if (data != null && data.length > 0) {
            LegionService.getInstance().uploadEmblemData(getConnection().getActivePlayer(), size, data);
        }
    }
}
