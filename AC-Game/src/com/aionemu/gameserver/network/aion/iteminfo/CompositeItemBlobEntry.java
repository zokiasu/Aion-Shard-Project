package com.aionemu.gameserver.network.aion.iteminfo;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Set;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.network.aion.iteminfo.ItemInfoBlob.ItemBlobType;

/**
 * This blob is sending info about the item that were fused with current item.
 *
 * @author -Nemesiss-
 * @modified Rolandas
 */
public class CompositeItemBlobEntry extends ItemBlobEntry {

    CompositeItemBlobEntry() {
        super(ItemBlobType.COMPOSITE_ITEM);
    }

    @Override
    public void writeThisBlob(ByteBuffer buf) {
        Item item = ownerItem;

        writeD(buf, item.getFusionedItemId());
        writeFusionStones(buf);
        writeH(buf, 0);
    }

    private void writeFusionStones(ByteBuffer buf) {
        Item item = ownerItem;
        int count = 0;

        if (item.hasFusionStones()) {
            Set<ManaStone> itemStones = item.getFusionStones();
            ArrayList<ManaStone> basicStones = new ArrayList<ManaStone>();
            ArrayList<ManaStone> ancientStones = new ArrayList<ManaStone>();

            for (ManaStone itemStone : itemStones) {
                if (itemStone.isBasic()) {
                    basicStones.add(itemStone);
                } else {
                    ancientStones.add(itemStone);
                }
            }

            if (item.getFusionedItemTemplate().getSpecialSlots() > 0) {
                if (ancientStones.size() > 0) {
                    for (ManaStone ancientStone : ancientStones) {
                        if (count == 6) {
                            break;
                        }
                        writeD(buf, ancientStone.getItemId());
                        count++;
                    }
                }

                for (int i = count; i < item.getFusionedItemTemplate().getSpecialSlots(); i++) {
                    writeD(buf, 0);
                    count++;
                }
            }

            for (ManaStone basicFusionStone : basicStones) {
                if (count == 6) {
                    break;
                }
                writeD(buf, basicFusionStone.getItemId());
                count++;
            }
            skip(buf, (6 - count) * 4);
        } else {
            skip(buf, 24);
        }
    }

    @Override
    public int getSize() {
        return 30;
        //return 12 * 2 + 6;
    }
}