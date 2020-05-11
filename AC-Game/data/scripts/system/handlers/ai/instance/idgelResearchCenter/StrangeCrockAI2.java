package ai.instance.idgelResearchCenter;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.player.Player;

import ai.ActionItemNpcAI2;

/**
 * @author zxl001
 */
@AIName("strangecrock")
public class StrangeCrockAI2 extends ActionItemNpcAI2 {

	@Override
	protected void handleDialogStart(Player player) {
		handleUseItemStart(player);
	}

	@Override
	protected void handleUseItemStart(final Player player) {
		super.handleUseItemStart(player);
	}

	@Override
	protected void handleUseItemFinish(Player player) {
		int num = Rnd.get(3, 5);
		for (int i = 0; i < num; i++) {
			float direction = Rnd.get(0, 199) / 100f;
			int r = Rnd.get(3, 6);
			float x = (float) (Math.cos(Math.PI * direction) * r);
			float y = (float) (Math.sin(Math.PI * direction) * r);
			spawn(230118, player.getX() + x, player.getY() + y, player.getZ(), (byte)(127 - player.getHeading()));
		}
		getOwner().getController().delete();
	}
}
