package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.item.ItemRemodelService;

/**
 * @author Sarynth
 */
public class CM_ITEM_REMODEL extends AionClientPacket {

    private int keepItemId;
    private int extractItemId;

    public CM_ITEM_REMODEL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readD(); // npcId
        keepItemId = readD();
        extractItemId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        final Player activePlayer = getConnection().getActivePlayer();
        ItemRemodelService.remodelItem(activePlayer, keepItemId, extractItemId);
    }
}
