package ai.instance.danuarReliquary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import ai.AggressiveNpcAI2;

import com.aionemu.commons.network.util.ThreadPoolManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.manager.EmoteManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FORCED_MOVE;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;

/**
 * @Author Sayem
 * @Reworked GiGatR00n v4.7.5.x
 */
@AIName("modorcursed")
public class ModorCursedAI2 extends AggressiveNpcAI2 {

	private AtomicBoolean isHome = new AtomicBoolean(true);
	protected List < Integer > percents = new ArrayList < Integer > ();
	private boolean canThink = true;
	private Future <? > skillTask;

	@Override
	public boolean canThink() {
		return canThink;
	}

	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		checkPercentage(getLifeStats().getHpPercentage());
		if (isHome.compareAndSet(true, false)) {
			sendMsg(1500740);
			startSkillTask();
		}
	}

	private void addPercent() {
		percents.clear();
		Collections.addAll(percents, new Integer[] {75, 50, 25});
	}

	@Override
	protected void handleSpawned() {
		super.handleSpawned();
		addPercent();
	}

	@Override
	protected void handleDespawned() {
		super.handleDespawned();
		percents.clear();
	}

	@Override
	protected void handleBackHome() {
		addPercent();
		super.handleBackHome();
		isHome.set(true);
	}

	private void sendMsg(int msg) {
		NpcShoutsService.getInstance().sendMsg(getOwner(), msg, getObjectId(), 0, 0);
	}

	private synchronized void checkPercentage(int hpPercentage) {
		for (Integer percent: percents) {
			if (hpPercentage <= percent) {
				switch (percent) {
					case 75:
						Teleport();
						break;
					case 50:
						Teleport();
						break;
					case 25:
						startInvincibleTask();
						break;
				}
				percents.remove(percent);
				break;
			}
		}
	}

	private void startSkillTask() {
		skillTask = ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				if (isAlreadyDead())
					cancelTask();
				else {
					chooseRandomEvent();
				}
			}
		}, 4000, 30000);
	}

	private void cancelTask() {
		if (skillTask != null && !skillTask.isCancelled()) {
			skillTask.cancel(true);
		}
	}

	private void chooseRandomEvent() {
		int rand = Rnd.get(0, 5);
		if (rand == 0)
			Anger();
		if (rand == 1)
			Sphere();
		if (rand == 2)
			Revenge();
		if (rand == 3)
			Wrath();
		if (rand == 4)
			Snow();
		else
			Storm();
	}

	private void Snow() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21367, 55, getOwner()).useNoAnimationSkill();
	}

	private void Wrath() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21229, 55, getOwner()).useNoAnimationSkill();
	}

	private void Revenge() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21175, 55, getOwner()).useNoAnimationSkill();
	}

	private void Sphere() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21174, 55, getOwner()).useNoAnimationSkill();
	}

	private void Anger() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21171, 55, getOwner()).useNoAnimationSkill();
	}

	private void Storm() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21173, 55, getOwner()).useNoAnimationSkill();
	}

	private void Teleport() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21165, 65, getOwner()).useNoAnimationSkill();
		sendMsg(1500741);
		ThreadPoolManager.getInstance().schedule(new Runnable() {@Override
			public void run() {
				World.getInstance().updatePosition(getOwner(), 255.52051f, 293.178f, 253.8094f, (byte) 30);
				PacketSendUtility.broadcastPacketAndReceive(getOwner(), new SM_FORCED_MOVE(getOwner(), getOwner()));
				spawn(284664, 266.879f, 247.496f, 242.03f, (byte) 45);
				spawn(284380, 246.349f, 247.481f, 242.01f, (byte) 15);
				spawn(284381, 257.405f, 243.156f, 241.91f, (byte) 31);
			}
		}, 2000);
	}

	private void startInvincibleTask() {
		AI2Actions.targetSelf(ModorCursedAI2.this);
		SkillEngine.getInstance().getSkill(getOwner(), 21165, 65, getOwner()).useNoAnimationSkill();
		sendMsg(1500742);
		canThink = false;
		EmoteManager.emoteStopAttacking(getOwner());
		ThreadPoolManager.getInstance().schedule(new Runnable() {@Override
			public void run() {
				SkillEngine.getInstance().getSkill(getOwner(), 21167, 65, getOwner()).useNoAnimationSkill();
				World.getInstance().updatePosition(getOwner(), 256.4457f, 257.6867f, 253.0f, (byte) 30);
				PacketSendUtility.broadcastPacketAndReceive(getOwner(), new SM_FORCED_MOVE(getOwner(), getOwner()));
				switch (Rnd.get(0, 2)) {
					case 0:
						spawn(284383, 284.504f, 262.939f, 248.7541f, (byte) 66);
						spawn(284384, 271.426f, 230.243f, 250.9022f, (byte) 38);
						spawn(284384, 240.130f, 235.219f, 251.1553f, (byte) 17);
						spawn(284384, 232.541f, 263.877f, 248.6566f, (byte) 115);
						spawn(284384, 255f, 293f, 253f, (byte) 88);
						break;
					case 1:
						spawn(284384, 232.426f, 263.818f, 248.6419f, (byte) 115);
						spawn(284384, 271.426f, 230.243f, 250.9022f, (byte) 38);
						spawn(284383, 240.130f, 235.219f, 251.1553f, (byte) 17);
						spawn(284384, 284.504f, 262.939f, 248.7541f, (byte) 66);
						spawn(284384, 255f, 293f, 253f, (byte) 88);
						break;
					case 2:
						spawn(284384, 232.426f, 263.818f, 248.6419f, (byte) 115);
						spawn(284384, 271.426f, 230.243f, 250.9022f, (byte) 38);
						spawn(284384, 240.130f, 235.219f, 251.1553f, (byte) 17);
						spawn(284383, 284.504f, 262.939f, 248.7541f, (byte) 66);
						spawn(284384, 255f, 293f, 253f, (byte) 88);
						break;
				}
			}
		}, 2000);
		
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				if (getOwner().getEffectController().hasAbnormalEffect(21167)) {
					getOwner().getEffectController().removeEffect(21167);
				}
			}
		}, 12000);
	}
}