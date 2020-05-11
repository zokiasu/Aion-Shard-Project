package com.aionemu.gameserver.network.aion.clientpackets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FIND_GROUP;
import com.aionemu.gameserver.services.FindGroupService;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author cura, MrPoke
 * @modified teenwolf
 */
public class CM_FIND_GROUP extends AionClientPacket {

	/**
	 * Logger
	 */
	private static final Logger log = LoggerFactory.getLogger(CM_FIND_GROUP.class);
	private int action;
	private int playerObjId;
	private String message;
	private int groupType;
	@SuppressWarnings("unused")
	private int classId;
	@SuppressWarnings("unused")
	private int level;
	private int unk;
	private int instanceId;
	private int minMembers;
	@SuppressWarnings("unused")
	private int groupId;

	public CM_FIND_GROUP(int opcode, State state, State... restStates) {
		super(opcode, state, restStates);
	}

	@Override
	protected void readImpl() {
		PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
		action = readC();

		switch (action) {
			case 0x00: // recruit list
				break;
			case 0x01: // offer delete
				playerObjId = readD();
				readH(); //serverId
				readC(); // 0
				readC(); // someCode (10) for Group owner ?
				break;
			case 0x02: // send offer
				playerObjId = readD();
				message = readS();
				groupType = readC();
				break;
			case 0x03: // recruit update
				playerObjId = readD();
				readH(); //serverId
				readC(); // 0
				readC(); // someCode (10) for Group owner ?
				message = readS();
				groupType = readC();
				break;
			case 0x04: // apply list
				break;
			case 0x05: // post delete
				playerObjId = readD();
				break;
			case 0x06: // apply create
				playerObjId = readD();
				message = readS();
				groupType = readC();
				classId = readC();
				level = readC();
				break;
			case 0x07: // apply update
				playerObjId = readD();
				message = readS();
				groupType = readC();
				classId = readC();
				level = readC();
				break;
			case 0x08: // register InstanceGroup
				instanceId = readD(); // correct = InstanceId
				unk = readC(); // Todo = 0
				message = readS(); // correct = Message
				minMembers = readC(); // Todo = Groupsize?
				unk = readD(); // Todo = 0
				unk = readD(); // Todo = 0
				break;
			case 0x09: // New
				unk = readD(); // correct = ??
				instanceId = readD(); // correct = InstanceId
				break;
			case 0x14: // TODO 5.6
				groupId = readD();
				instanceId = readD();
				unk = readC();
				break;
			case 0x0A: // New 4.0 Group Recruitment
				break;
			case 0x0D: // New
				break;
			default:
				log.error("Unknown find group packet? 0x" + Integer.toHexString(action).toUpperCase());
				break;
		}
	}

	@Override
	protected void runImpl() {
		final Player player = this.getConnection().getActivePlayer();
		switch (action) {
			case 0x00:
			case 0x04:
				FindGroupService.getInstance().sendFindGroups(player, action);
				break;
			case 0x01:
			case 0x05:
				FindGroupService.getInstance().removeFindGroup(player.getRace(), action - 1, playerObjId);
				break;
			case 0x02:
			case 0x06:
				FindGroupService.getInstance().addFindGroupList(player, action, message, groupType);
				break;
			case 0x03:
			case 0x07:
				FindGroupService.getInstance().updateFindGroupList(player, message, action, groupType, playerObjId);
				break;
			case 0x08:
				FindGroupService.getInstance().registerInstanceGroup(player, 0x0E, instanceId, message, minMembers, groupType);
				break;
			case 0x14:
				// TODO 5.6
				break;
			case 0x0A: // search
				FindGroupService.getInstance().sendFindGroups(player, action);
				break;
			case 0x0D:
				FindGroupService.getInstance().sendFindGroups(player, action);
				break;
			default:
				PacketSendUtility.sendPacket(player, new SM_FIND_GROUP(action, playerObjId, unk));
				break;
		}
	}
}
