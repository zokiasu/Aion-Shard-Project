package com.aionemu.gameserver.services.ecfunctions.oneVsone;

import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Kill3r
 */
public interface OneVsOneStruct {

    int worldid[] = {EventSystem.ONEVSONE_MAP, EventSystem.ONEVSONE_MAP2, EventSystem.ONEVSONE_MAP3};

    ScheduledFuture<?> autoAnnounce(int delayInMin);

    ScheduledFuture<?> duelArenaRegChecker(int deLayInMin);

    boolean isEnemy(Player effector, Player effected);

    void increaseCode();
}
