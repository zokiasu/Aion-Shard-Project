package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.item.ItemPacketService.ItemDeleteType;

/**
 * @author Avol
 */
public class SM_DELETE_ITEM extends AionServerPacket {

    private final int itemObjectId;
    private final ItemDeleteType deleteType;

    public SM_DELETE_ITEM(int itemObjectId) {
        this(itemObjectId, ItemDeleteType.QUEST_REWARD);
    }

    public SM_DELETE_ITEM(int itemObjectId, ItemDeleteType deleteType) {
        this.itemObjectId = itemObjectId;
        this.deleteType = deleteType;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(itemObjectId);
        writeC(deleteType.getMask());
    }
}
