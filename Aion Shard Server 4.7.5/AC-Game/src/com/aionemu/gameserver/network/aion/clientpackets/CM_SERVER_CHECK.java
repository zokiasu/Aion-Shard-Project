package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;


public class CM_SERVER_CHECK extends AionClientPacket {

	/**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_SERVER_CHECK(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
    	PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
    	//NOTHING
    }

	@Override
	protected void runImpl() {
		//TODO not sure about the handling
		
	}
}
