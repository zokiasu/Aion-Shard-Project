package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.List;

import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.house.House;
import com.aionemu.gameserver.model.team.legion.LegionMemberEx;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.HousingService;

/**
 * @author Simple
 * @modified A7xatomic!
 */
public class SM_LEGION_MEMBERLIST extends AionServerPacket {

    private static final int OFFLINE = 0x00, ONLINE = 0x01;
    private boolean isFirst;
    private boolean result;
    private List<LegionMemberEx> legionMembers;

    /**
     * This constructor will handle legion member info when a List of members is
     * given
     *
     * @param ArrayList <LegionMemberEx> legionMembers
     */
    public SM_LEGION_MEMBERLIST(List<LegionMemberEx> legionMembers, boolean result, boolean isFirst) {
        this.legionMembers = legionMembers;
        this.result = result;
        this.isFirst = isFirst;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        int size = legionMembers.size();
        writeC(isFirst ? 1 : 0);
        writeH(result ? size : -size);
        for (LegionMemberEx legionMember : legionMembers) {
            writeD(legionMember.getObjectId());
            writeS(legionMember.getName());
            writeC(legionMember.getPlayerClass().getClassId());
            writeD(legionMember.getLevel());
            writeC(legionMember.getRank().getRankId());
            writeD(legionMember.getWorldId());
            writeC(legionMember.isOnline() ? ONLINE : OFFLINE);
            writeS(legionMember.getSelfIntro());
            writeS(legionMember.getNickname());
            writeD(legionMember.getLastOnline());

            int address = HousingService.getInstance().getPlayerAddress(legionMember.getObjectId());
            if (address > 0) {
                House house = HousingService.getInstance().getPlayerStudio(legionMember.getObjectId());
                if (house == null) {
                    house = HousingService.getInstance().getHouseByAddress(address);
                }
                writeD(address);
                writeD(house.getDoorState().getPacketValue());
            } else {
                writeD(0);
                writeD(0);
            }
            writeD(NetworkConfig.GAMESERVER_ID);
        }
    }
}
