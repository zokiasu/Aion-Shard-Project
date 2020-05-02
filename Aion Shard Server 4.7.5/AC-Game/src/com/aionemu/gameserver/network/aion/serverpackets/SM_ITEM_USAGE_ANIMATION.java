package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.World;

/**
 * @author ATracer
 */
public class SM_ITEM_USAGE_ANIMATION extends AionServerPacket {

    private int playerObjId;
    private int targetObjId;
    private int itemObjId;
    private int itemId;
    private int time;
    private int end;
    private int unk;

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = 0;
        this.end = 1;
        this.unk = 1;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end, int unk) {
        this.playerObjId = playerObjId;
        this.targetObjId = playerObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
        this.unk = unk;
    }

    public SM_ITEM_USAGE_ANIMATION(int playerObjId, int targetObjId, int itemObjId, int itemId, int time, int end, int unk) {
        this.playerObjId = playerObjId;
        this.targetObjId = targetObjId;
        this.itemObjId = itemObjId;
        this.itemId = itemId;
        this.time = time;
        this.end = end;
        this.unk = unk;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (time > 0) {
            final Player player = World.getInstance().findPlayer(playerObjId);
            final Item item = player.getInventory().getItemByObjId(itemObjId);
            player.setUsingItem(item);
        }

        writeD(playerObjId); // player obj id
        writeD(targetObjId); // target obj id

        writeD(itemObjId); // itemObjId
        writeD(itemId); // item id

        writeD(time); // unk
        writeC(end); // unk
        writeC(0); // unk
        writeC(1);
        writeD(unk);
        writeC(0);//unk
    }
}
