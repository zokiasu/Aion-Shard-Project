package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.BrokerService;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_BROKER_SEARCH extends AionClientPacket {

    @SuppressWarnings("unused")
    private int brokerId;
    private int sortType;
    private int page;
    private int mask;
    private int itemCount;
    private List<Integer> itemList;

    public CM_BROKER_SEARCH(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        this.brokerId = readD();
        this.sortType = readC(); // 1 - name; 2 - level; 4 - totalPrice; 6 - price for piece
        this.page = readH();
        this.mask = readH();
        this.itemCount = readH();
        this.itemList = new ArrayList<Integer>();

        for (int index = 0; index < this.itemCount; index++) {
            this.itemList.add(readD());
        }
    }

    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
    	if (player == null) return;
        BrokerService.getInstance().showRequestedItems(player, mask, sortType, page, itemList);
    }
}
