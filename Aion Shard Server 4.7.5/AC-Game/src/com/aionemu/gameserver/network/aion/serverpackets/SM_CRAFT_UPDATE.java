package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * @author Mr. Poke
 * @author Antraxx
 */
public class SM_CRAFT_UPDATE extends AionServerPacket {

    private int skillId;
    private int itemId;
    private int action;
    private int success;
    private int failure;
    private int nameId;
    private int timerDelay = 700;
    private int timerPeriod = 1200;

    /**
     * Constructor
     *
     * @param skillId
     * @param item
     * @param success
     * @param failure
     * @param action
     */
    public SM_CRAFT_UPDATE(int skillId, ItemTemplate item, int success, int failure, int action) {
        this.action = action;
        this.skillId = skillId;
        this.itemId = item.getTemplateId();
        this.success = success;
        this.failure = failure;
        this.nameId = item.getNameId();
        // aether morphing
        if (skillId == 40009) {
            this.timerPeriod = 3000;
        }
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(skillId);
        writeC(action);
        writeD(itemId);

        switch (action) {
            case 0: // init
            {
                writeD(success);
                writeD(failure);
                writeD(0);
                writeD(timerPeriod);
                writeD(1330048);
                writeH(0x24); // 0x24
                writeD(nameId);
                writeH(0);
                break;
            }
            case 1: // update
            case 2: // Bluecrit
            case 5: // sucess
            {
                writeD(success);
                writeD(failure);
                writeD(timerDelay);
                writeD(timerPeriod);
                writeD(0);
                writeH(0);
                break;
            }
            case 3: // purpleCrit or CraftProc
            {
                writeD(success);
                writeD(failure);
                writeD(0);
                writeD(timerPeriod);
                writeD(1330048); // message
                writeH(0x24);
                writeD(nameId);
                writeH(0);
                break;
            }
            case 4: // cancel
            {
                writeD(success);
                writeD(failure);
                writeD(0);
                writeD(0);
                writeD(1330051);
                writeH(0);
                break;
            }
            case 6: // failed
            {
                writeD(success);
                writeD(failure);
                writeD(timerDelay);
                writeD(timerPeriod);
                writeD(1330050);
                writeH(0x24);
                writeD(nameId);
                writeH(0);
                break;
            }
            case 7: {
                writeD(success);
                writeD(failure);
                writeD(0);
                writeD(1200);
                writeD(1330050);
                writeH(0x24);
                writeD(nameId);
                writeH(0);
                break;
            }
        }
    }
}
