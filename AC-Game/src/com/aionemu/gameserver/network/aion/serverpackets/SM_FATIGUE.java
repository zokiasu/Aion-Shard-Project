package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;


public class SM_FATIGUE extends AionServerPacket {

    private int effectEnabled;
    private int isFull;
    private int fatigueRecover;
    private int iconSet;

    /**
	 * @param fatigueRecover  
	 */
    public SM_FATIGUE(int effectEnabled, int isFull, int fatigueRecover, int iconSet) {
        this.effectEnabled = effectEnabled;
        this.isFull = isFull;
        this.fatigueRecover = 0;//fatigueRecover
        this.iconSet = iconSet;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(0);//unk
        writeC(0);//unk
        writeC(effectEnabled);// 1=effect enabled | 0=effect disabled //VERIFIED!
        writeH(iconSet);//icon
        writeC(isFull);//isFull 1=100% | 0=0% //VERIFIED!
        writeC(fatigueRecover);//fatigue recovery //VERIFIED! //seems it isnt implemented
    }
}
