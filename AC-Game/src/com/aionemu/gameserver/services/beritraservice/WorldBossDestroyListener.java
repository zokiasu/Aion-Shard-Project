package com.aionemu.gameserver.services.beritraservice;

import com.aionemu.gameserver.ai2.AbstractAI;
import com.aionemu.gameserver.ai2.eventcallback.OnDieEventCallback;
import com.aionemu.gameserver.services.BeritraService;


@SuppressWarnings("rawtypes")
public class WorldBossDestroyListener extends OnDieEventCallback
{
	private final BeritraInvasion<?> beritra;
	
	public WorldBossDestroyListener(BeritraInvasion beritra) {
		this.beritra = beritra;
	}
	
	@Override
	public void onBeforeDie(AbstractAI obj) {
	}
	
	@Override
	public void onAfterDie(AbstractAI obj) {
	    beritra.setWorldBossDestroyed(true);
		BeritraService.getInstance().stopBeritraInvasion(beritra.getBeritraLocationId());
	}
}