package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.List;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.stats.container.PlayerLifeStats;
import com.aionemu.gameserver.model.team2.common.legacy.GroupEvent;
import com.aionemu.gameserver.model.team2.group.PlayerGroup;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author Lyahim, ATracer
 */
public class SM_GROUP_MEMBER_INFO extends AionServerPacket {

    private int groupId;
    private Player player;
    private GroupEvent event;

    public SM_GROUP_MEMBER_INFO(PlayerGroup group, Player player, GroupEvent event) {
        this.groupId = group.getTeamId();
        this.player = player;
        this.event = event;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        PlayerLifeStats pls = player.getLifeStats();
        PlayerCommonData pcd = player.getCommonData();
        WorldPosition wp = pcd.getPosition();

        if (event == GroupEvent.ENTER && !player.isOnline()) {
            event = GroupEvent.ENTER_OFFLINE;
        }

        writeD(groupId);
        writeD(player.getObjectId());
        if (player.isOnline()) {
            writeD(pls.getMaxHp());
            writeD(pls.getCurrentHp());
            writeD(pls.getMaxMp());
            writeD(pls.getCurrentMp());
            writeD(pls.getMaxFp()); // maxflighttime
            writeD(pls.getCurrentFp()); // currentflighttime
        } else {
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
            writeD(0);
        }

        writeD(0);//unk 3.5
        writeD(wp.getMapId());
        writeD(wp.getMapId());
        writeF(wp.getX());
        writeF(wp.getY());
        writeF(wp.getZ());
        writeC(pcd.getPlayerClass().getClassId()); // class id
        writeC(pcd.getGender().getGenderId()); // gender id
        writeC(pcd.getLevel()); // level

        writeC(event.getId()); // something events
        writeH(player.isOnline() ? 1 : 0); // TODO channel?
        writeC(player.isMentor() ? 0x01 : 0x00);

        switch (event) {
            case MOVEMENT: // 0x01
            case DISCONNECTED: //0x03
                break;
            case LEAVE: // 0x00
                writeH(0x00); // unk
                writeC(0x00); // unk
                break;
            case ENTER_OFFLINE: // 0x07
            case JOIN: // 0x05
                writeS(pcd.getName()); // name
                break;
            default: // 0x13, 0x09
                writeS(pcd.getName()); // name
                writeD(0x00); // unk
                writeD(0x00); // unk
                writeC(0x7F); //slots
                List<Effect> abnormalEffects = player.getEffectController().getAbnormalEffects();
                writeH(abnormalEffects.size()); // Abnormal effects
                for (Effect effect : abnormalEffects) {
                    writeD(effect.getEffectorId()); // casterid
                    writeH(effect.getSkillId()); // spellid
                    writeC(effect.getSkillLevel()); // spell level
                    writeC(effect.getTargetSlot()); // unk ?
                    writeD(effect.getRemainingTime()); // estimatedtime
                }
                writeB(new byte[32]);
                break;
            case UPDATE:
                writeS(pcd.getName());
                writeD(0x00);
                writeD(0x00);
                writeC(0x7F);
                List<Effect> abnormalEffects1 = this.player.getEffectController().getAbnormalEffects();
                writeH(abnormalEffects1.size());
                for (Effect effect : abnormalEffects1) {
                    writeD(effect.getEffectorId());
                    writeH(effect.getSkillId());
                    writeC(effect.getSkillLevel());
                    writeC(effect.getTargetSlot());
                    writeD(effect.getRemainingTime());
                }
            writeB(new byte[32]);   
        }
    }
}
