package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Sweetkr
 */
public class SM_SHOW_BRAND extends AionServerPacket {

    private int brandId;
    private int targetObjectId;

    public SM_SHOW_BRAND(int brandId, int targetObjectId) {
        this.brandId = brandId;
        this.targetObjectId = targetObjectId;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(0x01);
        writeD(0x01); // unk
        writeD(brandId);
        writeD(targetObjectId);

    }
}
