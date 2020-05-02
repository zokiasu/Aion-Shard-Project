package com.aionemu.gameserver.services.beritraservice;

import com.aionemu.gameserver.model.beritra.BeritraLocation;
import com.aionemu.gameserver.model.beritra.BeritraStateType;


public class Invade extends BeritraInvasion<BeritraLocation>
{
	public Invade(BeritraLocation beritra) {
		super(beritra);
	}
	
	@Override
	public void startBeritraInvasion() {
		getBeritraLocation().setActiveBeritra(this);
		despawn();
		spawn(BeritraStateType.INVASION);
		initWorldBoss();
	}
	
	@Override
	public void stopBeritraInvasion() {
		getBeritraLocation().setActiveBeritra(null);
		rmvWorldBossListener();
		despawn();
		spawn(BeritraStateType.PEACE);
	}
}