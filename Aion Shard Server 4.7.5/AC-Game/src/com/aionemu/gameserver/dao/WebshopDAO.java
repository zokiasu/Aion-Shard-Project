package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.Webshop;

import java.util.Set;

/**
 * @author Blackfire
 */
public abstract class WebshopDAO implements DAO {

    public abstract Set<Webshop> getWebshop();
	public abstract void setWebshop(String send, int id);

   @Override
    public final String getClassName() {
        return WebshopDAO.class.getName();
    }
}
