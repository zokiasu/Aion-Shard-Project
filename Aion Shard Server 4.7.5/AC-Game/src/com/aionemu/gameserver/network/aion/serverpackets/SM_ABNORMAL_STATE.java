package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.Collection;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Avol, ATracer
 */
public class SM_ABNORMAL_STATE extends AionServerPacket {

    private Collection<Effect> effects;
    private int abnormals;

    public SM_ABNORMAL_STATE(Collection<Effect> effects, int abnormals) {
        this.effects = effects;
        this.abnormals = abnormals;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(abnormals);
        writeD(0);
        writeD(0); // unk 4.5
        writeC(0x7F);
        
        writeH(effects.size());

        for (Effect effect : effects) {
            writeD(effect.getEffectorId());
            writeH(effect.getSkillId());
            writeC(effect.getSkillLevel());
            writeC(effect.getTargetSlot());
            writeD(effect.getRemainingTime());
        }
    }
}
