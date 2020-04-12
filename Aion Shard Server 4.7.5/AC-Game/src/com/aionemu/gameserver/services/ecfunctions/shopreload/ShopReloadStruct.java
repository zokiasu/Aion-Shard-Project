package com.aionemu.gameserver.services.ecfunctions.shopreload;

import com.aionemu.gameserver.configs.main.EventSystem;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import java.util.concurrent.ScheduledFuture;

/**
 * @author Zokiasu
 */
public interface ShopReloadStruct {

    ScheduledFuture<?> autoReload();

}
