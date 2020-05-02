package ai.instance.steelRose;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author zxl001
 */
@AIName("artillerist")
public class RoseArtilleristAI2 extends AggressiveNpcAI2 {
	
	private Npc cannon = null;
	private Future<?> task;
	private int attackCount = 0;
	private AtomicBoolean isStartedEvent = new AtomicBoolean(false);
	
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (isStartedEvent.compareAndSet(false, true)) {
			cannonAttackTask(creature);
		}
	}
	
	private void cannonAttackTask(final Creature creature) {
		
		task = ThreadPoolManager.getInstance().schedule(new Runnable() {
			
			@Override
			public void run() {
				if(++attackCount % 5 == 0)
					cannonAttack(creature);
				else 
					spawnevent(creature);
				cannonAttackTask(creature);
			}
		}, 30 * 1000);
	}
	
	private void spawnevent(Creature creature) {
			NpcShoutsService.getInstance().sendMsg(getOwner(), 1500880, getObjectId(), 26, 0);
			cannon = getOwner().getPosition().getWorldMapInstance().getNpc(231017);
			Npc bnb1 = (Npc) spawn(231018, 754.08154f, 510.0203f, 1012.389f, (byte) 65);
			bnb1.getAggroList().addHate(creature, 100000);
			Npc bnb2 = (Npc) spawn(231018, 753.6996f, 507.61172f, 1012.389f, (byte) 65);
			bnb2.getAggroList().addHate(creature, 100000);
	}
	
	private void cannonAttack(final Creature creature) {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500879, getObjectId(), 26, 0);
		cannon = getOwner().getPosition().getWorldMapInstance().getNpc(231016);
		SkillEngine.getInstance().getSkill(cannon, 20385, 55, cannon).useNoAnimationSkill();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			
			@Override
			public void run() {
				creature.getController().onAttack(cannon, 20385, 5000, true);
			}
		}, cannon.getCastingSkill().getSkillTemplate().getDuration());
	}
	
	@Override
	protected void handleDied() {
		task.cancel(true);
	}
}
