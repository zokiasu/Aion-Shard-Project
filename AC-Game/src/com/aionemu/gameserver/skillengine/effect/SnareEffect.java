package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SnareEffect")
public class SnareEffect extends BufEffect {

    @Override
    public void applyEffect(Effect effect) {
        effect.addToEffectedController();
    }

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, StatEnum.SNARE_RESISTANCE, null);
    }

    @Override
    public void endEffect(Effect effect) {
        super.endEffect(effect);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.SNARE.getId());
    }

    @Override
    public void startEffect(Effect effect) {
        super.startEffect(effect);
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.SNARE.getId());
        effect.setAbnormal(AbnormalState.SNARE.getId());
        if (effect.getEffected().isFlying() || effect.getEffected().isInState(CreatureState.GLIDING)) {
            PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_TARGET_IMMOBILIZE(effect.getEffected()));
            effect.getEffected().getMoveController().abortMove();
        }
    }
}
