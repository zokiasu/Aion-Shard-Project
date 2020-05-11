package com.aionemu.gameserver.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.administration.DeveloperConfig;


public class PacketLoggerService {

    private static final Logger log = LoggerFactory.getLogger(PacketLoggerService.class);
    
    public void logPacketCM(String name) 
    {
		if (DeveloperConfig.SHOW_PACKETS) {
			log.info("[PACKET CLIENT] " + name);
		}
    }    
    
    public void logPacketSM(String name)
    {    	
		if (DeveloperConfig.SHOW_PACKETS) {
			log.info("[PACKET SERVER] " + name);
		}	
    }
    
    @SuppressWarnings("synthetic-access")
    private static class SingletonHolder {
        protected static final PacketLoggerService instance = new PacketLoggerService();
    }

    public static final PacketLoggerService getInstance() {
        return SingletonHolder.instance;
    }    
}
