package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Map;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * Packet with macro list.
 *
 * @author -Nemesiss-
 */
public class SM_MACRO_LIST extends AionServerPacket {

    private Player player;

    /**
     * Constructs new <tt>SM_MACRO_LIST </tt> packet
     */
    public SM_MACRO_LIST(Player player) {
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(player.getObjectId());// player id

        int size = player.getMacroList().getSize();

        writeC(0x01);
        writeH(-size);

        if (size != 0) {
            for (Map.Entry<Integer, String> entry : player.getMacroList().getMacrosses().entrySet()) {
                writeC(entry.getKey());// order
                writeS(entry.getValue());// xml
            }
        }
    }
}
