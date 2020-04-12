package com.aionemu.gameserver.services.ecfunctions.shopreload;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.effect.AbnormalState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import javolution.util.FastList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author Kill3r
 */
public class ShopReloadService implements ShopReloadStruct{

    private static final Logger log = LoggerFactory.getLogger(ShopReloadService.class);
    private static final ShopReloadService Service = new ShopReloadService();
    public List<Player> playersToArena = new FastList<Player>();
    public boolean Initialized = false;

    public static ShopReloadService getInstance(){
        return Service;
    }

    @Override
    public ScheduledFuture autoReload(){
        Initialized = true;
        log.info("Starting Shop Reload Service...");
        return ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                announceEveryOne("Shop", "ShopReload"); // later make config for this
            }
        }, 30000, 30000);  // also config for delay Timmer
    }

    public void announceEveryOne(final String senderName,final String Message){
        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendSys2Message(object, senderName, Message);
            }
        });
    }
}