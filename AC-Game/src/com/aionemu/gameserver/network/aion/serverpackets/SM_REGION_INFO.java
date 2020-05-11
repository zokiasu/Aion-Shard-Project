package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.zone.ZoneName;

/**
 * @author Ever'
 */
public class SM_REGION_INFO extends AionServerPacket {

    private final ZoneName subZone;

    public SM_REGION_INFO(ZoneName subZone) {
        this.subZone = subZone;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(0);
        writeC(0);
        writeC(0);
        writeD(subZone.name().hashCode());
        writeD(subZone.name().hashCode());
    }
}
