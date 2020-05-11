package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;


public class CM_CHECK_MAIL_SIZE2 extends AionClientPacket {
    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_CHECK_MAIL_SIZE2(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionlightning.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
    }

    /* (non-Javadoc)
     * @see com.aionlightning.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
        //TODO???
    }
}
