package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.npc.NpcTemplate;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_SETTINGS;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author ATracer
 * @modified Cheatkiller
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolymorphEffect")
public class PolymorphEffect extends TransformEffect {

    @Override
    public void applyEffect(Effect effect) {
        super.applyEffect(effect);
        if (effect.getEffected() instanceof Player) {
            final Player player = (Player) effect.getEffected();
            player.getKnownList().doOnAllNpcs(new Visitor<Npc>() {
                @Override
                public void visit(Npc npc) {
                    PacketSendUtility.sendPacket(player, new SM_CUSTOM_SETTINGS(npc.getObjectId(), 0, npc.getType(player), 0));
                }
            });
        }
    }

    @Override
    public void startEffect(Effect effect) {
        if (model > 0) {
            Creature effected = effect.getEffected();
            NpcTemplate template = DataManager.NPC_DATA.getNpcTemplate(model);
            if (template != null) {
                effected.getTransformModel().setTribe(template.getTribe(), false);
            }
        }
        super.startEffect(effect);
    }

    @Override
    public void endEffect(Effect effect) {
        effect.getEffected().getTransformModel().setActive(false);
        if (effect.getEffected() instanceof Player) {
            final Player player = (Player) effect.getEffected();
            player.getKnownList().doOnAllNpcs(new Visitor<Npc>() {
                @Override
                public void visit(Npc npc) {
                    PacketSendUtility.sendPacket(player, new SM_CUSTOM_SETTINGS(npc.getObjectId(), 0, npc.getType(player), 0));
                    player.getTransformModel().setTribe(null, false);
                }
            });
        }
        super.endEffect(effect);
    }
}
