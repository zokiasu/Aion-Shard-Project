package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;


public class CM_GM_COMMAND_ACTION extends AionClientPacket {

    private int action;
    @SuppressWarnings("unused")
    private int targetObjectId;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_GM_COMMAND_ACTION(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readC();
        switch (action) {
            case 0:
                targetObjectId = readD();
                break;
        }

    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
        // TODO Auto-generated method stub

    }

}
