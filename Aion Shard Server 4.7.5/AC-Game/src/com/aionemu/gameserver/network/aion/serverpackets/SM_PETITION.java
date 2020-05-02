package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.PetitionService;

/**
 * @author zdead
 */
public class SM_PETITION extends AionServerPacket {

    private Petition petition;

    public SM_PETITION() {
        this.petition = null;
    }

    public SM_PETITION(Petition petition) {
        this.petition = petition;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        if (petition == null) {
            writeD(0x00);
            writeD(0x00);
            writeD(0x00);
            writeD(0x00);
            writeH(0x00);
            writeC(0x00);
        } else {
            writeC(0x01); // Action ID ?
            writeD(100); // unk (total online players ?)
            writeH(PetitionService.getInstance().getWaitingPlayers(con.getActivePlayer().getObjectId())); // Users
            // waiting for
            // Support
            writeS(Integer.toString(petition.getPetitionId())); // Ticket ID
            writeH(0x00);
            writeC(50); // Total Petitions
            writeC(49); // Remaining Petitions
            writeH(PetitionService.getInstance().calculateWaitTime(petition.getPlayerObjId())); // Estimated minutes
            // before GM reply
            writeD(0x00);
        }
    }
}
