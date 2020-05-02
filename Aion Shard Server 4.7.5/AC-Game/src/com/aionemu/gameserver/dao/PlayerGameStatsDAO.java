package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @author Glass
 */
public abstract class PlayerGameStatsDAO implements DAO {

    @Override
    public final String getClassName() {
        return PlayerGameStatsDAO.class.getName();
    }

    /* 
     loaded into PlayerService (at the player connection)
     */

    /**
     * @param player
     */
    public abstract void insertPlayerGameStat(Player player);

    /**
     * @param player
     */
    public abstract void updatePlayerGameStat(Player player);

    /**
     * @param player
     */
    public abstract void deletePlayerGameStat(int playerId);
}
