package com.aionemu.gameserver.taskmanager.fromdb.handler;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.player.FatigueService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.knownlist.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;


/**
 * @author Alcpawnd
 */
public class FatigueHandler extends TaskFromDBHandler {
    private int countDown;
    private static final Logger log = LoggerFactory.getLogger(FatigueHandler.class);
    private Calendar calendar = Calendar.getInstance();

    @Override
    public boolean isValid() {
        if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
            return false;
        }
        if (params.length == 1) {
            try {
                countDown = Integer.parseInt(params[0]);

                return true;
            } catch (NumberFormatException e) {
                log.warn("Invalid parameters for FatigueHandler. Only valid integers allowed - not registered", e);
            }
        }
        log.warn("FatigueHandler has more than 1 parameters - not registered");
        return false;
    }

    @Override
    public void trigger() {
        log.info("Task[" + taskId + "] launched : fatigue reset got started !");

        World.getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Automatic Task: The fatigue will be reset in " + countDown
                        + " seconds !");
            }
        });

        ThreadPoolManager.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                FatigueService.getInstance().resetFatigue();
            }
        }, countDown * 1000);

    }

}
