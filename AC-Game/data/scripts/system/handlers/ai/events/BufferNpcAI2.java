package ai.events;

import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;

import java.util.Random;

/**
 * @author Master
 */
@AIName("bufnpc")
public class BufferNpcAI2 extends NpcAI2 {

	@Override
	protected void handleDialogStart(Player player) {
		PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(getObjectId(), 1011));
	}

	@Override
	public boolean onDialogSelect(Player player, int dialogId, int questId, int exp) {
		switch (dialogId) {
			case 10000: {
				int[] rr = { 2, 2, 1, 1, 1 };
				Random rand = new Random();

				int skillLevel = 1;
				getOwner().setTarget(player);

				int skillId1 = 20950; // Blessing of Rock I SKILLID:21282 21283
				int skillId2 = 20950; // Blessing of Health I

				if (rr[rand.nextInt(4)] == 2) {
					SkillEngine.getInstance().getSkill(getOwner(), skillId1, skillLevel, player).useWithoutPropSkill();
					SkillEngine.getInstance().getSkill(getOwner(), skillId2, skillLevel, player).useWithoutPropSkill();
				}
				else {
					if (rand.nextInt(1) == 0) {
						SkillEngine.getInstance().getSkill(getOwner(), skillId1, skillLevel, player).useWithoutPropSkill();
					}
					else {
						SkillEngine.getInstance().getSkill(getOwner(), skillId2, skillLevel, player).useWithoutPropSkill();
					}
				}
				break;
			}
		}
		return true;
	}
}
