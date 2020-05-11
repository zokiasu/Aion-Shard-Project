package com.aionemu.gameserver.model.templates.login_event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Ranastic
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reward")
public class LoginReward
{
	@XmlAttribute(name = "item_id")
	protected int item_id;
	
	@XmlAttribute(name = "count")
	protected int count;
	
	@XmlAttribute(name = "expire_time")
	protected int expire_time;
	
	@XmlAttribute(name = "reward_title")
	protected int reward_title;
	
	public int getItemId() {
		return item_id;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getExpireTime() {
		return expire_time;
	}
	
	public int getRewardTitle() {
		return reward_title;
	}
}