package ai.instance.ophidanBridge;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.skillengine.SkillEngine;

/**
 * @author DeathMagnestic
 */
@AIName("vera")
public class VeraAI2 extends AggressiveNpcAI2 {

    @Override
    protected void handleSpawned() {
        super.handleSpawned();
        SkillEngine.getInstance().getSkill(getOwner(), 21438, 1, getOwner()).useNoAnimationSkill(); // Buff surkana
    }
}
