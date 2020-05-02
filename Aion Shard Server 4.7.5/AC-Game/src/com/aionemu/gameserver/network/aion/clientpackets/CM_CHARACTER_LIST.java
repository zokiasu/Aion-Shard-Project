package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ACCOUNT_ACCESS_PROPERTIES;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHARACTER_LIST;

/**
 * In this packets aion client is requesting character list.
 *
 * @author -Nemesiss-
 */
public class CM_CHARACTER_LIST extends AionClientPacket {

    /**
     * PlayOk2 - we dont care...
     */
    private int accountId;

    /**
     * Constructs new instance of <tt>CM_CHARACTER_LIST </tt> packet.
     *
     * @param opcode
     */
    public CM_CHARACTER_LIST(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.accountId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        sendPacket(new SM_CHARACTER_LIST(accountId));
        boolean isGM = (getConnection()).getAccount().getAccessLevel() >= AdminConfig.GM_PANEL;
        sendPacket(new SM_ACCOUNT_ACCESS_PROPERTIES(isGM));
    }
}
