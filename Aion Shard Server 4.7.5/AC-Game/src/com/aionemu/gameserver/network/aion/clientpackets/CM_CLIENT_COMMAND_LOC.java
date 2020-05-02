package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;

/**
 * Handler for "/loc" command
 *
 * @author Antraxx
 * @modify Ever
 */
public class CM_CLIENT_COMMAND_LOC extends AionClientPacket {

    private static final Logger log = LoggerFactory.getLogger(CM_CLIENT_COMMAND_LOC.class);

    public CM_CLIENT_COMMAND_LOC(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
    }

    @Override
    protected void runImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        Player player = getConnection().getActivePlayer();
        if (player == null) {
            return;
        }

        log.info("Received \"/loc\" command");

        player.getPosition();
        sendPacket(SM_SYSTEM_MESSAGE.STR_CMD_LOCATION_DESC(player.getPosition().getMapId(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ()));
    }
}
