package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TARGET_IMMOBILIZE;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;

/**
 * Created by Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StunAlwaysEffect")
public class StunAlwaysEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect){
        if(!effect.getEffected().getEffectController().hasMagicalStateEffect() && !effect.getEffected().getEffectController().hasAbnormalEffect(AbnormalState.STUN.getId())){
            effect.addToEffectedController();
            effect.setIsMagicalState(true);
        }
    }

    @Override
    public void startEffect(Effect effect){
        final Creature effected = effect.getEffected();
        effected.getController().cancelCurrentSkill();
        effected.getMoveController().abortMove();
        effect.getEffected().getEffectController().setAbnormal(AbnormalState.STUN.getId());
        effect.setAbnormal(AbnormalState.STUN.getId());
        PacketSendUtility.broadcastPacketAndReceive(effect.getEffected(), new SM_TARGET_IMMOBILIZE(effect.getEffected()));
    }

    @Override
    public void endEffect(Effect effect){
        effect.setIsMagicalState(false);
        effect.getEffected().getEffectController().unsetAbnormal(AbnormalState.STUN.getId());
    }
}