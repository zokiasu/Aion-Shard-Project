package ai.instance.tiamatStrongHold;

import ai.AggressiveNpcAI2;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.spawns.SpawnTemplate;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.world.WorldMapInstance;

import java.util.List;

/**
 * @author Cheatkiller
 */
@AIName("traitorkumbanda")
public class TraitorKumbandaAI2 extends AggressiveNpcAI2 {

    private boolean isFinalBuff;

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (Rnd.get(1, 100) < 5) {
            spawnTimeAccelerator();
            spawnKumbandaGhost();
        }
        if (!isFinalBuff && getOwner().getLifeStats().getHpPercentage() <= 5) {
            isFinalBuff = true;
            AI2Actions.useSkill(this, 20942);
        }
    }

    private void spawnTimeAccelerator() {
        if (getPosition().getWorldMapInstance().getNpc(283086) == null) {
            SkillEngine.getInstance().getSkill(getOwner(), 20726, 55, getOwner()).useNoAnimationSkill();
            spawn(283086, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
            rndSpawn(283086, 6);
        }
    }

    private void spawnKumbandaGhost() {
        if (getPosition().getWorldMapInstance().getNpc(283085) == null && getOwner().getLifeStats().getHpPercentage() <= 50) {
            spawn(283085, getOwner().getX(), getOwner().getY(), getOwner().getZ(), (byte) 0);
        }
    }

    private void deleteNpcs(List<Npc> npcs) {
        for (Npc npc : npcs) {
            if (npc != null) {
                npc.getController().onDelete();
            }
        }
    }

    @Override
    protected void handleDied() {
        super.handleDied();
        WorldMapInstance instance = getPosition().getWorldMapInstance();
        deleteNpcs(instance.getNpcs(283086));
        deleteNpcs(instance.getNpcs(283088));
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        isFinalBuff = false;
    }

    private void rndSpawn(int npcId, int count) {
        for (int i = 0; i < count; i++) {
            SpawnTemplate template = rndSpawnInRange(npcId, Rnd.get(10, 20));
            SpawnEngine.spawnObject(template, getPosition().getInstanceId());
        }
    }

    private SpawnTemplate rndSpawnInRange(int npcId, int dist) {
        float direction = Rnd.get(0, 199) / 100f;
        float x1 = (float) (Math.cos(Math.PI * direction) * dist);
        float y1 = (float) (Math.sin(Math.PI * direction) * dist);
        return SpawnEngine.addNewSingleTimeSpawn(getPosition().getMapId(), npcId, getPosition().getX() + x1, getPosition().getY()
                + y1, getPosition().getZ(), getPosition().getHeading());
    }
}
