package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author orz
 * @author Antraxx
 */
public class SM_GATHER_STATUS extends AionServerPacket {

    private int status;
    private int playerobjid;
    private int gatherableobjid;

    public SM_GATHER_STATUS(int playerobjid, int gatherableobjid, int status) {
        this.playerobjid = playerobjid;
        this.gatherableobjid = gatherableobjid;
        this.status = status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(playerobjid);
        writeD(gatherableobjid);
        writeH(0); // unk
        writeC(status);

    }
}