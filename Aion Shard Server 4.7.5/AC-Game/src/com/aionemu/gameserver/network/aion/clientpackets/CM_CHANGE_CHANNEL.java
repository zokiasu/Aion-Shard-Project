package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author ATracer
 */
public class CM_CHANGE_CHANNEL extends AionClientPacket {

    private int channel;

    public CM_CHANGE_CHANNEL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        channel = readD();
    }

    @Override
    protected void runImpl() {
        Player activePlayer = getConnection().getActivePlayer();
        TeleportService2.changeChannel(activePlayer, channel);
    }
}
