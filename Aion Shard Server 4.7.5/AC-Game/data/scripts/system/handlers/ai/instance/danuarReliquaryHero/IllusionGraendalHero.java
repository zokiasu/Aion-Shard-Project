package ai.instance.danuarReliquaryHero;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.services.NpcShoutsService;
import java.util.ArrayList;
import java.util.List;

@AIName("illusion_graendal_hero")
public class IllusionGraendalHero extends AggressiveNpcAI2 {

	private List<Integer> percents = new ArrayList<Integer>();

	@Override
	protected void handleSpawned() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 348724, getObjectId(), 0, 3000);
		super.handleSpawned();
	}

	@Override
	protected void handleDespawned() {
		percents.clear();
		super.handleDespawned();
	}

	@Override
	protected void handleDied() {
		percents.clear();
		super.handleDied();
	}
}