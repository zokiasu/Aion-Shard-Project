package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.challenge.ChallengeTask;
import com.aionemu.gameserver.model.templates.challenge.ChallengeType;

import java.util.Map;

/**
 * @author ViAl
 */
public abstract class ChallengeTasksDAO implements DAO {

    public abstract Map<Integer, ChallengeTask> load(int ownerId, ChallengeType type);

    public abstract void storeTask(ChallengeTask task);

    @Override
    public String getClassName() {
        return ChallengeTasksDAO.class.getName();
    }
}
