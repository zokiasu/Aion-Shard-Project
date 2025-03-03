package com.aionemu.gameserver.services.instance;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.services.CronService;
import com.aionemu.gameserver.configs.main.AutoGroupConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_AUTO_GROUP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AutoGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class KamarBattlefieldService {

    private static final Logger log = LoggerFactory.getLogger(KamarBattlefieldService.class);
    
    public static final byte minlevel = 60, maxlevel = 66;
    public static final int maskId = 107;
    private boolean registerAvailable;
    private final FastList<Integer> playersWithCooldown = FastList.newInstance();

    
    public boolean isKamarAvailable() {
        return registerAvailable;
    }
    public byte getInstanceMaskId(Player player) {
        int level = player.getLevel();
        if (level < minlevel || level >= maxlevel) {
            return 0;
        }
        return maskId;
    }
    public void addCoolDown(Player player) {
        playersWithCooldown.add(player.getObjectId());
    }
    public boolean hasCoolDown(Player player) {
        return playersWithCooldown.contains(player.getObjectId());
    }
    public void showWindow(Player player, byte instanceMaskId) {
        if (getInstanceMaskId(player) != instanceMaskId) {
            return;
        }
        if (!playersWithCooldown.contains(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId));
        }
    }

    public void start() {
        String[] times = AutoGroupConfig.KAMAR_TIMES.split("\\|");
        for (String cron : times) {
            CronService.getInstance().schedule(new Runnable() {
                @Override
                public void run() {
                    startKamarRegistration();
                }
            }, cron);
            log.info("Scheduled Kamar Battlefield: based on cron expression: " + cron + " Duration: " + AutoGroupConfig.KAMAR_TIMER + " in minutes");
        }
    }

    private void startKamarRegistration() {
        registerAvailable = true;
        startUregisterKamarTask();
        Iterator<Player> iter = World.getInstance().getPlayersIterator();
        while (iter.hasNext()) {
            Player player = iter.next();
            if (player.getLevel() > minlevel && player.getLevel() < maxlevel) {
                int instanceMaskId = getInstanceMaskId(player);
                if (instanceMaskId > 0) {
                    PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId, SM_AUTO_GROUP.wnd_EntryIcon));
                    PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_MSG_INSTANCE_OPEN_IDKAMAR);
                }
            }
        }
    }    
    
    private void startUregisterKamarTask() {
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
                        int instanceMaskId = getInstanceMaskId(player);
                        if (instanceMaskId > 0) {
                            PacketSendUtility.sendPacket(player, new SM_AUTO_GROUP(instanceMaskId, SM_AUTO_GROUP.wnd_EntryIcon, true));
                        }
                    }
                }
            }
        }, AutoGroupConfig.KAMAR_TIMER * 60 * 1000);
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
    
    private static class SingletonHolder 
    {
        protected static final KamarBattlefieldService instance = new KamarBattlefieldService();
    }

    public static KamarBattlefieldService getInstance() {
        return SingletonHolder.instance;
    }
}
