package ai.siege;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Eloann
 */
@AIName("danaria_siege_gate")
public class DanariaSiegeGateAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        super.handleDialogStart(player);
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        switch (getNpcId()) {
            case 701783:
                despawnNpc(273286);
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_LDF5B_6021_OUT_DOOR_01_DESPAWN);
                break;
            case 701784:
                despawnNpc(273289);
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_LDF5B_6021_OUT_DOOR_02_DESPAWN);
                break;
            case 701785:
                despawnNpc(273285);
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_LDF5B_6021_OUT_DOOR_01_DESPAWN);
                break;
            case 701786:
                despawnNpc(273288);
                PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_LDF5B_6021_OUT_DOOR_02_DESPAWN);
                break;
        }
    }

    private void despawnNpc(final int npcId) {
        getKnownList().doOnAllNpcs(new Visitor<Npc>() {
            @Override
            public void visit(Npc npc) {
                if (npc.getNpcId() == npcId) {
                    npc.getController().onDelete();
                }
            }
        });
    }
}
