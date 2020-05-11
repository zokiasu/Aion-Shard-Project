package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Collection;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.team2.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author ginho1
 * @edit Cheatkiller
 */
public class SM_CHAT_WINDOW extends AionServerPacket {

    private Player target;
    private boolean isGroup;

    public SM_CHAT_WINDOW(Player target, boolean isGroup) {
        this.target = target;
        this.isGroup = isGroup;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (target == null) {
            return;
        }

        if (isGroup) {
            if (target.isInGroup2()) {
                writeC(2); // group
                writeS(target.getName());

                PlayerGroup group = target.getPlayerGroup2();

                writeD(group.getTeamId());
                writeS(group.getLeader().getName());

                Collection<Player> members = group.getMembers();
                for (Player groupMember : members) {
                    writeC(groupMember.getLevel());
                }

                for (int i = group.size(); i < 6; i++) {
                    writeC(0);
                }

                for (Player groupMember : members) {
                    writeC(groupMember.getPlayerClass().getClassId());
                }

                for (int i = group.size(); i < 6; i++) {
                    writeC(0);
                }
            } else if (target.isInAlliance2()) {
                writeC(2); // alliance
                writeS(target.getName());

                PlayerAlliance alliance = target.getPlayerAlliance2();

                writeD(alliance.getTeamId());
                writeS(alliance.getLeader().getName());

                Collection<Player> members = alliance.getMembers();
                for (Player groupMember : members) {
                    writeC(groupMember.getLevel());
                }

                for (int i = alliance.size(); i < 24; i++) {
                    writeC(0);
                }

                for (Player groupMember : members) {
                    writeC(groupMember.getPlayerClass().getClassId());
                }

                for (int i = alliance.size(); i < 24; i++) {
                    writeC(0);
                }
            } else {
                writeC(4); // no group
                writeS(target.getName());
                writeD(0); // no group yet
                writeC(target.getPlayerClass().getClassId());
                writeC(target.getLevel());
                writeC(0); // unk
            }
        } else {
            writeC(1);
            writeS(target.getName());
            writeS(target.getLegion() != null ? target.getLegion().getLegionName() : "");
            writeC(target.getLevel());
            writeH(target.getPlayerClass().getClassId());
            writeS(target.getCommonData().getNote());
            writeD(1);
        }
    }
}
