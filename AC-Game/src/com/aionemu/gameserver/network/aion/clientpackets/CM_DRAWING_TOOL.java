package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DRAWING_TOOL;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Maestros
 */
public class CM_DRAWING_TOOL extends AionClientPacket {

    private int action;
    private int unk2;
    @SuppressWarnings("unused")
    private int unk3;
    private int dataSize;
    private byte[] data;

    public CM_DRAWING_TOOL(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readC();
        switch (action) {
            case 1:
                dataSize = readD();
                break;
            default:
                unk3 = readC();
                unk2 = readC();
                dataSize = readD();
        }
        if ((dataSize > 0) && (dataSize <= 5086)) {
            data = readB(dataSize);
        }
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        if ((player == null) || (data == null)) {
            return;
        }
        if (action == 1) {
            PacketSendUtility.sendPacket(player, new SM_DRAWING_TOOL(data));
            return;
        }
        switch (action) {
            case 0:
                PacketSendUtility.broadcastPacketToGroup(player, new SM_DRAWING_TOOL(data, action, unk2), true);
                break;
            case 1:
                PacketSendUtility.broadcastPacketToAlliance(player, new SM_DRAWING_TOOL(data, action, unk2), true);
        }
    }
}