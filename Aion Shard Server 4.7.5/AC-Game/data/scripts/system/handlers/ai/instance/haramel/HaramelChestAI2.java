package ai.instance.haramel;

import ai.ChestAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.world.WorldPosition;

/**
 * @author xTz
 */
@AIName("haramelchest")
public class HaramelChestAI2 extends ChestAI2 {

    @Override
    protected void handleDespawned() {
        WorldPosition p = getPosition();
        if (p != null && p.getWorldMapInstance() != null) {
            spawn(700852, 224.598f, 331.143f, 141.892f, (byte) 90);
        }
        super.handleDespawned();
    }
}
