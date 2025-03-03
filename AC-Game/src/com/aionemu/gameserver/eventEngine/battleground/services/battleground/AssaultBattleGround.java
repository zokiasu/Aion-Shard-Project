package com.aionemu.gameserver.eventEngine.battleground.services.battleground;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.services.factories.SurveyFactory;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.WorldMapInstance;

/**
 * @author Eloann
 */
public class AssaultBattleGround extends BattleGround {

    private int totalScoreE = 0;
    private int totalScoreA = 0;

    public AssaultBattleGround(int tplId, WorldMapInstance instance) {
        super(tplId, instance);
    }

    @Override
    public void increasePoints(Player player, int value) {
        super.increasePoints(player, value);

        if (player.getCommonData().getRace() == Race.ELYOS) {
            totalScoreE += value;
            if (totalScoreE >= template.getTargetScore()) {
                end();
            }
        } else if (player.getCommonData().getRace() == Race.ASMODIANS) {
            totalScoreA += value;
            if (totalScoreA >= template.getTargetScore()) {
                end();
            }
        }
    }

    @Override
    public void decreasePoints(Player player, int value) {
        super.decreasePoints(player, value);

        if (player.getCommonData().getRace() == Race.ELYOS && totalScoreE >= value) {
            totalScoreE -= value;
        } else if (player.getCommonData().getRace() == Race.ASMODIANS && totalScoreA >= value) {
            totalScoreA -= value;
        } else if (player.getCommonData().getRace() == Race.ELYOS && totalScoreE <= value) {
            totalScoreE = 0;
        } else if (player.getCommonData().getRace() == Race.ASMODIANS && totalScoreA >= value) {
            totalScoreA = 0;
        }
    }

    public void onKillPlayer(Player victim, Creature killer) {
        decreasePoints(victim, template.getRules().getDie());
        increasePoints((Player) killer, template.getRules().getKillPlayer());
    }

    @Override
    public void start() {
        BattleGroundTemplate bgTemplate = DataManager.BATTLEGROUND_DATA.getBattleGroundTemplate(tplId);
        /*
         * Spawn Healers
         */
        SpawnTemplate se = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 203098, bgTemplate.getHealerLocation().getXe(), bgTemplate.getHealerLocation().getYe(), bgTemplate.getHealerLocation().getZe(), (byte) 0);
        SpawnTemplate sa = SpawnEngine.addNewSingleTimeSpawn(bgTemplate.getWorldId(), 203557, bgTemplate.getHealerLocation().getXa(), bgTemplate.getHealerLocation().getYa(), bgTemplate.getHealerLocation().getZa(), (byte) 0);

        SpawnEngine.spawnBGHealer(se, instance.getInstanceId(), Race.ELYOS);
        SpawnEngine.spawnBGHealer(sa, instance.getInstanceId(), Race.ASMODIANS);

        super.start();

    }

    @Override
    public void end() {
        super.end();

        for (Player p : players) {
            HTMLService.showHTML(p, SurveyFactory.buildAssaultBattleGroundReport(p), 152000001);
            p.getEffectController().removeAllEffects();
            if (!p.getLifeStats().isAlreadyDead()) {
                if (p.getCommonData().getRace() == Race.ELYOS) {
                    TeleportService2.teleportTo(p, 110010000, 1374f, 1399f, 573f, (byte) 0);
                } else {
                    TeleportService2.teleportTo(p, 120010000, 1324f, 1550f, 210f, (byte) 0);
                }
            }
        }

    }
}
