//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.DuelResult;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.summons.SummonMode;
import com.aionemu.gameserver.model.summons.UnsummonType;
import com.aionemu.gameserver.model.templates.zone.ZoneType;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DUEL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DUEL_REQUEST_CANCEL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.summons.SummonsService;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import java.util.Iterator;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import javolution.util.FastMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DuelService {
    private static Logger log = LoggerFactory.getLogger(DuelService.class);
    private FastMap<Integer, Integer> duels;
    private FastMap<Integer, Future<?>> drawTasks;

    public static final DuelService getInstance() {
        return DuelService.SingletonHolder.instance;
    }

    private DuelService() {
        this.duels = (new FastMap()).shared();
        this.drawTasks = (new FastMap()).shared();
        log.info("DuelService started.");
    }

    public void onDuelRequest(Player var1, Player var2) {
        if (!var1.isInsideZoneType(ZoneType.PVP) && !var2.isInsideZoneType(ZoneType.PVP)) {
            if (!var1.isEnemy(var2) && !this.isDueling(var1.getObjectId()) && !this.isDueling(var2.getObjectId())) {
                Iterator var3 = var2.getPosition().getMapRegion().getZones(var2).iterator();

                ZoneInstance var4;
                do {
                    if (!var3.hasNext()) {
                        RequestResponseHandler var5 = new RequestResponseHandler(var1) {
                            public void denyRequest(Creature var1, Player var2) {
                                DuelService.this.rejectDuelRequest((Player)var1, var2);
                            }

                            public void acceptRequest(Creature var1, Player var2) {
                                if (!DuelService.this.isDueling(var1.getObjectId())) {
                                    DuelService.this.startDuel((Player)var1, var2);
                                }

                            }
                        };
                        var2.getResponseRequester().putRequest(50028, var5);
                        PacketSendUtility.sendPacket(var2, new SM_QUESTION_WINDOW(50028, 0, 0, new Object[]{var1.getName()}));
                        PacketSendUtility.sendPacket(var2, SM_SYSTEM_MESSAGE.STR_DUEL_REQUESTED(var1.getName()));
                        return;
                    }

                    var4 = (ZoneInstance)var3.next();
                } while((var4.isOtherRaceDuelsAllowed() || var2.getRace().equals(var1.getRace())) && (var4.isSameRaceDuelsAllowed() || !var2.getRace().equals(var1.getRace())));

                PacketSendUtility.sendPacket(var1, SM_SYSTEM_MESSAGE.STR_MSG_DUEL_CANT_IN_THIS_ZONE);
            } else {
                PacketSendUtility.sendPacket(var1, SM_SYSTEM_MESSAGE.STR_DUEL_HE_REJECT_DUEL(var2.getName()));
            }
        } else {
            PacketSendUtility.sendPacket(var1, SM_SYSTEM_MESSAGE.STR_DUEL_PARTNER_INVALID(var2.getName()));
        }
    }

    public void confirmDuelWith(Player var1, Player var2) {
        if (!var1.isEnemy(var2)) {
            RequestResponseHandler var3 = new RequestResponseHandler(var2) {
                public void denyRequest(Creature var1, Player var2) {
                    DuelService.log.debug("[Duel] Player " + var2.getName() + " confirmed his duel with " + var1.getName());
                }

                public void acceptRequest(Creature var1, Player var2) {
                    DuelService.this.cancelDuelRequest(var2, (Player)var1);
                }
            };
            var1.getResponseRequester().putRequest(50030, var3);
            PacketSendUtility.sendPacket(var1, new SM_QUESTION_WINDOW(50030, 0, 0, new Object[]{var2.getName()}));
            PacketSendUtility.sendPacket(var1, SM_SYSTEM_MESSAGE.STR_DUEL_REQUEST_TO_PARTNER(var2.getName()));
        }
    }

    private void rejectDuelRequest(Player var1, Player var2) {
        log.debug("[Duel] Player " + var2.getName() + " rejected duel request from " + var1.getName());
        PacketSendUtility.sendPacket(var1, new SM_DUEL_REQUEST_CANCEL(1300097, var2.getName()));
        PacketSendUtility.sendPacket(var2, SM_SYSTEM_MESSAGE.STR_DUEL_REJECT_DUEL(var1.getName()));
    }

    private void cancelDuelRequest(Player var1, Player var2) {
        log.debug("[Duel] Player " + var1.getName() + " cancelled his duel request with " + var2.getName());
        PacketSendUtility.sendPacket(var2, new SM_DUEL_REQUEST_CANCEL(1300134, var1.getName()));
        PacketSendUtility.sendPacket(var1, SM_SYSTEM_MESSAGE.STR_DUEL_WITHDRAW_REQUEST(var2.getName()));
    }

    private void startDuel(Player var1, Player var2) {
        PacketSendUtility.sendPacket(var1, SM_DUEL.SM_DUEL_STARTED(var2.getObjectId()));
        PacketSendUtility.sendPacket(var2, SM_DUEL.SM_DUEL_STARTED(var1.getObjectId()));
        this.createDuel(var1.getObjectId(), var2.getObjectId());
        this.createTask(var1, var2);
    }

    public void loseDuel(Player var1) {
        if (this.isDueling(var1.getObjectId())) {
            int var2 = (Integer)this.duels.get(var1.getObjectId());
            var1.getAggroList().clear();
            Player var3 = World.getInstance().findPlayer(var2);
            if (var3 != null) {
                var3.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
                var3.getController().cancelCurrentSkill();
                var3.getAggroList().clear();
                if (var1.getSummon() != null) {
                    SummonsService.doMode(SummonMode.GUARD, var1.getSummon(), UnsummonType.UNSPECIFIED);
                }

                if (var3.getSummon() != null) {
                    SummonsService.doMode(SummonMode.GUARD, var3.getSummon(), UnsummonType.UNSPECIFIED);
                }

                if (var1.getSummonedObj() != null) {
                    var1.getSummonedObj().getController().cancelCurrentSkill();
                }

                if (var3.getSummonedObj() != null) {
                    var3.getSummonedObj().getController().cancelCurrentSkill();
                }

                PacketSendUtility.sendPacket(var3, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_WON, var1.getName()));
                PacketSendUtility.sendPacket(var1, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_LOST, var3.getName()));
                PacketSendUtility.sendMessage(var1, "\ue06eГосподь: Не отчаивайся!");
                PacketSendUtility.sendMessage(var3, "\ue05eДьявол: Продолжай в том же духе!");
            } else {
                log.warn("CHECKPOINT : duel opponent is already out of world");
            }

            this.removeDuel(var1.getObjectId(), var2);
        }
    }

    public void loseArenaDuel(Player var1) {
        if (this.isDueling(var1.getObjectId())) {
            var1.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
            var1.getController().cancelCurrentSkill();
            int var2 = (Integer)this.duels.get(var1.getObjectId());
            Player var3 = World.getInstance().findPlayer(var2);
            if (var3 != null) {
                var3.getEffectController().removeAbnormalEffectsByTargetSlot(SkillTargetSlot.DEBUFF);
                var3.getController().cancelCurrentSkill();
            } else {
                log.warn("CHECKPOINT : duel opponent is already out of world");
            }

            this.removeDuel(var1.getObjectId(), var2);
        }
    }

    private void createTask(final Player var1, final Player var2) {
        ScheduledFuture var3 = ThreadPoolManager.getInstance().schedule(new Runnable() {
            public void run() {
                if (DuelService.this.isDueling(var1.getObjectId(), var2.getObjectId())) {
                    PacketSendUtility.sendPacket(var1, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_DRAW, var1.getName()));
                    PacketSendUtility.sendPacket(var2, SM_DUEL.SM_DUEL_RESULT(DuelResult.DUEL_DRAW, var2.getName()));
                    DuelService.this.removeDuel(var1.getObjectId(), var2.getObjectId());
                }

            }
        }, 300000L);
        this.drawTasks.put(var1.getObjectId(), var3);
        this.drawTasks.put(var2.getObjectId(), var3);
        PacketSendUtility.sendPacket(var1, new SM_QUEST_ACTION(4, 300));
        PacketSendUtility.sendPacket(var2, new SM_QUEST_ACTION(4, 300));
    }

    public boolean isDueling(int var1) {
        return this.duels.containsKey(var1) && this.duels.containsValue(var1);
    }

    public boolean isDueling(int var1, int var2) {
        return this.duels.containsKey(var1) && (Integer)this.duels.get(var1) == var2;
    }

    public void createDuel(int var1, int var2) {
        this.duels.put(var1, var2);
        this.duels.put(var2, var1);
    }

    private void removeDuel(int var1, int var2) {
        this.duels.remove(var1);
        this.duels.remove(var2);
        this.removeTask(var1);
        this.removeTask(var2);
        Player var3 = World.getInstance().findPlayer(var1);
        Player var4 = World.getInstance().findPlayer(var2);
        PacketSendUtility.sendPacket(var3, new SM_QUEST_ACTION(4, 0));
        PacketSendUtility.sendPacket(var4, new SM_QUEST_ACTION(4, 0));
    }

    private void removeTask(int var1) {
        Future var2 = (Future)this.drawTasks.get(var1);
        if (var2 != null && !var2.isDone()) {
            var2.cancel(true);
            this.drawTasks.remove(var1);
        }

    }

    private static class SingletonHolder {
        protected static final DuelService instance = new DuelService();

        private SingletonHolder() {
        }
    }
}
