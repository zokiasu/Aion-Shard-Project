package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;

/**
 * @author ATracer
 */
public class SM_CS_PLAYER_AUTH extends CsServerPacket {

    private int playerId;
    private String playerLogin;
    private String nick;

    public SM_CS_PLAYER_AUTH(int playerId, String playerLogin, String nick) {
        super(0x01);
        this.playerId = playerId;
        this.playerLogin = playerLogin;
        this.nick = nick;
    }

    @Override
    protected void writeImpl(ChatServerConnection con) {
        writeD(playerId);
        writeS(playerLogin);
        writeS(nick);
    }
}
