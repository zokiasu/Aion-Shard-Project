package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.BrokerService;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_BUY_BROKER_ITEM extends AionClientPacket {

    @SuppressWarnings("unused")
    private int brokerId;
    private int itemUniqueId;
    private long itemCount;

    public CM_BUY_BROKER_ITEM(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.brokerId = readD();
        this.itemUniqueId = readD();
        this.itemCount = readQ(); // since v4.7.5.2 it became 64-bit
    }

    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
    	if (player == null) return;
        if (itemCount < 1) return;
        BrokerService.getInstance().buyBrokerItem(player, itemUniqueId, itemCount);
    }
}
