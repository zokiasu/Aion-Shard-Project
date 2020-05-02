package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.ExchangeService;

/**
 * @author Avol
 */
public class CM_EXCHANGE_ADD_ITEM extends AionClientPacket {

    public int itemObjId;
    public int itemCount;

    public CM_EXCHANGE_ADD_ITEM(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        itemObjId = readD();
        itemCount = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        final Player activePlayer = getConnection().getActivePlayer();
        ExchangeService.getInstance().addItem(activePlayer, itemObjId, itemCount);
    }
}
