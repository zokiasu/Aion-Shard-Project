package com.aionemu.gameserver.model.templates.spawns.beritraspawns;

import com.aionemu.gameserver.model.templates.spawns.SpawnGroup2;
import com.aionemu.gameserver.model.templates.spawns.SpawnSpotTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.model.beritra.BeritraStateType;

/**
 * @author Rinzler (Encom)
 */

public class BeritraSpawnTemplate extends SpawnTemplate
{
	private int id;
	private BeritraStateType beritraType;
	
	public BeritraSpawnTemplate(SpawnGroup2 spawnGroup, SpawnSpotTemplate spot) {
		super(spawnGroup, spot);
	}
	
	public BeritraSpawnTemplate(SpawnGroup2 spawnGroup, float x, float y, float z, byte heading, int randWalk, String walkerId, int entityId, int fly) {
		super(spawnGroup, x, y, z, heading, randWalk, walkerId, entityId, fly);
	}
	
	public int getId() {
		return id;
	}
	
	public BeritraStateType getBStateType() {
		return beritraType;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setBStateType(BeritraStateType beritraType) {
		this.beritraType = beritraType;
	}
	
	public final boolean isBeritraInvasion() {
		return beritraType.equals(BeritraStateType.INVASION);
	}
	
	public final boolean isBeritraPeace() {
		return beritraType.equals(BeritraStateType.PEACE);
	}
}