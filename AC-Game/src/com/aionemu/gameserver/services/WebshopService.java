package com.aionemu.gameserver.services;

import java.util.Collection;
import java.util.Set;

import javolution.util.FastSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dao.WebshopDAO;
import com.aionemu.gameserver.model.Webshop;
import com.aionemu.gameserver.services.mail.MailFormatter;

/**
 * Custom Webshop System
 *
 * @author Blackfire
 */
public class WebshopService {

    private static final Logger log = LoggerFactory.getLogger(WebshopService.class);
    
	private String done = "TRUE";
		    
    private WebshopService() {
        this.load();
    }

    public static final WebshopService getInstance() {
		log.info("Starting Webshop service...");
        return SingletonHolder.instance;
    }

    /**
     * Check webshop entry for non delivered mail every 2 minutes
     */
    private void load() {
		if (CustomConfig.WEBSHOP_ENABLED){
			log.info("Webshop service loaded successfully");
			ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					Collection<Webshop> webshops;
					webshops = new FastSet<Webshop>(getDAO().getWebshop()).shared();
					
					for (final Webshop webshop : webshops) {
						if (webshop.getSend().equals("FALSE")) {
							MailFormatter.sendBlackCloudMail(webshop.getRecipient(), webshop.getItemId(), webshop.getCount());
							setWebshop(done, webshop.getId());
						}
					}
				}
			}, 30000, 30000);
		} else {
			log.info("Webshop service disable");	
		}
	}
	
	public void setWebshop(String send, int id) {
        getDAO().setWebshop(send, id);
    }

    public Set<Webshop> getWebshop() {
        return getDAO().getWebshop();
    }
	
	private WebshopDAO getDAO() {
        return DAOManager.getDAO(WebshopDAO.class);
    }

    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final WebshopService instance = new WebshopService();
    }
}
