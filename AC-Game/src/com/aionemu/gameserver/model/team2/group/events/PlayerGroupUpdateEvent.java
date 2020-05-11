package com.aionemu.gameserver.model.team2.group.events;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.common.events.AlwaysTrueTeamEvent;
import com.aionemu.gameserver.model.team2.common.legacy.GroupEvent;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.google.common.base.Predicate;

/**
 * @author ATracer
 */
public class PlayerGroupUpdateEvent extends AlwaysTrueTeamEvent implements Predicate<Player> {

    private final PlayerGroup group;
    private final Player player;
    private final GroupEvent groupEvent;

    public PlayerGroupUpdateEvent(PlayerGroup group, Player player, GroupEvent groupEvent) {
        this.group = group;
        this.player = player;
        this.groupEvent = groupEvent;
    }

    @Override
    public void handleEvent() {
        group.applyOnMembers(this);
    }

    @Override
    public boolean apply(Player member) {
        if (!player.equals(member)) {
            PacketSendUtility.sendPacket(member, new SM_GROUP_MEMBER_INFO(group, player, groupEvent));
        }
        return true;
    }
}
