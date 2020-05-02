package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * @author KKnD , orz, avol
 */
public class CM_SUBZONE_CHANGE extends AionClientPacket {

    private int unk;
    private static Logger log = LoggerFactory.getLogger(CM_SUBZONE_CHANGE.class);

    /**
     * Constructs new instance of <tt>CM_SUBZONE_CHANGE </tt> packet
     *
     * @param opcode
     */
    public CM_SUBZONE_CHANGE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        unk = readC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        //TODO
        log.info("Send CM_SUBZONE_CHANGE - unk = " + unk + "");
    }
}
