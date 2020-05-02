package com.aionemu.gameserver.model.team2.alliance.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceService;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerEnteredEvent implements Predicate<PlayerAllianceMember>, TeamEvent {

    private final PlayerAlliance alliance;
    private final Player invited;
    private PlayerAllianceMember invitedMember;

    public PlayerEnteredEvent(PlayerAlliance alliance, Player player) {
        this.alliance = alliance;
        this.invited = player;
    }

    /**
     * Entered player should not be in group yet
     */
    @Override
    public boolean checkCondition() {
        return !alliance.hasMember(invited.getObjectId());
    }

    @Override
    public void handleEvent() {
        PlayerAllianceService.addPlayerToAlliance(alliance, invited);

        invitedMember = alliance.getMember(invited.getObjectId());

        PacketSendUtility.sendPacket(invited, new SM_ALLIANCE_INFO(alliance));
        PacketSendUtility.sendPacket(invited, new SM_SHOW_BRAND(0, 0));
        PacketSendUtility.sendPacket(invited, SM_SYSTEM_MESSAGE.STR_FORCE_ENTERED_FORCE);
        PacketSendUtility.sendPacket(invited, new SM_ALLIANCE_MEMBER_INFO(invitedMember, PlayerAllianceEvent.JOIN));

        alliance.apply(this);
    }

    @Override
    public boolean apply(PlayerAllianceMember member) {
        Player player = member.getObject();
        if (!invited.getObjectId().equals(player.getObjectId())) {
            PacketSendUtility.sendPacket(player, new SM_ALLIANCE_MEMBER_INFO(invitedMember, PlayerAllianceEvent.JOIN));
            PacketSendUtility.sendPacket(player, new SM_INSTANCE_INFO(invited, false, alliance));
            PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_FORCE_HE_ENTERED_FORCE(invited.getName()));

            PacketSendUtility.sendPacket(invited, new SM_ALLIANCE_MEMBER_INFO(member, PlayerAllianceEvent.ENTER));
        }
        return true;
    }
}
