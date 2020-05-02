package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.LoginEventService;


public class CM_LOGIN_REWARD extends AionClientPacket {

	private int timestamp;
  private int count;
  private List<Integer> passportId = new ArrayList<Integer>();
  
	public CM_LOGIN_REWARD(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}
	
	@Override
  protected void readImpl() {
      count = readH();
      for (int i = 0; i < count; i++) {
      	passportId.add(readD());
          timestamp = readD();
      }

  }

  /* (non-Javadoc)
   * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
   */
  @Override
  protected void runImpl() {
      Player player = getConnection().getActivePlayer();
      if (player == null)
          return;
      LoginEventService.getInstance().getReward(player, timestamp, passportId);

  }
}
