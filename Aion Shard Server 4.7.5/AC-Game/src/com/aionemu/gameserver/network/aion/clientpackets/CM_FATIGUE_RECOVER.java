package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.player.FatigueService;


public class CM_FATIGUE_RECOVER extends AionClientPacket {

    private int removeCount;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_FATIGUE_RECOVER(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        removeCount = readD();
    }


    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
    	if (player == null) return;   
        FatigueService.getInstance().removeRecoverCount(player, removeCount);
    }
}
