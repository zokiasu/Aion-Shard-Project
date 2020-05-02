package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;

/**
 * @author Ben
 */
public class CM_FRIEND_DEL extends AionClientPacket {

    private String targetName;
    private static Logger log = LoggerFactory.getLogger(CM_FRIEND_DEL.class);

    public CM_FRIEND_DEL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        targetName = readS();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {

        Player activePlayer = getConnection().getActivePlayer();
        Friend target = activePlayer.getFriendList().getFriend(targetName);
        if (target == null) {
            log.warn(activePlayer.getName() + " tried to delete friend " + targetName + " who is not his friend");
            sendPacket(SM_SYSTEM_MESSAGE.STR_BUDDYLIST_NOT_IN_LIST);
        } else {
            SocialService.deleteFriend(activePlayer, target.getOid());
        }
    }
}
