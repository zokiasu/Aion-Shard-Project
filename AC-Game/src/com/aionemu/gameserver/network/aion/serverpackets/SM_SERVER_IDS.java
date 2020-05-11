package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.transfers.FastTrack;

/**
 * @author Eloann - Enomine
 */
public class SM_SERVER_IDS extends AionServerPacket {

    private FastTrack settings;

    public SM_SERVER_IDS(FastTrack settings) {
        this.settings = settings;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(settings.getServerId());
        writeH(0);//unk
        writeH(settings.getIconSet()); //257 or 513 the icon available
        writeD(settings.getMaxLevel());
        writeD(settings.getMaxLevel());
        writeD(1);//unk first packet 1, second packet 0
        writeC(0);
    }
}
