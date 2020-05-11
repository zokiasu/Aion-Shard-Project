package com.aionemu.gameserver.eventEngine.battleground.model.gameobjects;

import com.aionemu.gameserver.eventEngine.battleground.controllers.BattleGroundHealerController;
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
public class BattleGroundHealer extends Npc {

    public BattleGroundHealer(int objId, BattleGroundHealerController controller, SpawnTemplate spawnTemplate, NpcTemplate objectTemplate) {
        super(objId, controller, spawnTemplate, objectTemplate, objectTemplate.getLevel());
    }

    @Override
    public BattleGroundHealerController getController() {
        return (BattleGroundHealerController) super.getController();
    }

    public BattleGroundHealer getOwner() {
        return (BattleGroundHealer) this;
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
