package ai.instance.danuarReliquary;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;

/**
 * @Author Sayem
 */@AIName("modorclone")
public class ModorCloneAI2 extends AggressiveNpcAI2 {

	private AtomicBoolean isHome = new AtomicBoolean(true);
	private Future <? > skillTask;

	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		if (isHome.compareAndSet(true, false)) {
			startSkillTask();
		}
	}

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				sendMsg(1500746);
			}
		}, 5000);
	}

	private void startSkillTask() {
		skillTask = ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				if (isAlreadyDead())
					cancelSkillTask();
				else {
					VengefullOrbEvent();
				}
			}
		}, 30000);
	}

	private void cancelSkillTask() {
		if (skillTask != null && !skillTask.isCancelled()) {
			skillTask.cancel(true);
		}
	}

	private void VengefullOrbEvent() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21177, 65, target).useNoAnimationSkill();
		}
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				spawnSorcererQueenModor();
			}
		}, 11000);
	}

	private void sendMsg(int msg) {
		NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
	}

	private void spawnSorcererQueenModor() {
		if (!isAlreadyDead()) {
			spawn(284443, 256.4457f, 257.6867f, 242.30f, (byte) 90);
			getOwner().getController().onAttack(getOwner(), getLifeStats().getMaxHp() + 1, true);
		} 
		else
		return;
	}

	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		cancelSkillTask();
	}

	@Override
	protected void handleBackHome() {
		super.handleBackHome();
		cancelSkillTask();
		isHome.set(true);
	}

	@Override
	protected void handleDied() {
		super.handleDied();
		cancelSkillTask();
		AI2Actions.deleteOwner(ModorCloneAI2.this);
	}
}