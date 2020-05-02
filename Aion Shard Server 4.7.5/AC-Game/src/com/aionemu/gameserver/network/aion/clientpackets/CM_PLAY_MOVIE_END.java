package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;

/**
 * @author MrPoke
 */
public class CM_PLAY_MOVIE_END extends AionClientPacket {

    @SuppressWarnings("unused")
    private int type;
    @SuppressWarnings("unused")
    private int targetObjectId;
    @SuppressWarnings("unused")
    private int dialogId;
    private int movieId;
    @SuppressWarnings("unused")
    private int unk;

    public CM_PLAY_MOVIE_END(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        type = readC();
        targetObjectId = readD();
        dialogId = readD();
        movieId = readH();
        unk = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        QuestEngine.getInstance().onMovieEnd(new QuestEnv(null, player, 0, 0), movieId);
        player.getPosition().getWorldMapInstance().getInstanceHandler().onPlayMovieEnd(player, movieId);
    }
}
