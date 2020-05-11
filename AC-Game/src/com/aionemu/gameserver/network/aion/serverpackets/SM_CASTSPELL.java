package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * This packet show casting spell animation.
 *
 * @author alexa026
 * @author rhys2002
 */
public class SM_CASTSPELL extends AionServerPacket {

    private final int attackerObjectId;
    private final int spellId;
    private final int level;
    private final int targetType;
    private final int duration;
    private final boolean isCharge;
    private int targetObjectId;
    private float x;
    private float y;
    private float z;

    public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, int targetObjectId, int duration, boolean isCharge) {
        this.attackerObjectId = attackerObjectId;
        this.spellId = spellId;
        this.level = level;
        this.targetType = targetType;
        this.targetObjectId = targetObjectId;
        this.duration = duration;
        this.isCharge = isCharge;
    }

    public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, float x, float y, float z,
                        int duration) {
        this(attackerObjectId, spellId, level, targetType, 0, duration, false);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeD(attackerObjectId);
        writeH(spellId);
        writeC(level);

        writeC(targetType);
        switch (targetType) {
            case 0:
            case 3:
            case 4:
                writeD(targetObjectId);
                break;
            case 1:
                writeF(x);
                writeF(y);
                writeF(z);
                break;
            case 2:
                writeF(x);
                writeF(y);
                writeF(z);
                writeD(0);//unk1
                writeD(0);//unk2
                writeD(0);//unk3
                writeD(0);//unk4
                writeD(0);//unk5
                writeD(0);//unk6
                writeD(0);//unk7
                writeD(0);//unk8
        }

        writeH(duration);
        writeC(0x00);//unk
        writeF(0x01);//unk
        writeC(isCharge ? 0x01 : 0x00);//charge?
    }
}
