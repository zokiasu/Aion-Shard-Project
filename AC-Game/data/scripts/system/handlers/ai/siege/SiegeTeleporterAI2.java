package ai.siege;

import ai.GeneralNpcAI2;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.siege.SiegeNpc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_ARTIFACT_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORTRESS_INFO;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
@AIName("siege_teleporter")
public class SiegeTeleporterAI2 extends GeneralNpcAI2 {

    boolean isArtifact = false;

    @Override
    protected void handleDespawned() {
        canTeleport(false);
        super.handleDespawned();
    }

    @Override
    protected void handleSpawned() {
        canTeleport(true);
        super.handleSpawned();
    }

    private void canTeleport(final boolean status) {
        final int id = ((SiegeNpc) getOwner()).getSiegeId();
        try {
            SiegeService.getInstance().getFortress(id).setCanTeleport(status);
        } catch (@SuppressWarnings("unused") Exception e) {
            SiegeService.getInstance().getArtifact(id).setCanTeleport(status);
        }


        getPosition().getWorldMapInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player player) {
                if (isArtifact) {
                    PacketSendUtility.sendPacket(player, new SM_ABYSS_ARTIFACT_INFO(id, status));
                } else {
                    PacketSendUtility.sendPacket(player, new SM_FORTRESS_INFO(id, status));
                }
            }
        });
    }
}
