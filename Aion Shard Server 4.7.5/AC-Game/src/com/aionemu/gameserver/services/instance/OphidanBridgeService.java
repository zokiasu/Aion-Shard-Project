package com.aionemu.gameserver.services.instance;

import java.util.Iterator;

import javolution.util.FastList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

/**
 * @author Ever
 * @author GiGatR00n v4.7.5.x
 */
public class OphidanBridgeService {

	private static final Logger log = LoggerFactory.getLogger(OphidanBridgeService.class);
    private boolean registerAvailable;
    private FastList<Integer> playersWithCooldown = new FastList<Integer>();
    public static final byte minlevel = 60, maxlevel = 66;
    public static final int maskId = 108;

    /**
     * instantiate class 
     */
    private static class SingletonHolder {
        protected static final OphidanBridgeService instance = new OphidanBridgeService();
    }

    public static OphidanBridgeService getInstance() {
        return SingletonHolder.instance;
    }
    
    public void start() {
        String[] times = AutoGroupConfig.OPHIDAN_TIMES.split("\\|");
        for (String cron : times) {
            CronService.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    startOphidanRegistration();
                }
            }, cron);
            log.info("Scheduled Engulfed Ophidan Bridge based on cron expression: " + cron + " Duration: " + AutoGroupConfig.OPHIDAN_TIMER + " in minutes");
        }
    }

    private void startUregisterOphidanTask() {
        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                registerAvailable = false;
                playersWithCooldown.clear();
                AutoGroupService.getInstance().unRegisterInstance(maskId);
                Iterator<Player> iter = World.getInstance().getPlayersIterator();
                while (iter.hasNext()) {
                    Player player = iter.next();
                    if (player.getLevel() > minlevel) {
                        PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(maskId, SM_AUTO_GROUP.wnd_EntryIcon, true));
                    }
                }
            }
        }, AutoGroupConfig.OPHIDAN_TIMER * 60 * 1000);
    }

    private void startOphidanRegistration() {
        this.registerAvailable = true;
        startUregisterOphidanTask();
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            Player player = iter.next();
            if (player.getLevel() > minlevel && player.getLevel() < maxlevel) {
                PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(maskId, SM_AUTO_GROUP.wnd_EntryIcon));
                // You can now participate in the Ophidan Bridge battle.
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_OPEN_OPHIDAN_WAR);
            }
        }
    }

    public boolean isOphidanAvailable() {
        return registerAvailable;
    }

    public void addCoolDown(Player player) {
        playersWithCooldown.add(player.getObjectId());
    }

    public boolean hasCoolDown(Player player) {
        return playersWithCooldown.contains(player.getObjectId());
    }

    public void showWindow(Player player, byte instanceMaskId) {
        if (!playersWithCooldown.contains(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId));
        }
    }
    
    private boolean isInInstance(Player player) {
    	if (player.isInInstance()) {
    		return true;
    	}
        return false;
    }

    public boolean canPlayerJoin(Player player) {
		if (registerAvailable && player.getLevel() > minlevel && player.getLevel() < maxlevel && !hasCoolDown(player) && !isInInstance(player)) {
			 return true;
		}
		return false;
    }
    
}
