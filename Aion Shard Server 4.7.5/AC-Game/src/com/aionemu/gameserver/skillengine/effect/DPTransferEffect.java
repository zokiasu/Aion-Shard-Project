package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author Sippolo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DPTransferEffect")
public class DPTransferEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        ((Player) effect.getEffected()).getCommonData().addDp(-effect.getReserved1());
        ((Player) effect.getEffector()).getCommonData().addDp(effect.getReserved1());
    }

    @Override
    public void calculate(Effect effect) {
        if (!super.calculate(effect, null, null)) {
            return;
        }
        effect.setReserved1(-getCurrentStatValue(effect));
    }

    private int getCurrentStatValue(Effect effect) {
        return ((Player) effect.getEffector()).getCommonData().getDp();
    }

    @SuppressWarnings("unused")
    private int getEffectedCurrentStatValue(Effect effect) {
        return ((Player) effect.getEffected()).getCommonData().getDp();
    }

    @SuppressWarnings("unused")
    private int getMaxStatValue(Effect effect) {
        return ((Player) effect.getEffected()).getGameStats().getMaxDp().getCurrent();
    }
}
