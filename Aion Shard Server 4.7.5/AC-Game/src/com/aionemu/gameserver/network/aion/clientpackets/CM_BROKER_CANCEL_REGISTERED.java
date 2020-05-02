package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.BrokerService;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_BROKER_CANCEL_REGISTERED extends AionClientPacket {

    @SuppressWarnings("unused")
    private int npcId;
    private int brokerItemId;

    public CM_BROKER_CANCEL_REGISTERED(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        npcId = readD();
        brokerItemId = readD();
    }

    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
        if (player == null) return;
        BrokerService.getInstance().cancelRegisteredItem(player, brokerItemId);
    }
}
