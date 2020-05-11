package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Ever'
 */
public class SM_MEGAPHONE extends AionServerPacket {

    private Player player;
    private String message;
    private int itemId;
    private boolean isAll;

    public SM_MEGAPHONE(Player player, String message, int itemId, boolean isAll) {
        this.player = player;
        this.message = message;
        this.itemId = itemId;
        this.isAll = isAll;
    }

    @Override
    protected void writeImpl(AionConnection client) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeS(player.getName());
        writeS(message);
        writeD(itemId);
        writeC(this.isAll ? this.player.getRace().getRaceId() : 255);
    }
}
