package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.Petition;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PETITION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.PetitionService;

/**
 * @author zdead
 */
public class CM_PETITION extends AionClientPacket {

    private int action;
    private String title = "";
    private String text = "";
    private String additionalData = "";

    public CM_PETITION(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        action = readH();
        if (action == 2) {
            readD();
        } else {
            String data = readS();
            String[] dataArr = data.split("/", 3);
            title = dataArr[0];
            text = dataArr[1];
            additionalData = dataArr[2];
        }
    }

    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        int playerObjId = player.getObjectId();
        if (action == 2) {
            if (PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {
                int petitionId = PetitionService.getInstance().getPetition(playerObjId).getPetitionId();
                PetitionService.getInstance().deletePetition(playerObjId);
                sendPacket(new SM_SYSTEM_MESSAGE(1300552, petitionId));
                sendPacket(new SM_SYSTEM_MESSAGE(1300553, 49));
                return;
            }

        }

        if (!PetitionService.getInstance().hasRegisteredPetition(playerObjId)) {
            Petition petition = PetitionService.getInstance().registerPetition(player, action,
                    title, text, additionalData);
            sendPacket(new SM_PETITION(petition));
        }
    }
}
