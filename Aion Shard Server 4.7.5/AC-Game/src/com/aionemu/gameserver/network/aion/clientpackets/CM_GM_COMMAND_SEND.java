package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gm.GmPanelCommands;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.network.aion.gmhandler.CmdAddSkill;
import com.aionemu.gameserver.network.aion.gmhandler.CmdAttrBonus;
import com.aionemu.gameserver.network.aion.gmhandler.CmdChangeClass;
import com.aionemu.gameserver.network.aion.gmhandler.CmdDeleteQuest;
import com.aionemu.gameserver.network.aion.gmhandler.CmdEndQuest;
import com.aionemu.gameserver.network.aion.gmhandler.CmdGiveTitle;
import com.aionemu.gameserver.network.aion.gmhandler.CmdInvisible;
import com.aionemu.gameserver.network.aion.gmhandler.CmdItemCoolTime;
import com.aionemu.gameserver.network.aion.gmhandler.CmdLevelUpDown;
import com.aionemu.gameserver.network.aion.gmhandler.CmdLevelUpDown.LevelUpDownState;
import com.aionemu.gameserver.network.aion.gmhandler.CmdResurrect;
import com.aionemu.gameserver.network.aion.gmhandler.CmdStartQuest;
import com.aionemu.gameserver.network.aion.gmhandler.CmdTeleportTo;
import com.aionemu.gameserver.network.aion.gmhandler.CmdTeleportToNamed;
import com.aionemu.gameserver.network.aion.gmhandler.CmdVisible;
import com.aionemu.gameserver.network.aion.gmhandler.CmdWish;
import com.aionemu.gameserver.network.aion.gmhandler.CmdWishId;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author Ever', Magenik
 */
public class CM_GM_COMMAND_SEND extends AionClientPacket {

    private String cmd = "";
    private String params = "";
    private Player admin;

    public CM_GM_COMMAND_SEND(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        admin = getConnection().getActivePlayer();
        String clientCmd = readS();

        int index = clientCmd.indexOf(" ");

        cmd = clientCmd;
        if (index >= 0) {
            cmd = clientCmd.substring(0, index).toUpperCase();
            params = clientCmd.substring(index + 1);
        }
    }

    @Override
    protected void runImpl() {
        if (admin == null) {
            return;
        }

        // check accesslevel - not needed but to be sure
        if (admin.getAccessLevel() < AdminConfig.GM_PANEL) {
            return;
        }

        switch (GmPanelCommands.getValue(cmd)) {
            case REMOVE_SKILL_DELAY_ALL:
                //new CmdRemoveSkillDelayAll(admin); // TODO
                break;
            case ITEMCOOLTIME:
                new CmdItemCoolTime(admin);
                break;
            case ATTRBONUS:
                new CmdAttrBonus(admin, params);
                break;
            case TELEPORTTO:
                new CmdTeleportTo(admin, params);
                break;
            case TELEPORT_TO_NAMED:
                new CmdTeleportToNamed(admin, params);
                break;
            case RESURRECT:
                new CmdResurrect(admin, "");
                break;
            case INVISIBLE:
                new CmdInvisible(admin, "");
                break;
            case VISIBLE:
                new CmdVisible(admin, "");
                break;
            case LEVELDOWN:
                new CmdLevelUpDown(admin, params, LevelUpDownState.DOWN);
                break;
            case LEVELUP:
                new CmdLevelUpDown(admin, params, LevelUpDownState.UP);
                break;
            case WISHID:
                new CmdWishId(admin, params);
                break;
            case DELETECQUEST:
                new CmdDeleteQuest(admin, params);
                break;
            case GIVETITLE:
                new CmdGiveTitle(admin, params);
                break;
            case DELETE_ITEMS:
                PacketSendUtility.sendMessage(admin, "Invalid command: " + cmd.toString());
                break;
            case CHANGECLASS:
                new CmdChangeClass(admin, params);
                break;
            case CLASSUP:
                new CmdChangeClass(admin, params);
                break;
            case WISH:
                new CmdWish(admin, params);
                break;
            case ADDQUEST:
                new CmdStartQuest(admin, params);
                break;
            case ENDQUEST:
                new CmdEndQuest(admin, params);
                break;
            case ADDSKILL:
                new CmdAddSkill(admin, params);
                break;
            case SETINVENTORYGROWTH:
            case SKILLPOINT:
            case COMBINESKILL:
            case DELETESKILL:
            case ENCHANT100:
            case SEARCH:
            case BOOKMARK_ADD:
                PacketSendUtility.sendMessage(admin, "Invalid command: " + cmd.toString());
                break;
            case FREEFLY:
                PacketSendUtility.sendMessage(admin, "Freefly On");
                break;
            default:
                PacketSendUtility.sendMessage(admin, "Invalid command: " + cmd.toString());
                break;
        }
    }

}