package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.zone.ZoneClassName;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;

import java.util.List;

/**
 * @author KKnD , orz, avol
 */
public class CM_SUBZONE_CHANGE extends AionClientPacket {

    private int unk;
    private static Logger log = LoggerFactory.getLogger(CM_SUBZONE_CHANGE.class);

    /**
     * Constructs new instance of <tt>CM_SUBZONE_CHANGE </tt> packet
     *
     * @param opcode
     */
    public CM_SUBZONE_CHANGE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        unk = readC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void runImpl() {
        Player player = getConnection().getActivePlayer();
        player.revalidateZones();
        if (player.getAccessLevel() >= 5) {
            List<ZoneInstance> zones = player.getPosition().getMapRegion().getZones(player);
            int foundZones = 0;
            for (ZoneInstance zone: zones) {
                if (zone.getZoneTemplate().getZoneType() == ZoneClassName.DUMMY || zone.getZoneTemplate().getZoneType() == ZoneClassName.WEATHER)
                    continue;
                foundZones++;
                PacketSendUtility.sendMessage(player, "Passed zone: unk=" + unk + "; " + zone.getZoneTemplate().getZoneType() + " " + zone.getAreaTemplate().getZoneName().name());
            } if (foundZones == 0) {
                PacketSendUtility.sendMessage(player, "Passed unknown zone, unk=" + unk);
                return;
            }
        }
        log.info("Send CM_SUBZONE_CHANGE - unk = " + unk + "");
    }
}
