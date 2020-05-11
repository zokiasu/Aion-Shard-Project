package com.aionemu.gameserver.dataholders;

import gnu.trove.map.hash.THashMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;

/**
 * 
 * @author Ranastic
 *
 */
@XmlRootElement(name = "login_rewards")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginEventData {
	
	@XmlElement(name = "login_reward")
	private List<LoginRewardTemplate> login_reward;
	
	@XmlTransient
	private FastMap<Integer, LoginRewardTemplate> reward = new FastMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private THashMap<Integer, LoginRewardTemplate> activeEvents = new THashMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private THashMap<Integer, LoginRewardTemplate> allEvents = new THashMap<Integer, LoginRewardTemplate>();
	
	@XmlTransient
	private int counter = 0;
	
	/**
	 * @param u  
	 * @param parent 
	 */
	void afterUnmarshal(Unmarshaller u, Object parent) {
		counter = 0;
		activeEvents.clear();
		allEvents.clear();
		Set<Integer> ae = new HashSet<Integer>();
		for (LoginRewardTemplate template : login_reward) {
			if (ae.contains(template.getId()) && template.isActive()) {
				activeEvents.put(template.getId(), new LoginRewardTemplate());
				counter++;
			}
			allEvents.put(template.getId(), template);
			reward.put(template.getId(), new LoginRewardTemplate());
		}
	}
	
	public int size() {
		return reward.size();
	}

	public FastMap<Integer, LoginRewardTemplate> getloginTemplate() {
		return reward;
	}
	
	public List<LoginRewardTemplate> getAllEvents() {
		List<LoginRewardTemplate> result = new ArrayList<LoginRewardTemplate>();
		synchronized (allEvents) {
			result.addAll(allEvents.values());
		}

		return result;
	}
	
	public List<LoginRewardTemplate> getActiveEvents() {
		List<LoginRewardTemplate> result = new ArrayList<LoginRewardTemplate>();
		synchronized (activeEvents) {
			result.addAll(activeEvents.values());
		}

		return result;
	}
	
	public boolean Contains(int eventId) {
		return activeEvents.containsKey(eventId);
	}
	
	public LoginRewardTemplate getLoginRewardTemplateById(int id) {
		return reward.get(id);
	}
}