package instance;

import java.util.*;
import java.util.concurrent.Future;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.commons.network.util.ThreadPoolManager;

import com.aionemu.gameserver.instance.handlers.*;
import com.aionemu.gameserver.controllers.effect.*;
import com.aionemu.gameserver.model.*;
import com.aionemu.gameserver.model.drop.*;
import com.aionemu.gameserver.model.gameobjects.*;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.services.drop.DropRegistrationService;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.utils.*;
import com.aionemu.gameserver.world.zone.*;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.knownlist.Visitor;

/****/
/** Author Rinzler (Encom)
/****/

@InstanceID(400030000)
public class TransidiumAnnexInstance extends GeneralInstanceHandler {
    private long startTime;
	private int hangarBarricade;
	private Future<?> instanceTimer;
	private int transidiumAnnexBase;
	private Map<Integer, StaticDoor> doors;
	protected boolean isInstanceDestroyed = false;
	private int numberBossDie = 0;
	
	public void onDropRegistered(Npc npc) {
		Set<DropItem> dropItems = DropRegistrationService.getInstance().getCurrentDropMap().get(npc.getObjectId());
		int npcId = npc.getNpcId();
		int index = dropItems.size() + 1;
		switch (npcId) {
			case 234564:
			case 234570:
			case 234571:
			case 234572:
				for (Player player: instance.getPlayersInside()) {
					if (player.isOnline()) {
						dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 166030005, 1)); //Tempering Stone.
						dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 166020000, 1)); //Omega Enchantment Stone.
						dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 188053618, 1)); //Honorable Elim's Idian Bundle.
					}
				}
				break;
			case 277224: //Ahserion.
				for (Player player: instance.getPlayersInside()) {
				    if (player.isOnline()) {
					    dropItems.add(DropRegistrationService.getInstance().regDropItem(index++, player.getObjectId(), npcId, 188053117, 1)); //Ahserion's Glory Reward Box.
					}
				}
			break;
		}
	}
	
	@Override
	public void onInstanceCreate(WorldMapInstance instance) {
		super.onInstanceCreate(instance);
		doors = instance.getDoors();
		Npc npc = instance.getNpc(277224); //Ahserion.
		if (npc != null) {
			SkillEngine.getInstance().getSkill(npc, 21571, 60, npc).useNoAnimationSkill(); //Ereshkigal's Reign.
		}
	}
	
	@Override
	public void onEnterInstance(final Player player) {
		super.onInstanceCreate(instance);
		if (instanceTimer == null) {
			startTime = System.currentTimeMillis();
			//Loading the Advance Corridor Shield... Please wait.
			sendMsgByRace(1402252, Race.PC_ALL, 10000);
			//The Advance Corridor Shield has been activated.
			//If the protection device is destroyed, the corridor will disappear and you will return to the fortress.
			sendMsgByRace(1402637, Race.PC_ALL, 20000);
			//The member recruitment window has passed. You cannot recruit any more members.
			sendMsgByRace(1401181, Race.PC_ALL, 50000);
			//The effect of the Transidium Annex has weakened the Hangar Barricade.
			sendMsgByRace(1402638, Race.PC_ALL, 1200000);
			instanceTimer = ThreadPoolManager.getInstance().schedule(new Runnable() {
				@Override
				public void run() {
					openFirstDoors();
					sendMsg(1401838);
					sendQuestionWindow();
				}
			}, 60000);
		}
	}
	
	@Override
    public void onEnterZone(Player player, ZoneInstance zone) {
		if (zone.getAreaTemplate().getZoneName() == ZoneName.get("CHARIOT_HANGAR_1_400030000")) {
			transidiumAnnexBase = 1;
		} else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("CHARIOT_HANGAR_2_400030000")) {
			transidiumAnnexBase = 2;
		} else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("IGNUS_ENGINE_HANGAR_1_400030000")) {
            transidiumAnnexBase = 3;
	    } else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("IGNUS_ENGINE_HANGAR_2_400030000")) {
			transidiumAnnexBase = 4;
		}
    }
	
	@Override
    public void onDie(Npc npc) {
		switch (npc.getObjectTemplate().getTemplateId()) {
			case 234564:
			case 234570:
			case 234571:
			case 234572:
				numberBossDie++;
				if(numberBossDie >= 4){
					spawn(277224, 509.8f, 512.7f, 675.0f, (byte) 60);
				}
            	break;
			case 277224: //Ahserion.
            	break;
		}
	}
	
	private void sendQuestionWindow() {
		instance.doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				PacketSendUtility.sendPacket(player, new SM_QUESTION_WINDOW(SM_QUESTION_WINDOW.STR_MSG_SVS_DIRECT_PORTAL_OPEN_NOTICE, 0, 0));
			}
		});
	}
	
	protected void openDoor(int doorId) {
        StaticDoor door = doors.get(doorId);
        if (door != null) {
            door.setOpen(true);
        }
    }
	
	protected void openFirstDoors() {
	    openDoor(176);
		openDoor(177);
		openDoor(178);
		openDoor(179);
    }
	
	@Override
	public void handleUseItemFinish(Player player, Npc npc) {
		switch (npc.getNpcId()) {
			case 277225: //Belus Camp Defense Cannon.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21731, 60, player).useNoAnimationSkill();
			break;
			case 277226: //Aspida Camp Defense Cannon.
			    despawnNpc(npc);			    
				SkillEngine.getInstance().getSkill(npc, 21728, 60, player).useNoAnimationSkill();
			break;
			case 277227: //Atanatos Camp Defense Cannon.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21729, 60, player).useNoAnimationSkill();
			break;
			case 277228: //Disilon Camp Defense Cannon.
				despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21730, 60, player).useNoAnimationSkill();
			break;
			//**/////////**//
			//**/////////**//
			case 297331: //Belus Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21582, 60, player).useNoAnimationSkill(); //Board The Chariot.
			break;
			case 297332: //Aspida Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21589, 60, player).useNoAnimationSkill(); //Board The Chariot.
			break;
			case 297333: //Atanatos Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21590, 60, player).useNoAnimationSkill(); //Board The Chariot.
			break;
			case 297334: //Disilon Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21591, 60, player).useNoAnimationSkill(); //Board The Chariot.
			break;
			//**/////////**//
			//**/////////**//
			case 297472: //Belus Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21579, 60, player).useNoAnimationSkill(); //Board The Ignus Engine.
			break;
			case 297473: //Aspida Chariot.
                despawnNpc(npc);			
				SkillEngine.getInstance().getSkill(npc, 21586, 60, player).useNoAnimationSkill(); //Board The Ignus Engine.
			break;
			case 297474: //Atanatos Chariot.
			    despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21587, 60, player).useNoAnimationSkill(); //Board The Ignus Engine.
			break;
			case 297475: //Disilon Chariot.
				despawnNpc(npc);
				SkillEngine.getInstance().getSkill(npc, 21588, 60, player).useNoAnimationSkill(); //Board The Ignus Engine.
			break;
		}
	}
	
	private void removeEffects(Player player) {
		PlayerEffectController effectController = player.getEffectController();
		effectController.removeEffect(21728);
		effectController.removeEffect(21729);
		effectController.removeEffect(21730);
		effectController.removeEffect(21731);
		effectController.removeEffect(21579);
		effectController.removeEffect(21582);
		effectController.removeEffect(21586);
		effectController.removeEffect(21587);
		effectController.removeEffect(21588);
		effectController.removeEffect(21589);
		effectController.removeEffect(21590);
		effectController.removeEffect(21591);
	}
	
	protected void despawnNpc(Npc npc) {
        if (npc != null) {
            npc.getController().onDelete();
        }
    }
	
	private void deleteNpc(int npcId) {
		if (getNpc(npcId) != null) {
			getNpc(npcId).getController().onDelete();
		}
	}
	
	@Override
	public void onPlayerLogOut(Player player) {
		removeEffects(player);
	}
	
	@Override
	public void onLeaveInstance(Player player) {
		removeEffects(player);
	}
	
	@Override
	public void onInstanceDestroy() {
		isInstanceDestroyed = true;
		doors.clear();
	}
	
	private void sendMsg(final String str) {
		instance.doOnAllPlayers(new Visitor<Player>() {
			@Override
			public void visit(Player player) {
				PacketSendUtility.sendMessage(player, str);
			}
		});
	}
	
	protected void sendMsgByRace(final int msg, final Race race, int time) {
		ThreadPoolManager.getInstance().schedule(new Runnable() {
			@Override
			public void run() {
				instance.doOnAllPlayers(new Visitor<Player>() {
					@Override
					public void visit(Player player) {
						if (player.getRace().equals(race) || race.equals(Race.PC_ALL)) {
							PacketSendUtility.sendPacket(player, new SM_SYSTEM_MESSAGE(msg));
						}
					}
				});
			}
		}, time);
	}
	
	@Override
	public boolean onDie(final Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}
}