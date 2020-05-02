package com.aionemu.gameserver.model.team2.alliance.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerDisconnectedEvent implements TeamEvent, Predicate<PlayerAllianceMember> {

    private final PlayerAlliance alliance;
    private final Player disconnected;
    private final PlayerAllianceMember disconnectedMember;

    public PlayerDisconnectedEvent(PlayerAlliance alliance, Player player) {
        this.alliance = alliance;
        this.disconnected = player;
        this.disconnectedMember = alliance.getMember(disconnected.getObjectId());
    }

    /**
     * Player should be in alliance before disconnection
     */
    @Override
    public boolean checkCondition() {
        return alliance.hasMember(disconnected.getObjectId());
    }

    @Override
    public void handleEvent() {
        Preconditions.checkNotNull(disconnectedMember, "Disconnected member should not be null");
        alliance.apply(this);
        if (alliance.onlineMembers() <= 1) {
            PlayerAllianceService.disband(alliance);
        } else {
            if (disconnected.equals(alliance.getLeader().getObject())) {
                alliance.onEvent(new ChangeAllianceLeaderEvent(alliance));
            }
        }
    }

    @Override
    public boolean apply(PlayerAllianceMember member) {
        Player player = member.getObject();
        if (!disconnected.getObjectId().equals(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE(disconnected.getName()));
            PacketSendUtility.sendPacket(player, new SM_ALLIANCE_MEMBER_INFO(disconnectedMember,
                    PlayerAllianceEvent.DISCONNECTED));
        }
        return true;
    }
}
