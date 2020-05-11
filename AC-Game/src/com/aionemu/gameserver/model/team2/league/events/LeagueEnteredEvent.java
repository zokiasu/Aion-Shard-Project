package com.aionemu.gameserver.model.team2.league.events;

import com.aionemu.gameserver.model.team2.TeamEvent;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.league.League;
import com.aionemu.gameserver.model.team2.league.LeagueMember;
import com.aionemu.gameserver.model.team2.league.LeagueService;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SHOW_BRAND;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class LeagueEnteredEvent implements Predicate<LeagueMember>, TeamEvent {

    private final League league;
    private final PlayerAlliance invitedAlliance;

    public LeagueEnteredEvent(League league, PlayerAlliance alliance) {
        this.league = league;
        this.invitedAlliance = alliance;
    }

    /**
     * Entered alliance should not be in league yet
     */
    @Override
    public boolean checkCondition() {
        return !league.hasMember(invitedAlliance.getObjectId());
    }

    @Override
    public void handleEvent() {
        LeagueService.addAllianceToLeague(league, invitedAlliance);
        league.apply(this);
    }

    @Override
    public boolean apply(LeagueMember member) {
        PlayerAlliance alliance = member.getObject();
        alliance.sendPacket(new SM_ALLIANCE_INFO(alliance, SM_ALLIANCE_INFO.LEAGUE_ENTERED, league.getLeaderObject()
                .getLeader().getName()));
        alliance.sendPacket(new SM_SHOW_BRAND(0, 0));
        return true;
    }
}
