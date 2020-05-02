package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.player.PlayerSecurityTokenService;

public class CM_SECURITY_TOKEN extends AionClientPacket {

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_SECURITY_TOKEN(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
    }

    @Override
    protected void runImpl() {
        Player player = this.getConnection().getActivePlayer();
        if (player == null) {
            return;
        }

        if (!"".equals(player.getPlayerAccount().getSecurityToken())) {
            PlayerSecurityTokenService.getInstance().sendToken(player, player.getPlayerAccount().getSecurityToken());
        } else {
            PlayerSecurityTokenService.getInstance().generateToken(player);
        }

    }
}
