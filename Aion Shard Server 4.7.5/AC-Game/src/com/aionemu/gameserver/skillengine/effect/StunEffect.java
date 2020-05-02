package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.stats.container.StatEnum;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * @author ATracer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StunEffect")
public class StunEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        if (!effect.getEffected().getEffectController().hasMagicalStateEffect() && !effect.getEffected().getEffectController().isAbnormalSet(AbnormalState.CANNOT_MOVE)) {
            effect.addToEffectedController();
            effect.setIsMagicalState(true);
        }
    }

    @Override
    public void calculate(Effect effect) {
        super.calculate(effect, StatEnum.STUN_RESISTANCE, null);
    }

    @Override
    public void startEffect(Effect effect) {
        final Creature effected = effect.getEffected();
        effected.getController().cancelCurrentSkill();
        effected.getMoveController().abortMove();
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.STUN.getId());
        effect.setAbnormal(AbnormalState.STUN.getId());
        PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_TARGET_IMMOBILIZE(effect.getEffected()));
    }

    @Override
    public void endEffect(Effect effect) {
        effect.setIsMagicalState(false);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.STUN.getId());
    }
}
