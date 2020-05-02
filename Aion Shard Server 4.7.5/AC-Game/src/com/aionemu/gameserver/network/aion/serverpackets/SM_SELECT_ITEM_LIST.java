package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.List;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.templates.decomposable.SelectItem;
import com.aionemu.gameserver.model.templates.decomposable.SelectItems;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


/**
 * @author GiGatR00n v4.7.5.x
 */
public class SM_SELECT_ITEM_LIST extends AionServerPacket {

    private int uniqueItemId;
    private List<SelectItem> selsetitems;

    public SM_SELECT_ITEM_LIST(SelectItems selsetitem, int uniqueItemId) {
        this.uniqueItemId = uniqueItemId;
        this.selsetitems = selsetitem.getItems();
    }

    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(this.uniqueItemId);
        writeD(0);
        writeC(this.selsetitems.size());
        for (int i = 0; i < this.selsetitems.size(); i++) {
            writeC(i);
            SelectItem rt = (SelectItem) this.selsetitems.get(i);
            ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(rt.getSelectItemId());
            writeD(rt.getSelectItemId());
            writeD(rt.getCount());
            writeC(itemTemplate.getOptionSlotBonus() > 0 ? 255 : 0);
			writeC(itemTemplate.getMaxEnchantBonus() > 0 ? 255 : 0);
            if ((itemTemplate.isArmor()) || (itemTemplate.isWeapon())) {
                writeC(-1);
            } else {
                writeC(0);
            }
			if ((itemTemplate.isCloth()) || (itemTemplate.getOptionSlotBonus() > 0) || (itemTemplate.getMaxEnchantBonus() > 0)) {
                writeC(1);
            } else {
                writeC(0);
            }
        }
    }
}
