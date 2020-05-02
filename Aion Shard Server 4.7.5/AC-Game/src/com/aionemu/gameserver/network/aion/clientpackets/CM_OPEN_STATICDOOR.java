package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.StaticDoorService;

/**
 * @author rhys2002 & Wakizashi
 */
public class CM_OPEN_STATICDOOR extends AionClientPacket {

    private int doorId;

    /**
     * @param opcode
     */
    public CM_OPEN_STATICDOOR(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        doorId = readD();
    }

    @Override
    protected void runImpl() {
    	Player player = this.getConnection().getActivePlayer();
        if (player.getBattleGround() != null && !player.getBattleGround().running) {
            return;
        } else {
            StaticDoorService.getInstance().openStaticDoor(player, doorId);
        }
    }
}
