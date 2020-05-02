package com.aionemu.gameserver.eventEngine.battleground.controllers;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.eventEngine.battleground.model.gameobjects.BattleGroundFlag;
import com.aionemu.gameserver.eventEngine.battleground.model.gameobjects.BattleGroundFlag.BattleGroundFlagState;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.ObjectLocation;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.CTFBattleGround;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Eloann
 */
public class BattleGroundFlagController extends NpcController {

    public boolean dropped = true;
    private float lastX, lastY, lastZ = 0;
    private ObjectLocation loc;
    protected BattleGroundTemplate template;

    private void onFlagCaptured(Player carrier) {
        int tplId = carrier.getBattleGround().getTplId();
        template = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);

        carrier.getBattleGround().broadcastToBattleGround(carrier.getCommonData().getName() + (carrier.getLegion() != null ? " of " + carrier.getLegion().getLegionName() : "") + " has captured the "
                + (getOwner().getRace() == Race.ELYOS ? "Elyos" : "Asmodian") + " flag !", null);

        carrier.getController().getTask(TaskId.BATTLEGROUND_CARRY_FLAG).cancel(true);
        carrier.getController().addTask(TaskId.BATTLEGROUND_CARRY_FLAG, null);

        carrier.getBattleGround().increasePoints(carrier, template.getRules().getFlagCap());

        carrier.battlegroundFlag.state = BattleGroundFlagState.AT_BASE;

        World.getInstance().updatePosition(getOwner(), getOwner().getSpawn().getX(), getOwner().getSpawn().getY(), getOwner().getSpawn().getZ(), getOwner().getSpawn().getHeading(), true);

        getOwner().setFlagHolder(null);
        dropped = true;
        carrier.battlegroundFlag = null;

        carrier.getBattleGround().getInstance().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player object) {
                PacketSendUtility.sendPacket(object, new SM_NPC_INFO(getOwner(), object));
            }
        });

        ((CTFBattleGround) carrier.getBattleGround()).score(carrier.getCommonData().getRace());

        carrier.getBattleGround()
                .broadcastToBattleGround(
                        "The score is now : Ely. " + ((CTFBattleGround) carrier.getBattleGround()).getElyosFlagCount() + " - " + ((CTFBattleGround) carrier.getBattleGround()).getAsmosFlagCount() + " Asmo.",
                        null);

    }

    @Override
    public void onDialogRequest(final Player player) {
        int tplId = player.getBattleGround().getTplId();
        template = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);

        if (getOwner() == null) {
            return;
        }
        if (loc == null) {
            loc = template.getFlagLocation();
        }
        if (player.getBattleGround() != null && player.getBattleGround().running) {
            if (dropped && player.getController().getTask(TaskId.BATTLEGROUND_CARRY_FLAG) == null && player.getCommonData().getRace() != getOwner().getRace() && getOwner().getFlagHolder() == null) {
                getOwner().setFlagHolder(player);
                dropped = false;
                player.battlegroundFlag = getOwner();

                if (getOwner().state == BattleGroundFlagState.AT_BASE) {
                    getOwner().state = BattleGroundFlagState.ON_FIELD;

                    // points for capturing flag in other base
                    player.getBattleGround().increasePoints(player, template.getRules().getFlagBase());
                } else {
                    player.getBattleGround().increasePoints(player, template.getRules().getFlagGround());
                }

                player.getBattleGround().broadcastToBattleGround(
                        player.getCommonData().getName() + (player.getLegion() != null ? " of " + player.getLegion().getLegionName() : "") + " got the "
                        + (getOwner().getRace() == Race.ELYOS ? "Elyos" : "Asmodian") + " flag !", null);

                player.getController().addTask(TaskId.BATTLEGROUND_CARRY_FLAG, ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        if (player.getX() == lastX && player.getY() == lastY && player.getZ() == lastZ) {
                            return;
                        }
                        World.getInstance().updatePosition(getOwner(), player.getX(), player.getY(), player.getZ(), player.getHeading(), false);
                        lastX = player.getX();
                        lastY = player.getY();
                        lastZ = player.getZ();
                        PacketSendUtility.broadcastPacket(player, new SM_NPC_INFO(getOwner(), player), true);

                        if (loc != null) {
                            if (MathUtil.getDistance(getOwner(), loc.getXe(), loc.getYe(), loc.getZe()) <= 2f && getOwner().getRace() == Race.ASMODIANS) {
                                onFlagCaptured(player);
                            } else if (MathUtil.getDistance(getOwner(), loc.getXa(), loc.getYa(), loc.getZa()) <= 2f && getOwner().getRace() == Race.ELYOS) {
                                onFlagCaptured(player);
                            }
                        }

                    }
                }, 0, 15));
            } else if (!dropped && player.getController().getTask(TaskId.BATTLEGROUND_CARRY_FLAG) != null && player.getCommonData().getRace() != getOwner().getRace()) {
                dropped = true;
                getOwner().setFlagHolder(null);

                player.getController().getTask(TaskId.BATTLEGROUND_CARRY_FLAG).cancel(true);
                player.getController().addTask(TaskId.BATTLEGROUND_CARRY_FLAG, null);

                player.getBattleGround().broadcastToBattleGround(
                        player.getCommonData().getName() + (player.getLegion() != null ? " of " + player.getLegion().getLegionName() : "") + " has dropped the "
                        + (player.battlegroundFlag.getRace() == Race.ELYOS ? "Elyos" : "Asmodian") + " flag !", null);
                player.battlegroundFlag = null;
            } else if (dropped && player.getCommonData().getRace() == getOwner().getRace()) {
                World.getInstance().updatePosition(getOwner(), getOwner().getSpawn().getX(), getOwner().getSpawn().getY(), getOwner().getSpawn().getZ(), getOwner().getSpawn().getHeading(), true);

                getOwner().setFlagHolder(null);

                getOwner().state = BattleGroundFlagState.AT_BASE;

                player.getBattleGround().getInstance().doOnAllPlayers(new Visitor<Player>() {
                    @Override
                    public void visit(Player object) {
                        PacketSendUtility.sendPacket(object, new SM_NPC_INFO(getOwner(), object));
                    }
                });

                player.getBattleGround().broadcastToBattleGround(
                        player.getCommonData().getName() + (player.getLegion() != null ? " of " + player.getLegion().getLegionName() : "") + " has returned the "
                        + (getOwner().getRace() == Race.ELYOS ? "Elyos" : "Asmodian") + " flag at its base !", null);

            } else {
                PacketSendUtility.sendMessage(player, "unhandled case");
            }
        }
    }

    @Override
    public BattleGroundFlag getOwner() {
        return (BattleGroundFlag) super.getOwner();
    }
}
