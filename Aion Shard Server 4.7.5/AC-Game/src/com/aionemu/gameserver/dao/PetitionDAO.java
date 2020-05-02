package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.Petition;

import java.util.Set;

/**
 * @author zdead
 */
public abstract class PetitionDAO implements DAO {

    public abstract int getNextAvailableId();

    public abstract void insertPetition(Petition p);

    public abstract void deletePetition(int playerObjId);

    public abstract Set<Petition> getPetitions();

    public abstract Petition getPetitionById(int petitionId);

    public abstract void setReplied(int petitionId);

    @Override
    public final String getClassName() {
        return PetitionDAO.class.getName();
    }
}
