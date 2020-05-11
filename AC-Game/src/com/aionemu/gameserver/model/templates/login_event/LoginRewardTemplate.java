package com.aionemu.gameserver.model.templates.login_event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

import com.aionemu.gameserver.utils.gametime.DateTimeUtil;

/**
 * @author Ranastic
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoginReward")
public class LoginRewardTemplate
{
	@XmlAttribute(name = "id")
	protected int id;
	
	@XmlAttribute(name = "active")
	protected boolean active;
	
	@XmlAttribute(name = "start", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar startDate;
	
	@XmlAttribute(name = "end", required = true)
	@XmlSchemaType(name = "dateTime")
	protected XMLGregorianCalendar endDate;
	
	@XmlAttribute(name = "type")
	protected LoginType type;
	
	@XmlAttribute(name = "attend_num")
	protected int attend_num;
	
	@XmlAttribute(name = "permit_level")
	protected int permit_level;
	
	@XmlElement(name = "reward")
	protected LoginReward reward;
	
	public int getId() {
		return id;
	}
	
	public boolean getActive() {
		return active;
	}
	
	public DateTime getStartDate() {
		return DateTimeUtil.getDateTime(startDate.toGregorianCalendar());
	}
	
	public DateTime getEndDate() {
		return DateTimeUtil.getDateTime(endDate.toGregorianCalendar());
	}
	
	public LoginType getType() {
		return type;
	}
	
	public int getAttendNum() {
		return attend_num;
	}
	
	public int getPermitLevel() {
		return permit_level;
	}
	
	public LoginReward getReward() {
		return reward;
	}
	
	public boolean isActive() {
		return getStartDate().isBeforeNow() && getEndDate().isAfterNow();
	}
	
	public boolean isExpired() {
		return !isActive();
	}
	
	public boolean isDaily() {
		return type.getId() == LoginType.DAILY.getId();
	}
	
	public boolean isCumulative() {
		return type.getId() == LoginType.CUMULATIVE.getId();
	}
	
	public boolean isAnniversary() {
		return type.getId() == LoginType.ANNIVERSARY.getId();
	}
}