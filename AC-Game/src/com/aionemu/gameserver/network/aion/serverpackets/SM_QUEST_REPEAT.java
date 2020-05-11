package com.aionemu.gameserver.network.aion.serverpackets;

import javolution.util.FastList;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestState;

/**
 * @author Ever' new 4.5 packet
 */
public class SM_QUEST_REPEAT extends AionServerPacket {

    private FastList<QuestState> questState;

    public SM_QUEST_REPEAT(FastList<QuestState> questState) {
        this.questState = questState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(0x00); // unk

        for (QuestState qs : questState) {
            writeD(qs.getQuestId());
        }
        FastList.recycle(questState);
        questState = null;
    }
}
