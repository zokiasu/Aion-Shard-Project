package com.aionemu.gameserver.model.beritra;

import java.util.*;
import javolution.util.FastMap;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.beritra.BeritraTemplate;
import com.aionemu.gameserver.services.beritraservice.BeritraInvasion;


public class BeritraLocation
{
	protected int id;
	protected boolean isActive;
	protected BeritraTemplate template;
	protected BeritraInvasion<BeritraLocation> activeBeritra;
	protected FastMap<Integer, Player> players = new FastMap<Integer, Player>();
	private final List<VisibleObject> spawned = new ArrayList<VisibleObject>();
	
	public BeritraLocation() {
	}
	
	public BeritraLocation(BeritraTemplate template) {
		this.template = template;
		this.id = template.getId();
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	public void setActiveBeritra(BeritraInvasion<BeritraLocation> beritra) {
		isActive = beritra != null;
		this.activeBeritra = beritra;
	}
	
	public BeritraInvasion<BeritraLocation> getActiveBeritra() {
		return activeBeritra;
	}
	
	public final BeritraTemplate getTemplate() {
		return template;
	}
	
	public int getId() {
		return id;
	}
	
	public List<VisibleObject> getSpawned() {
		return spawned;
	}
	
	public FastMap<Integer, Player> getPlayers() {
		return players;
	}
}