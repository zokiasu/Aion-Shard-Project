package com.aionemu.gameserver.model.team2.alliance.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.team2.common.events.AlwaysTrueTeamEvent;
import com.aionemu.gameserver.model.team2.common.legacy.PlayerAllianceEvent;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerAllianceUpdateEvent extends AlwaysTrueTeamEvent implements Predicate<PlayerAllianceMember> {

    private final PlayerAlliance alliance;
    private final Player player;
    private final PlayerAllianceEvent allianceEvent;
    private final PlayerAllianceMember updateMember;

    public PlayerAllianceUpdateEvent(PlayerAlliance alliance, Player player, PlayerAllianceEvent allianceEvent) {
        this.alliance = alliance;
        this.player = player;
        this.allianceEvent = allianceEvent;
        this.updateMember = alliance.getMember(player.getObjectId());
    }

    @Override
    public void handleEvent() {
        switch (allianceEvent) {
            case MOVEMENT:
            case UPDATE:
                alliance.apply(this);
                break;
            default:
                // Unsupported
                break;
        }

    }

    @Override
    public boolean apply(PlayerAllianceMember member) {
        if (!member.getObjectId().equals(player.getObjectId())) {
            PacketSendUtility.sendPacket(member.getObject(), new SM_ALLIANCE_MEMBER_INFO(updateMember, allianceEvent));
        }
        return true;
    }
}
