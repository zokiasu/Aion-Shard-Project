package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.TradeService;

/**
 * @author MrPoke
 * @author GiGatR00n, Raziel
 */
public class CM_BUY_TRADE_IN_TRADE extends AionClientPacket {

    private int sellerObjId; //NPC Object Id
    @SuppressWarnings("unused")
    private int BuyMask; // v4.7.5.7 Maybe implemented at future
    private int itemId;
    private int BuyCount;
    
	private int TradeinListCount; // They can be used for implementing Anti-Cheat System
    private int TradeinItemObjectId1;
    private int TradeinItemObjectId2;
    private int TradeinItemObjectId3;

    /**
     * @param opcode
     */
    public CM_BUY_TRADE_IN_TRADE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        sellerObjId = readD();
        BuyMask = readC();
        itemId = readD();
        BuyCount = readD();
        TradeinListCount = readH();
        
        switch (TradeinListCount) {
			 case 1:
				 TradeinItemObjectId1 = readD();
				 break;
			 case 2:
				 TradeinItemObjectId1 = readD();
				 TradeinItemObjectId2 = readD();
				 break;
			 case 3:
				 TradeinItemObjectId1 = readD();
				 TradeinItemObjectId2 = readD();
				 TradeinItemObjectId3 = readD();
				 break;
	    }
    }

    @Override
    protected void runImpl() {
        Player player = this.getConnection().getActivePlayer();
        if (BuyCount < 1) return;
        TradeService.performBuyFromTradeInTrade(player, sellerObjId, itemId, BuyCount, TradeinListCount, TradeinItemObjectId1, TradeinItemObjectId2, TradeinItemObjectId3);
    }
}
