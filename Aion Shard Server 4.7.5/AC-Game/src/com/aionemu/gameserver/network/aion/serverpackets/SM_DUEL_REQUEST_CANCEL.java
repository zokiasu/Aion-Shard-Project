package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

public class SM_DUEL_REQUEST_CANCEL extends AionServerPacket {
    private int msgId;
    private String playerName;

    public SM_DUEL_REQUEST_CANCEL(int var1, String var2) {
        this.msgId = var1;
        this.playerName = var2;
    }

    protected void writeImpl(AionConnection var1) {
        this.writeD(0);
        this.writeD(this.msgId);
        this.writeS(this.playerName);
        this.writeD(0);
    }
}