package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;

/**
 * This packet is informing client that some AionObject is no longer visible.
 *
 * @author -Nemesiss-
 */
public class SM_DELETE extends AionServerPacket {

    /**
     * Object that is no longer visible.
     */
    private final int objectId;
    private final int time;

    /**
     * Constructor.
     *
     * @param object
     */
    public SM_DELETE(AionObject object, int time) {
        this.objectId = object.getObjectId();
        this.time = time;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        int action = 0;
        if (action != 1) {
            writeD(objectId);
            writeC(time); // removal animation speed
        }
    }
}
