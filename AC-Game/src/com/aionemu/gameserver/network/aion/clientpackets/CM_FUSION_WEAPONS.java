package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.ArmsfusionService;

/**
 * @author zdead modified by Wakizashi
 */
public class CM_FUSION_WEAPONS extends AionClientPacket {

    public CM_FUSION_WEAPONS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    private int firstItemId;
    private int secondItemId;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readD();
        firstItemId = readD();
        secondItemId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
    	if (player == null) return;
        ArmsfusionService.fusionWeapons(getConnection().getActivePlayer(), firstItemId, secondItemId);
    }
}
