package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 * @reworked Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnPointEffect")
public class ReturnPointEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
        ItemTemplate itemTemplate = effect.getItemTemplate();
        int worldId = itemTemplate.getReturnWorldId();
        String pointAlias = itemTemplate.getReturnAlias();
        TeleportService2.useTeleportScroll(((Player) effect.getEffector()), pointAlias, worldId);
    }

    @Override
    public void calculate(Effect effect) {
        //cannot use return inside ffa
        if(effect.getEffected() instanceof Player && ((Player) effect.getEffected()).isInFFA()){
            return;
        }
        //cannot use return inside 1v1
        if(effect.getEffected() instanceof Player && ((Player) effect.getEffected()).isInDuelArena()){
            return;
        }
        ItemTemplate itemTemplate = effect.getItemTemplate();
        if (itemTemplate != null) {
            effect.addSucessEffect(this);
        }
    }
}
