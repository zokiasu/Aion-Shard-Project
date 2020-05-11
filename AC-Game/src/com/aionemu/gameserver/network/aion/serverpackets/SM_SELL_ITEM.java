package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.templates.tradelist.TradeListTemplate;
import com.aionemu.gameserver.model.templates.tradelist.TradeNpcType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author orz, Sarynth, modified by Artur
 */
public class SM_SELL_ITEM extends AionServerPacket {

    private int targetObjectId;
    private TradeListTemplate plist;
    private int sellPercentage;
    private byte action = 0x01;

    public SM_SELL_ITEM(int targetObjectId, int sellPercentage) {
        this.sellPercentage = sellPercentage;
        this.targetObjectId = targetObjectId;
    }

    public SM_SELL_ITEM(int targetObjectId, TradeListTemplate plist, int sellPercentage) {

        this.targetObjectId = targetObjectId;
        this.plist = plist;
        this.sellPercentage = sellPercentage;
        if (plist.getTradeNpcType() == TradeNpcType.ABYSS) {
            this.action = 0x02;
        }
    }

    /**
     * {@inheritDoc}
     */
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if ((this.plist != null) && (this.plist.getNpcId() != 0) && (this.plist.getCount() != 0)) {
            writeD(this.targetObjectId);
            writeC(this.plist.getTradeNpcType().index());
            writeD(this.sellPercentage);
            writeH(256);
            writeH(this.plist.getCount());
            for (TradeListTemplate.TradeTab tradeTabl : this.plist.getTradeTablist())
                writeD(tradeTabl.getId());
        } else {
            writeD(this.targetObjectId);
            writeD(5121);
            writeD(65792);
            writeC(0);
        }
    }
}
