package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.Date;

/**
 * @author blakawk, Dr.Nism
 */
public abstract class PlayerWorldBanDAO implements DAO {

    @Override
    public String getClassName() {
        return PlayerWorldBanDAO.class.getName();
    }

    public abstract void loadWorldBan(Player player);

    public abstract boolean addWorldBan(int playerObjId, String by, long duration, Date date, String reason);

    public abstract void removeWorldBan(int playerObjId);
}
