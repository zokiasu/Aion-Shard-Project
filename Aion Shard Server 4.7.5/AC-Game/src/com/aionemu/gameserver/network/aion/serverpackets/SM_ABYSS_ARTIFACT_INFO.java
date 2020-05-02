package com.aionemu.gameserver.network.aion.serverpackets;

import java.util.ArrayList;
import java.util.Collection;

import com.aionemu.gameserver.model.siege.ArtifactLocation;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;

public class SM_ABYSS_ARTIFACT_INFO extends AionServerPacket {

    private Collection<ArtifactLocation> locations;
    private boolean teleportStatus;

    public SM_ABYSS_ARTIFACT_INFO(Collection<ArtifactLocation> collection) {
        this.locations = collection;
    }

    public SM_ABYSS_ARTIFACT_INFO(int loc) {
        locations = new ArrayList<ArtifactLocation>();
        locations.add(SiegeService.getInstance().getArtifact(loc));
    }

    public SM_ABYSS_ARTIFACT_INFO(int locationId, boolean teleportStatus) {
        locations = new ArrayList<ArtifactLocation>();
        locations.add(SiegeService.getInstance().getArtifact(locationId));
        this.teleportStatus = teleportStatus;
    }

    @Override
    protected void writeImpl(AionConnection con) {
    	PacketLoggerService.getInstance().logPacketSM(this.getPacketName());
        writeH(locations.size());
        for (ArtifactLocation artifact : locations) {
            writeD(artifact.getLocationId() * 10 + 1);
            writeC(artifact.getStatus().getValue());
            writeD(0);
            writeC(teleportStatus ? 1 : 0);
        }
    }
}
