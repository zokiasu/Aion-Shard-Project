package com.aionemu.gameserver.eventEngine.battleground.model.gameobjects;

import com.aionemu.gameserver.eventEngine.battleground.controllers.BattleGroundAgentController;
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
public class BattleGroundAgent extends Npc {

    public BattleGroundAgent(int objId, BattleGroundAgentController controller, SpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
        super(objId, controller, spawnTemplate, objectTemplate, objectTemplate.getLevel());
    }

    @Override
    public BattleGroundAgentController getController() {
        return (BattleGroundAgentController) super.getController();
    }

    public BattleGroundAgent getOwner() {
        return (BattleGroundAgent) this;
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
}
