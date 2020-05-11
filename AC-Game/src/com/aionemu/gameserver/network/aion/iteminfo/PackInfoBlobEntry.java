package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;

import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * @author Ranastic
 */
public class PackInfoBlobEntry extends ItemBlobEntry {

    PackInfoBlobEntry() {
        super(ItemBlobType.PACK_INFO);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {
        if (ownerItem.getItemTemplate().getPackCount() > 0 && ownerItem.isPacked()) {
            writeC(buf, ownerItem.getPackCount());
        } else if (!ownerItem.isPacked()) {
            writeC(buf, 0);
        } else {
            writeC(buf, 0);
        }
    }

    @Override
    public int getSize() {
        return 1;
    }
}
