package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TIME_CHECK;

/**
 * I dont know what this packet is doing - probably its ping/pong packet
 *
 * @author -Nemesiss-
 */
public class CM_TIME_CHECK extends AionClientPacket {

    /**
     * Nano time / 1000000
     */
    private int nanoTime;

    /**
     * Constructs new instance of <tt>CM_VERSION_CHECK </tt> packet
     *
     * @param opcode
     */
    public CM_TIME_CHECK(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        nanoTime = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        AionConnection client = getConnection();
        int timeNow = (int) (System.nanoTime() / 1000000);
        int diff = timeNow - nanoTime;
        client.sendPacket(new SM_TIME_CHECK(nanoTime));

        // log.info("CM_TIME_CHECK: " + nanoTime + " =?= " + timeNow + " dif: " + diff);
    }
}
