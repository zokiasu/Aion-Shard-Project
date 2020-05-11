package ai.instance.kamarBattlefield;

import ai.ActionItemNpcAI2;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;


@AIName("kamarsiegeweapon")
public class KamarSiegeWeaponAI2 extends ActionItemNpcAI2 {

    @Override
    protected void handleDialogStart(Player player) {
        super.handleDialogStart(player);
    }

    @Override
    protected void handleUseItemFinish(Player player) {
        switch (getNpcId()) {
            case 701807:
                SkillEngine.getInstance().getSkill(player, 21403, 1, player).useNoAnimationSkill();
                if (player.getInventory().getItemCountByItemId(164000262) < 1) {
                    return;
                }
                break;
            case 701808:
                SkillEngine.getInstance().getSkill(player, 21404, 1, player).useNoAnimationSkill();
                if (player.getInventory().getItemCountByItemId(164000262) < 1) {
                    return;
                }
                break;
            case 701902:
            case 701806:
                World.getInstance().updatePosition(player, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) getOwner().getHeading());
                PacketSendUtility.broadcastPacketAndReceive(player, new SM_FORCED_MOVE(player, player));
                SkillEngine.getInstance().getSkill(player, 21409, 1, player).useNoAnimationSkill();
                break;
        }
        AI2Actions.dieSilently(this, player);
        AI2Actions.deleteOwner(this);
    }
}
