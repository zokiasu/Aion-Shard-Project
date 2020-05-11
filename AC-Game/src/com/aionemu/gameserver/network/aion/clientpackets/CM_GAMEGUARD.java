package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.antihack.AntiHackService;


public class CM_GAMEGUARD extends AionClientPacket {

    private int size;
    @SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CM_GAMEGUARD.class);

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_GAMEGUARD(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        size = readD();
        readB(size);

    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
    	//log.info("AION Bin size from client: " + size);
        Player player = getConnection().getActivePlayer();
        AntiHackService.checkAionBin(size, player);
    }

}
