package ai.instance.shugoImperialTomb;

import ai.AggressiveNpcAI2;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

@AIName("strong_kobold_worker")
public class StrongKoboldWorkerAI2 extends AggressiveNpcAI2 {
	
	Npc tower;
	Npc towerCenter;
	
	@Override
	protected  void handleDeactivate() {
	}
	
	@Override
	public int modifyDamage(int damage) {
		return 500;
	}
	
	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		towerCenter = getPosition().getWorldMapInstance().getNpc(831130);
		tower = getPosition().getWorldMapInstance().getNpc(831250);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, towerCenter);
		getAggroList().addHate(towerCenter, 100000);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, tower);
		getAggroList().addHate(tower, 100000);
	}
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
	}
	
	@Override
	protected void handleActivate() {
		super.handleActivate();
		towerCenter = getPosition().getWorldMapInstance().getNpc(831130);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, towerCenter);
		tower = getPosition().getWorldMapInstance().getNpc(831250);
		AI2Actions.targetCreature(StrongKoboldWorkerAI2.this, tower);
	}
}
