package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.model.team.legion.LegionEmblem;
import com.aionemu.gameserver.model.team.legion.LegionEmblemType;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_SEND_EMBLEM;
import com.aionemu.gameserver.services.LegionService;

/**
 * @author Simple
 * @modified cura
 */
public class CM_LEGION_SEND_EMBLEM extends AionClientPacket {

    private int legionId;

    public CM_LEGION_SEND_EMBLEM(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        legionId = readD();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Legion legion = LegionService.getInstance().getLegion(legionId);

        if (legion != null) {
            LegionEmblem legionEmblem = legion.getLegionEmblem();
            if (legionEmblem.getEmblemType() == LegionEmblemType.DEFAULT) {
                sendPacket(new SM_LEGION_SEND_EMBLEM(legionId, legionEmblem.getEmblemId(), legionEmblem.getColor_r(),
                        legionEmblem.getColor_g(), legionEmblem.getColor_b(), legion.getLegionName(), legionEmblem.getEmblemType(), 0));
            } else {
                LegionService.getInstance().sendEmblemData(getConnection().getActivePlayer(), legionEmblem, legionId,
                        legion.getLegionName());
            }
        }
    }
}
