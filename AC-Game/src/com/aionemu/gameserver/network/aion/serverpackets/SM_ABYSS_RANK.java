package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

/**
 * @author Nemiroff Date: 25.01.2010
 * @author GiGatR00n v4.7.5.x 
 */
public class SM_ABYSS_RANK extends AionServerPacket {

    private AbyssRank rank;
    private int currentRankId;

    public SM_ABYSS_RANK(AbyssRank rank) {
        this.rank = rank;
        this.currentRankId = rank.getRank().getId();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeQ(rank.getAp()); // curAP
        writeD(rank.getGp()); // curGP
        writeD(currentRankId); // curRank
        writeD(rank.getRankPosition()); // curRanking

        if (currentRankId <= 9) {
            int nextRankId = currentRankId < AbyssRankEnum.values().length ? currentRankId + 1 : currentRankId;
            writeD(100 * rank.getAp() / AbyssRankEnum.getRankById(nextRankId).getRequired());
        } else if (currentRankId > 9 && currentRankId <= 18) {
            int nextGpRankId = currentRankId < AbyssRankEnum.values().length ? currentRankId + 1 : currentRankId;
            writeD(100 * rank.getGp() / AbyssRankEnum.getRankById(nextGpRankId).getRequired());
        }

        writeD(rank.getAllKill()); // allKill
        writeD(rank.getMaxRank()); // maxRank

        writeD(rank.getDailyKill()); // dayKill
        writeQ(rank.getDailyAP()); // dayAP
        writeD(rank.getDailyGP()); // dayGP

        writeD(rank.getWeeklyKill()); // weekKill
        writeQ(rank.getWeeklyAP()); // weekAP
        writeD(rank.getWeeklyGP()); // weekGP

        writeD(rank.getLastKill()); // laterKill
        writeQ(rank.getLastAP()); // laterAP
        writeD(rank.getLastGP()); // laterGP

        writeC(0); // unk 4.7.5.x
    }
}