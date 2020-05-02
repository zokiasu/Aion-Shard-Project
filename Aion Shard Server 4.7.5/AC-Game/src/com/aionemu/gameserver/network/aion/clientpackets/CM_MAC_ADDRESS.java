package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.network.BannedMacManager;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

/**
 * In this packet client is sending Mac Address - haha.
 *
 * @author -Nemesiss-, KID
 */
public class CM_MAC_ADDRESS extends AionClientPacket {

	private static final Logger log = LoggerFactory.getLogger(CM_MAC_ADDRESS.class);
	
    /**
     * Mac Address send by client in the same format as:
     * ipconfig /all [ie:xx-xx-xx-xx-xx-xx]
     */
    private String ClientMacAddress;
    private String ClientHddSerial;
    private int ClientLocalIp;

    /**
     * Constructs new instance of <tt>CM_MAC_ADDRESS </tt> packet
     *
     * @param opcode
     */
    public CM_MAC_ADDRESS(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        readC();
        short counter = (short) readH();
        for (short i = 0; i < counter; i++) {
            readD();
        }
        ClientMacAddress = readS();
        ClientHddSerial = readS();
        ClientLocalIp = readD();
    }

    @Override
    protected void runImpl() {
        if (BannedMacManager.getInstance().isBanned(ClientMacAddress)) {
            //TODO some information packets
            this.getConnection().closeNow();
            log.info("[MAC_AUDIT] " + ClientMacAddress + " (" + this.getConnection().getIP() + ") was kicked due to Mac Ban");
        } else {
            this.getConnection().setMacAddress(ClientMacAddress);
        }
    }
}
