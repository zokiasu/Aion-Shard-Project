package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.Iterator;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MEGAPHONE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

/**
 * @rework Ever'
 */
public class CM_MEGAPHONE extends AionClientPacket {

    private String chatMessage;
    private int itemObjectId;
    private boolean isAll = false;

    public CM_MEGAPHONE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        chatMessage = readS();
        itemObjectId = readD();
    }

    @Override
    protected void runImpl() {
        final Player activePlayer = getConnection().getActivePlayer();
        if (activePlayer == null) {
            return;
        }

        Item item = activePlayer.getInventory().getItemByObjId(itemObjectId);

        if (item == null) {
            return;
        }
        if ((item.getItemId() >= 188930000) && (item.getItemId() <= 188930008)) {
            this.isAll = true;
        }
        boolean deleteItem = activePlayer.getInventory().decreaseByObjectId(this.itemObjectId, 1);
        if (!deleteItem) {
            return;
        }
        Iterator<Player> players = World.getInstance().getPlayersIterator();
        while (players.hasNext()) {
            Player p = (Player) players.next();
            if (this.isAll) {
                PacketSendUtility.sendPacket(p, new SM_MEGAPHONE(activePlayer, this.chatMessage, item.getItemId(), this.isAll));
            } else if (activePlayer.getRace() == p.getRace()) {
                PacketSendUtility.sendPacket(p, new SM_MEGAPHONE(activePlayer, this.chatMessage, item.getItemId(), this.isAll));
            }
        }
    }
}
