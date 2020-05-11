package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Nemiroff Date: 17.02.2010
 */
public class SM_ABYSS_RANK_UPDATE extends AionServerPacket {

    private Player player;
    private int action;

    public SM_ABYSS_RANK_UPDATE(int action, Player player) {
        this.action = action;
        this.player = player;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeC(action);
        writeD(player.getObjectId());
        switch (action) {
            case 0:
                writeD(player.getAbyssRank().getRank().getId());
                break;
            case 1:
                if (player.isMentor()) {
                    writeD(1);
                } else {
                    writeD(0);
                }
                break;
            case 2:
                if (player.isMentor()) {
                    writeD(1);
                } else {
                    writeD(0);
                }
        }
    }
}
