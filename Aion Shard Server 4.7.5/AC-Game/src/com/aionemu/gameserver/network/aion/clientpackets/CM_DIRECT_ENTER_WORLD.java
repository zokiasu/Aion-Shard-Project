package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.FastTrackService;


public class CM_DIRECT_ENTER_WORLD extends AionClientPacket {

    private int accountId;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_DIRECT_ENTER_WORLD(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        accountId = readD();
    }

    @Override
    protected void runImpl() {
        final Player player = this.getConnection().getActivePlayer();
        if (player.isOnFastTrack()) {
            FastTrackService.getInstance().checkFastTrackMove(player, accountId, true);
        } else {
            FastTrackService.getInstance().checkFastTrackMove(player, accountId, false);
        }
    }

}
