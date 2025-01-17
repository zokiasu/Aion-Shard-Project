package ai.instance.danuarReliquaryHero;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.NpcShoutsService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@AIName("final_graendal_hero")
public class FinalGraendalHero extends AggressiveNpcAI2 {

	private List<Integer> percents = new ArrayList<Integer>();

	@Override
	protected void handleSpawned() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500741, getObjectId(), 0, 1000);
		addPercent();
		super.handleSpawned();
	}
	@Override
	protected void handleAttack(Creature creature) {
		super.handleAttack(creature);
		checkPercentage(getLifeStats().getHpPercentage());
	}

	private void checkPercentage(int hpPercentage) {
		if (hpPercentage > 95 && percents.size() < 4) {
			addPercent();
		}
		for (Integer percent : percents) {
			if (hpPercentage <= percent) {
				switch (percent) {
					case 95:
						skill2();
						break;
					case 93:
						skill3();
						break;
					case 90:
						skill4();
						shout_spawn();
						spawn_support();
						break;
					case 85:
						shout1();
						skill3();
						break;
					case 83:
						skill5();
						break;
					case 80:
						skill4();
						shout_spawn();
						spawn_support();
						break;
					case 75:
						skill3();
						break;
					case 70:
						skill5();
						shout_spawn();
						spawn_support();
						break;
					case 65:
						shout_spawn();
						spawn_support();
						skill4();
						break;
					case 60:
						shout_spawn();
						spawn_support();
						skill3();
						break;
					case 55:
						shout_spawn();
						spawn_support2();
						break;
					case 51:
						shout_mass();
						skill_mass_active();
						break;
					case 50:
						skill_mass_destroy();
						skill3();
						break;
					case 49:
						skill4();
						break;
					case 45:
						skill5();
						spawn_support();
						break;
					case 40:
						shout2();
						skill1();
						skill_mass_active();
						break;
					case 39:
						skill_mass_destroy();
						skill5();
						break;
					case 35:
						shout_spawn();
						spawn_support2();
						skill1();
						break;
					case 30:
						skill2();
						break;
					case 15:
						shout_spawn();
						spawn_support3();
						skill5();
						break;
					case 10:
						shout_spawn();
						spawn_support3();
						skill3();
						break;
					case 5:
						shout_died();
						skill1();
						skill3();
						break;
				}
				percents.remove(percent);
				break;
			}
		}
	}

	private void addPercent() {
		percents.clear();
		Collections.addAll(percents, new Integer[]{95,93,90,85,83,80,75,70,65,60,55,51,50,49,45,40,39,35,30,15,10,5});
	}

	private void skill1() { //Grendel's Explosive Temper
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21172, 65, target).useNoAnimationSkill();
		}
	}

	private void skill2() { //Grendel's Explosive Wrath
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21171, 65, target).useNoAnimationSkill();
		}
	}

	private void skill3() { //Dash Attack (Fear)
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 2336, 65, target).useNoAnimationSkill();
		}
	}

	private void skill4() { //Grendal's Explosive Temper (Stun)
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21172, 65, target).useNoAnimationSkill();
		}
	}

	private void skill5() { //Dance Off I (Fear AoE Pingouin)
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 3246, 65, target).useNoAnimationSkill();
		}
	}

	private void skill_mass_active() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21741, 65, target).useNoAnimationSkill();
		}
	}
	
	private void skill_mass_destroy() {
		VisibleObject target = getTarget();
		if (target != null && target instanceof Player) {
			SkillEngine.getInstance().getSkill(getOwner(), 21740, 65, target).useNoAnimationSkill();
		}
	}

	private void shout1() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500740, getObjectId(), 0, 1000);
	}

	private void shout2() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500745, getObjectId(), 0, 1000);
	}

	private void shout_mass() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500748, getObjectId(), 0, 1000);
	}

	private void shout_spawn() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500750, getObjectId(), 0, 1000);
	}

	private void shout_died() {
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500751, getObjectId(), 0, 0);
		NpcShoutsService.getInstance().sendMsg(getOwner(), 1500752, getObjectId(), 0, 8000);
	}

	private void spawn_support() {
		spawn(284659, 264.5786f, 249.9355f, 241.8923f, (byte) 45);
		spawn(284660, 248.6734f, 265.8675f, 241.8990f, (byte) 106);
	}

	private void spawn_support2() {
		spawn(284661, 264.6672f, 265.9347f, 241.8658f, (byte) 90);
		spawn(284662, 248.3278f, 249.7112f, 241.8719f, (byte) 16);
		spawn(284663, 266.71f, 252.84f, 241.8658f, (byte) 45);
	}

	private void spawn_support3() {
		spawn(284660, 264.5786f, 249.9355f, 241.8923f, (byte) 45);
		spawn(284661, 264.6672f, 265.9347f, 241.8658f, (byte) 90);
		spawn(284662, 266.71f, 252.84f, 241.8658f, (byte) 45);
		spawn(284663, 248.3278f, 249.7112f, 241.8719f, (byte) 16);
		spawn(284664, 248.77f, 261.9f, 241.8719f, (byte) 0);
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
	@Override
	protected void handleBackHome() {
		addPercent();
		super.handleBackHome();
	}
}