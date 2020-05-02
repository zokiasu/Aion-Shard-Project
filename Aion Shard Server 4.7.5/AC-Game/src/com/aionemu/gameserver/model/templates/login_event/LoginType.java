package com.aionemu.gameserver.model.templates.login_event;

/**
 * @author Ranastic
 */

public enum LoginType
{
	NONE(0),
	DAILY(1),
	ANNIVERSARY(2),
	CUMULATIVE(3);
	
	private int id;
	
	private LoginType(int id) {
		this.id = id;
	}
	
	public int getId() {
        return id;
    }
	
	public static LoginType getLoginTypeById(int id) {
		for (LoginType emotionType : values()) {
			if (emotionType.getId() == id) {
				return emotionType;
			}
		}
		return NONE;
	}
}