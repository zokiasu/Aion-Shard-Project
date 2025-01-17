package instance;

import com.aionemu.gameserver.instance.handlers.GeneralInstanceHandler;
import com.aionemu.gameserver.instance.handlers.InstanceID;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.Storage;
import com.aionemu.gameserver.network.aion.serverpackets.*;
import com.aionemu.gameserver.services.player.PlayerReviveService;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import com.aionemu.gameserver.world.zone.ZoneName;
import com.aionemu.gameserver.model.gameobjects.StaticDoor;

import java.util.*;

/**
 * @author xTz, Gigi
 */
@InstanceID(300230000)
public class KromedesTrialInstance extends GeneralInstanceHandler {

	private int skillId;
	private List<Integer> movies = new ArrayList<Integer>();
	private boolean isSpawned = false;
	private Map<Integer, StaticDoor> doors;

	@Override
	public void onInstanceCreate(WorldMapInstance instance) {
		super.onInstanceCreate(instance);
		doors = instance.getDoors();
	}

	@Override
	public void onEnterInstance(Player player) {
		openAllDoors();
		/*if (movies.contains(453)) {
			return;
		}
		skillId = player.getRace() == Race.ASMODIANS ? 19270 : 19220;
		//sendMovie(player, 453);*/
	}

	@Override
    public void onLeaveInstance(Player player) {
      /*player.getEffectController().removeEffect(skillId);
      removeItems(player);*/
    }
   
    private void removeItems(Player player) {
		/*Storage storage = player.getInventory();
		storage.decreaseByItemId(185000109, 1);
		storage.decreaseByItemId(185000098, 1);
		storage.decreaseByItemId(185000099, 1);
		storage.decreaseByItemId(185000100, 1);
		storage.decreaseByItemId(185000101, 1);
		storage.decreaseByItemId(185000102, 1);*/
    }

	@Override
	public void onPlayerLogOut(Player player) {
		/*player.setTransformed(false);
		PacketSendUtility.broadcastPacketAndReceive(player, new SM_TRANSFORM(player, false));*/
	}

	@Override
	public void onEnterZone(Player player, ZoneInstance zone) {
		/*if (zone.getAreaTemplate().getZoneName() == ZoneName.get("MANOR_ENTRANCE_300230000")) {
			//sendMovie(player, 462);
		} else if (zone.getAreaTemplate().getZoneName() == ZoneName.get("KALIGA_TREASURY_300230000")) {
            {
				if (!isSpawned) {
					isSpawned = true;
					Npc npc1 = getNpc(217002);
					Npc npc2 = getNpc(217000);
					Npc npc3 = getNpc(216982);
					if (isDead(npc1) && isDead(npc2) && isDead(npc3)) {
						spawn(217005, 669.214f, 774.387f, 216.88f, (byte) 60);
						spawn(217001, 663.8805f, 779.1967f, 216.26213f, (byte) 60);
						spawn(217003, 663.0468f, 774.6116f, 216.26215f, (byte) 60);
						spawn(217004, 663.0468f, 770.03815f, 216.26212f, (byte) 60);
					} else {
						spawn(217006, 669.214f, 774.387f, 216.88f, (byte) 60);
					}
				}
			}
		}*/
	}

	protected void openAllDoors() {
		openDoor(2);
		openDoor(81);
		openDoor(259);
		openDoor(325);
		openDoor(326);
		openDoor(360);
	}

	protected void openDoor(int doorId) {
		StaticDoor door = doors.get(doorId);
		if (door != null) {
			door.setOpen(true);
		}
	}

	/*@Override
	public boolean onDie(final Player player, Creature lastAttacker) {
		PacketSendUtility.broadcastPacket(player, new SM_EMOTION(player, EmotionType.DIE, 0, player.equals(lastAttacker) ? 0 : lastAttacker.getObjectId()), true);
		PacketSendUtility.sendPacket(player, new SM_DIE(player.haveSelfRezEffect(), player.haveSelfRezItem(), 0, 8));
		return true;
	}

	@Override
	public boolean onReviveEvent(Player player) {
		WorldMap map = World.getInstance().getWorldMap(player.getWorldId());
		if (map == null) {
			PlayerReviveService.bindRevive(player);
			return true;
		}
		PlayerReviveService.revive(player, 25, 25, true, 0);
		PacketSendUtility.sendPacket(player, SM_SYSTEM_MESSAGE.STR_REBIRTH_MASSAGE_ME);
		player.getGameStats().updateStatsAndSpeedVisually();
		PacketSendUtility.sendPacket(player, new SM_PLAYER_INFO(player, false));
		PacketSendUtility.sendPacket(player, new SM_MOTION(player.getObjectId(), player.getMotions().getActiveMotions()));
		TeleportService2.teleportTo(player, player.getWorldId(), 248, 244, 189);
		SkillEngine.getInstance().applyEffectDirectly(skillId, player, player, 0);
		player.unsetResPosState();
		return true;
	}*/
}
