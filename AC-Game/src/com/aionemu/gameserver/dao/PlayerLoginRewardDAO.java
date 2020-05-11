package com.aionemu.gameserver.dao;

import java.sql.Timestamp;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.LoginReward;

/**
 * @author Ranastic
 */

public abstract class PlayerLoginRewardDAO implements DAO
{
	@Override
	public String getClassName() {
		return PlayerLoginRewardDAO.class.getName();
	}
	
	public abstract LoginReward load(final int playerId);
	public abstract boolean addLoginReward(final int playerId, final int activated_id, final int login_count, final Timestamp next_login_count);
	public abstract boolean delLoginReward(final int playerId, final int activated_id, final int login_count, final Timestamp next_login_count);
	public abstract int getLoginCountByObjAndActivatedEventId(final int obj, final int activated_id);
	public abstract Timestamp getNextLoginCountbyObjAndActivatedEventId(final int obj, final int activated_id);
}