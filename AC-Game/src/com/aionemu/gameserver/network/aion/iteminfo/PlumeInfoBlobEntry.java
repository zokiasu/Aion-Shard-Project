package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * @rework Ever'
 */
public class PlumeInfoBlobEntry extends ItemBlobEntry {

    PlumeInfoBlobEntry() {
        super(ItemBlobType.PLUME_INFO);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {
        Item item = ownerItem;
        writeQ(buf, ItemSlot.getSlotFor(item.getItemTemplate().getItemSlot()).getSlotIdMask());
        writeQ(buf, 1048576);//unk 4.5.0.18
        writeQ(buf, 0x00);//unk 4.5.0.18
        writeQ(buf, 0x00);//unk 4.5.0.18
    }

    @Override
    public int getSize() {
        return 32;
    }
}
