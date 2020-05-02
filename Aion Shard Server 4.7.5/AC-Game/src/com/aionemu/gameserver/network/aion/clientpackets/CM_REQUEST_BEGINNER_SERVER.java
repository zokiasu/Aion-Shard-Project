package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.FastTrackService;

/**
 * @author Eloann - Enomine
 */
public class CM_REQUEST_BEGINNER_SERVER extends AionClientPacket {

    private int action;

    public CM_REQUEST_BEGINNER_SERVER(int opcode, State state, State... states) {
        super(opcode, state, states);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readH();
        readH();//unk
        readD();//unk
        readD();//unk
        readD();//unk
    }

    @Override
    protected void runImpl() {
        Player requested = getConnection().getActivePlayer();
        if (requested == null) return;
        switch (action) {
            case 1:
                FastTrackService.getInstance().handleMoveThere(requested);
                break;
            case 2:
                FastTrackService.getInstance().handleMoveBack(requested);
                break;
        }
    }
}
