package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.house.HouseBidEntry;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_HOUSE_BIDS;
import com.aionemu.gameserver.services.HousingBidService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.collections.ListSplitter;

/**
 * @author Rolandas
 */
public class CM_GET_HOUSE_BIDS extends AionClientPacket {

    public CM_GET_HOUSE_BIDS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        // No data
    }

    @Override
    protected void runImpl() {
    	boolean isFirst = true;
        Player player = getConnection().getActivePlayer();
        HouseBidEntry playerBid = HousingBidService.getInstance().getLastPlayerBid(player.getObjectId());

        List<HouseBidEntry> houseBids = HousingBidService.getInstance().getHouseBidEntries(player.getRace());
        ListSplitter<HouseBidEntry> splitter = new ListSplitter<HouseBidEntry>(houseBids, 181);
        while (!splitter.isLast()) {
            List<HouseBidEntry> packetBids = splitter.getNext();
            HouseBidEntry playerData = splitter.isLast() ? playerBid : null;
            PacketSendUtility.sendPacket(player, new SM_HOUSE_BIDS(isFirst, splitter.isLast(), playerData, packetBids));
            isFirst = false;
        }
    }
}
