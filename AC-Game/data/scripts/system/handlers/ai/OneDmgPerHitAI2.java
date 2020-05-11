package ai;

import com.aionemu.gameserver.ai2.AIName;

/**
 * @author xTz
 */
@AIName("onedmgperhit")
public class OneDmgPerHitAI2 extends NoActionAI2 {

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }
}
