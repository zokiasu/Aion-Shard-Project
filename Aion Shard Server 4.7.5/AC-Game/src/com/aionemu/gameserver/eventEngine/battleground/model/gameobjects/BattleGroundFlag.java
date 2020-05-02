package com.aionemu.gameserver.eventEngine.battleground.model.gameobjects;

import com.aionemu.gameserver.eventEngine.battleground.controllers.BattleGroundFlagController;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.NpcObjectType;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;

/**
 * @author Eloann
 */
public class BattleGroundFlag extends Npc {

    public enum BattleGroundFlagState {

        ON_FIELD,
        AT_BASE
    }
    public BattleGroundFlagState state = BattleGroundFlagState.AT_BASE;
    private Player flagHolder = null;

    public BattleGroundFlag(int objId, BattleGroundFlagController controller, SpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
        super(objId, controller, spawnTemplate, objectTemplate, objectTemplate.getLevel());
    }

    @Override
    public BattleGroundFlagController getController() {
        return (BattleGroundFlagController) super.getController();
    }

    public BattleGroundFlag getOwner() {
        return (BattleGroundFlag) this;
    }

    public boolean isEnemy(VisibleObject visibleObject) {
        return false;
    }

    protected boolean isEnemyNpc(Npc visibleObject) {
        return false;
    }

    protected boolean isEnemyPlayer(Player visibleObject) {
        return false;
    }

    protected boolean isEnemySummon(Summon summon) {
        return false;
    }

    @Override
    public NpcObjectType getNpcObjectType() {
        return NpcObjectType.NORMAL;
    }
    private Race race;

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Player getFlagHolder() {
        return flagHolder;
    }

    public void setFlagHolder(Player flagHolder) {
        this.flagHolder = flagHolder;
    }
}
