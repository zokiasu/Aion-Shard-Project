package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.team.legion.Legion;
import com.aionemu.gameserver.model.team.legion.LegionEmblem;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.LegionService;

/**
 * @author cura
 */
public class CM_LEGION_SEND_EMBLEM_INFO extends AionClientPacket {

    private int legionId;

    public CM_LEGION_SEND_EMBLEM_INFO(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        legionId = readD();
    }

    @Override
    protected void runImpl() {
        Legion legion = LegionService.getInstance().getLegion(legionId);
        if (legion != null) {
            LegionEmblem legionEmblem = legion.getLegionEmblem();
            if (legionEmblem.getCustomEmblemData() == null) {
                return;
            }
            LegionService.getInstance().sendEmblemData(getConnection().getActivePlayer(), legionEmblem, legionId,
                    legion.getLegionName());
        }
    }
}
