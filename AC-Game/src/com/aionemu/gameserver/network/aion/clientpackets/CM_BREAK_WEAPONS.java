package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.ArmsfusionService;

/**
 * @author zdead
 */
public class CM_BREAK_WEAPONS extends AionClientPacket {

    public CM_BREAK_WEAPONS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    private int weaponToBreakUniqueId;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readD();
        weaponToBreakUniqueId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        ArmsfusionService.breakWeapons(getConnection().getActivePlayer(), weaponToBreakUniqueId);
    }
}
