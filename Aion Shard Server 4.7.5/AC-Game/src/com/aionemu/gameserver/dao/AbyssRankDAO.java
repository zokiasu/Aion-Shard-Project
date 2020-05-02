package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.AbyssRankingResult;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * @author ATracer
 */
public abstract class AbyssRankDAO implements DAO {

    @Override
    public final String getClassName() {
        return AbyssRankDAO.class.getName();
    }

    public abstract List<Integer> rankPlayers(final int rank); // DailyReduceGp

	public abstract void updateGloryPoints(final int playerId, final int gp); // DailyReduceGp

    public abstract void loadAbyssRank(Player player);

    public abstract AbyssRank loadAbyssRank(int playerId);

    public abstract boolean storeAbyssRank(Player player);

    public abstract ArrayList<AbyssRankingResult> getAbyssRankingPlayers(Race race, final int maxOfflineDays);

    public abstract ArrayList<AbyssRankingResult> getAbyssRankingLegions(Race race);

    public abstract Map<Integer, Integer> loadPlayersAp(Race race, final int lowerApLimit, final int maxOfflineDays);

    public abstract Map<Integer, Integer> loadPlayersGp(Race race, final int lowerGpLimit, final int maxOfflineDays);

    public abstract void updateAbyssRank(int playerId, AbyssRankEnum rankEnum);

    public abstract void updateRankList(final int maxOfflineDays);
}