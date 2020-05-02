package com.aionemu.gameserver.services;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.FastTrackConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SERVER_IDS;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.transfers.FastTrack;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldType;

/**
 * @author Eloann - Enomine
 */
public class FastTrackService {


    private Logger log = LoggerFactory.getLogger(FastTrackService.class);
    
    private static final FastTrackService instance = new FastTrackService();
    private Map<Integer, Player> accountsOnFast = new HashMap<Integer, Player>(1);

    /**
	 * @param player  
     * @param off 
	 */
    public void fastTrackBonus(Player player, boolean off) {
    }
    
    public boolean isPvPZone(WorldType wt) {
    	return wt == WorldType.BALAUREA || wt == WorldType.ABYSS;
    }
    
    public boolean isNormalZone(WorldType wt) {
    	return wt == WorldType.ASMODAE || wt == WorldType.ELYSEA || wt == WorldType.NONE;
    }    
    
    /**
     * @param player
     */
    public void checkAuthorizationRequest(Player player) {
        int upto = FastTrackConfig.FASTTRACK_MAX_LEVEL;
        if (player.getLevel() > upto) {
            return;
        }
        PacketSendUtility.sendPacket(player, new SM_SERVER_IDS(new FastTrack(FastTrackConfig.FASTTRACK_SERVER_ID, true, 1, upto)));
    }

    public void handleMoveThere(Player player) {
        TeleportService2.moveFastTrack(player, FastTrackConfig.FASTTRACK_SERVER_ID, false);
    }

    public void handleMoveBack(Player player) {
        TeleportService2.moveFastTrack(player, FastTrackConfig.FASTTRACK_SERVER_ID, true);
    }

    public void checkFastTrackMove(Player player, int accId, boolean back) {
        if (back) {
            accountsOnFast.remove(accId);
            player.setOnFastTrack(false);
            PacketSendUtility.sendYellowMessage(player, "You joined the standard server!");
            fastTrackBonus(player, true);
        } else {
            if (accountsOnFast.containsKey(accId)) {
                log.warn("Fast Track Service: Player " + player.getName() + " tried to move twice to ft server!");
                accountsOnFast.remove(accId);
                handleMoveBack(player);
                PacketSendUtility.sendYellowMessage(player, "You got teleported back to the normal server because you tried to enter the fast track server twice!");
            }

            if (accountsOnFast.containsKey(accId) && !accountsOnFast.containsValue(player)) {
                log.warn("Fast Track Service: Player " + player.getName() + " got wrong accid???");
                handleMoveBack(player);
                PacketSendUtility.sendYellowMessage(player, "You got teleported back to the normal server because something went wrong!");
            }

            accountsOnFast.put(accId, player);
            player.setOnFastTrack(true);
            PacketSendUtility.sendYellowMessage(player, "You joined the fast track server!");
            fastTrackBonus(player, false);
        }

    }
    
    public static FastTrackService getInstance() {
        return instance;
    }    
}