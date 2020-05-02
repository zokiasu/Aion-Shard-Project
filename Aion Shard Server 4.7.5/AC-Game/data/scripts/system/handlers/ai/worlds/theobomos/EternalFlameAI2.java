package ai.worlds.theobomos;

import com.aionemu.gameserver.ai2.AI2Actions;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.NpcAI2;
import com.aionemu.gameserver.world.WorldPosition;

@AIName("eternal_flame")
public class EternalFlameAI2 extends NpcAI2 {

    @Override
    protected void handleDied() {
        WorldPosition p = getPosition();
        if (p != null) {
            spawn(214552, p.getX(), p.getY(), p.getZ() - 3, (byte) 0);
        }
        super.handleDied();
        AI2Actions.deleteOwner(this);
    }

    @Override
    public int modifyDamage(int damage) {
        return super.modifyDamage(1);
    }
}
