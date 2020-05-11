package com.aionemu.gameserver.model.gameobjects.player;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;

/**
 * @author Ranastic
 */

public class LoginReward
{
	private int eventId;
	private int loginCount;
	private Timestamp nextLoginCount;
	private Set<Integer> loginRewardList = new HashSet<Integer>();
	final Logger log = LoggerFactory.getLogger(LoginReward.class);
	
	public LoginReward(int eventId, int loginCount, Timestamp nextLoginCount) {
		this.eventId = eventId;
		this.loginCount = loginCount;
		this.nextLoginCount = nextLoginCount;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public int getLoginCount() {
		return loginCount;
	}
	
	public Timestamp getNextLoginCount() {
		return nextLoginCount;
	}
	
	public void setNextLoginCount(Timestamp time) {
		nextLoginCount = time;
	}
	
	public LoginReward(HashSet<Integer> loginRewardList) {
		this.loginRewardList = loginRewardList;
	}
	
	public LoginReward() {}
	
	public Set<Integer> getRecipeList() {
		return loginRewardList;
	}
	
	public void addLoginReward(Player player, LoginRewardTemplate template) {
		int eventId = template.getId();
		if (!player.getLoginReward().isLoginRewardPresent(eventId) && eventId != 0) {
			if (DAOManager.getDAO(PlayerLoginRewardDAO.class).addLoginReward(player.getObjectId(), eventId, getLoginCount(), getNextLoginCount())) {
				loginRewardList.add(eventId);
			}
		}
	}
	
	public void addLoginReward(int playerId, int eventId, int loginCount, Timestamp nextLoginCount) {
		if (DAOManager.getDAO(PlayerLoginRewardDAO.class).addLoginReward(playerId, eventId, loginCount, nextLoginCount)) {
			loginRewardList.add(eventId);
		}
	}
	
	public void deleteLoginReward(Player player, int eventId, int loginCount, Timestamp nextLoginCount) {
		if (loginRewardList.contains(eventId)) {
			if (DAOManager.getDAO(PlayerLoginRewardDAO.class).delLoginReward(player.getObjectId(), eventId, loginCount, nextLoginCount)) {
				loginRewardList.remove(eventId);
			}
		}
	}
	
	public boolean isLoginRewardPresent(int eventId) {
		return loginRewardList.contains(eventId);
	}
	
	public int size() {
		return this.loginRewardList.size();
	}
}