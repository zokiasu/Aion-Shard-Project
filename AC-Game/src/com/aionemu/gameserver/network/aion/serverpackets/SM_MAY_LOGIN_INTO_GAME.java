package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * This packet is response for CM_MAY_LOGIN_INTO_GAME
 *
 * @author -Nemesiss-
 */
public class SM_MAY_LOGIN_INTO_GAME extends AionServerPacket {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        /**
         * probably here is msg if fail.
         */
        writeD(0x00);
    }
}
