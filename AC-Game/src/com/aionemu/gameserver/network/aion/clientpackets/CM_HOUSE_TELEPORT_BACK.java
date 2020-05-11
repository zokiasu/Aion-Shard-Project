package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.TeleportAnimation;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author Rolandas
 */
public class CM_HOUSE_TELEPORT_BACK extends AionClientPacket {

    public CM_HOUSE_TELEPORT_BACK(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        // Nothing to read
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        float[] coords = player.getBattleReturnCoords();
        if (coords != null && player.getBattleReturnMap() != 0) {
            TeleportService2.teleportTo(player, player.getBattleReturnMap(), 1, coords[0], coords[1], coords[2], (byte) 0,
                    TeleportAnimation.BEAM_ANIMATION);

            player.setBattleReturnCoords(0, null);
        }
    }
}
