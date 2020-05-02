package ai.instance.beshmundirTemple;

import ai.AggressiveNpcAI2;
import com.aionemu.gameserver.ai2.AIName;
import com.aionemu.gameserver.ai2.handler.AttackEventHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.services.NpcShoutsService;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Cheatkiller
 * @author Antraxx
 */
@AIName("dorakiki_the_bold")
public class DorakikiTheBoldAI2 extends AggressiveNpcAI2 {

    private AtomicBoolean isHome = new AtomicBoolean(true);

    @Override
    protected void handleAttack(Creature creature) {
        super.handleAttack(creature);
        if (isHome.compareAndSet(true, false)) {
            NpcShoutsService.getInstance().sendMsg(getOwner(), 1500079, getObjectId(), 0, 1000);
        }
    }

    @Override
    protected void handleAttackComplete() {
        AttackEventHandler.onAttackComplete(this);
        if (getEffectController().hasAbnormalEffect(18901)) {
            getEffectController().removeEffect(18901);
        }
    }

    @Override
    protected void handleBackHome() {
        super.handleBackHome();
        if (getEffectController().hasAbnormalEffect(18901)) {
            getEffectController().removeEffect(18901);
        }
        isHome.set(true);
    }
}
