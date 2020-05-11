package com.aionemu.gameserver.network.aion.serverpackets;

import org.apache.commons.lang.StringUtils;

import com.aionemu.gameserver.model.team2.TeamType;
import com.aionemu.gameserver.model.team2.common.legacy.LootGroupRules;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Lyahim, ATracer, xTz
 */
public class SM_GROUP_INFO extends AionServerPacket {

    private LootGroupRules lootRules;
    private int groupId;
    private int leaderId;
    private int groupmapid;
    private TeamType type;

    public SM_GROUP_INFO(PlayerGroup group) {
        groupId = group.getObjectId();
        leaderId = group.getLeader().getObjectId();
        groupmapid = group.getLeaderObject().getWorldId();
        lootRules = group.getLootGroupRules();
        type = group.getTeamType();
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(groupId);
        writeD(leaderId);
        writeD(groupmapid);
        writeD(lootRules.getLootRule().getId());
        writeD(lootRules.getMisc());
        writeD(lootRules.getCommonItemAbove());
        writeD(lootRules.getSuperiorItemAbove());
        writeD(lootRules.getHeroicItemAbove());
        writeD(lootRules.getFabledItemAbove());
        writeD(lootRules.getEthernalItemAbove());
        writeD(lootRules.getAutodistribution().getId());
        writeD(2);
        writeC(0);
        writeD(type.getType());
        writeD(type.getSubType());
        writeH(0); //unk
        writeH(0); // message id
        writeS(StringUtils.EMPTY); // name
    }
}
