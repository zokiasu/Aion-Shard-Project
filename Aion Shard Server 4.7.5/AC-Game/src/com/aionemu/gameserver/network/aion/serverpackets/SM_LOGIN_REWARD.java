package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dao.PlayerLoginRewardDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.login_event.LoginRewardTemplate;
import com.aionemu.gameserver.model.templates.login_event.LoginType;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Ranastic
 */

public class SM_LOGIN_REWARD extends AionServerPacket
{
	Logger log = LoggerFactory.getLogger(SM_LOGIN_REWARD.class);
	private Calendar calendar = Calendar.getInstance();
	private Calendar creationDate = Calendar.getInstance();
	private Collection<LoginRewardTemplate> rewardtemp;
	private Player player;
	private int finishedId;
	
	public SM_LOGIN_REWARD(Player player, Collection<LoginRewardTemplate> collection) {
		this.player = player;
		this.rewardtemp = collection;
	}
	
	public SM_LOGIN_REWARD(Player player, Collection<LoginRewardTemplate> collection, int id) {
		this.player = player;
		this.rewardtemp = collection;
		this.finishedId = id;
	}
	
	@Override
	protected void writeImpl(AionConnection con) {
		PlayerDAO playerDAO = DAOManager.getDAO(PlayerDAO.class);
		PlayerLoginRewardDAO plrDAO = DAOManager.getDAO(PlayerLoginRewardDAO.class);
		creationDate.setTime(new Date(playerDAO.getCharacterCreationDateId(player.getObjectId()).getTime()));
		writeH(creationDate.get(Calendar.YEAR));
		writeH(creationDate.get(Calendar.MONTH)+1);
		writeH(creationDate.get(Calendar.DAY_OF_MONTH));
		writeH(rewardtemp.size());
		for (LoginRewardTemplate a : rewardtemp) {
			if (a.isActive()) {
				if (a.getId() == finishedId)
					writeD(0);
				else
					writeD(a.getId());
				
				if (a.getAttendNum() == calendar.get(Calendar.DAY_OF_MONTH) && a.getType() == LoginType.CUMULATIVE)
					writeD(0);
				else
					writeD(plrDAO.getLoginCountByObjAndActivatedEventId(player.getObjectId(), a.getId()));
				
				writeD(a.getType().getId());
				writeD((int) player.getCommonData().getLastOnline().getTime() / 1000);
			}
		}
	}
}