package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.teleport.HotspotTeleportTemplate;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.teleport.TeleportService2;

/**
 * @author GiGatR00n v4.7.5.x
 */
public class CM_HOTSPOT_TELEPORT extends AionClientPacket {

    public int id;
    public int teleportGoal;
    public int kinah;
    public int reqLevel;

    /**
     * @param opcode
     * @param state
     * @param restStates
     */
    public CM_HOTSPOT_TELEPORT(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        id = readC();
        teleportGoal = readD();
        kinah = readD();
        reqLevel = readD();

    }

    /* (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
    	Player player = getConnection().getActivePlayer();
        if (player == null)
            return;
        if (player.getLifeStats().isAlreadyDead()) {
            return;
        }
        HotspotTeleportTemplate teleport = DataManager.HOTSPOT_TELEPORTER_DATA.getHotspotTemplate(teleportGoal);
        if (teleport != null) {
            TeleportService2.teleport(teleport, teleportGoal, player, kinah, reqLevel);
        } else {
            LoggerFactory.getLogger(CM_HOTSPOT_TELEPORT.class).warn("teleportation id " + teleportGoal + " was not found!");
        }
    }

}
