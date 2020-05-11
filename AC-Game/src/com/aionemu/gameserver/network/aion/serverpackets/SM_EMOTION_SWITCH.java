package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


public class SM_EMOTION_SWITCH extends AionServerPacket {

    private Creature npc;
    private Player pl;
    private int state = 0;
    private int emotionType = 0;
    private boolean isPlayer = false;

    public SM_EMOTION_SWITCH(Npc npc, int state, EmotionType et) {
        this.npc = npc;
        this.state = state;
        this.emotionType = et.getTypeId();
        this.isPlayer = false;
    }
    
    public SM_EMOTION_SWITCH(Player pl, int state, EmotionType et) {
        this.pl = pl;
        this.state = state;
        this.emotionType = et.getTypeId();
        this.isPlayer = true;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
    	if (isPlayer) 
    		writeD(pl.getObjectId());
    	else
    		writeD(npc.getObjectId());
        writeC(state);
        writeD(emotionType);
        writeD(0);
    }
}
