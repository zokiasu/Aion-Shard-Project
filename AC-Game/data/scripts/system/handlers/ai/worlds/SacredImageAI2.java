package ai.worlds;

import ai.NoActionAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.utils.MathUtil;

/**
 * @author Steve
 */
@AIName("sacred_image")
public class SacredImageAI2 extends NoActionAI2 {

    @Override
    protected void handleCreatureSee(Creature creature) {
        checkDistance(creature);
    }

    @Override
    protected void handleCreatureMoved(Creature creature) {
        checkDistance(creature);
    }

    private void checkDistance(Creature creature) {
        int spellid = getOwner().getNpcId() == 258281 ? 20373 : 20374;
        if (creature instanceof Player) {
            Player player = (Player) creature;
            if ((player.getRace().equals(Race.ASMODIANS) && getOwner().getNpcId() == 258281) || (player.getRace().equals(Race.ELYOS) && getOwner().getNpcId() == 258280)) {
                return;
            }
            if (MathUtil.isIn3dRangeLimited(getOwner(), creature, 0, 25)) {
                SkillEngine.getInstance().getSkill(getOwner(), spellid, 65, player).useNoAnimationSkill();
            }
        }
    }

    @Override
    public int modifyDamage(int damage) {
        return 1;
    }
}
