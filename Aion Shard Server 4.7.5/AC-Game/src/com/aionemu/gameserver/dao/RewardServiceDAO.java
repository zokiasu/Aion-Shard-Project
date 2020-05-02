package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.templates.rewards.RewardEntryItem;
import javolution.util.FastList;

/**
 * @author KID
 */
public abstract class RewardServiceDAO implements DAO {

    @Override
    public final String getClassName() {
        return RewardServiceDAO.class.getName();
    }

    public abstract FastList<RewardEntryItem> getAvailable(int playerId);

    public abstract void uncheckAvailable(FastList<Integer> ids);
}
