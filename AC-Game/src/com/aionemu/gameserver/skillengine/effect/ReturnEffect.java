package com.aionemu.gameserver.skillengine.effect;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGround;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.items.storage.StorageType;
import com.aionemu.gameserver.services.teleport.TeleportService2;
import com.aionemu.gameserver.skillengine.model.Effect;

/**
 * @author ATracer
 * @modified Kill3r
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnEffect")
public class ReturnEffect extends EffectTemplate {

    @Override
    public void applyEffect(Effect effect) {
    	Player player = (Player) effect.getEffector();
    	BattleGround battleground = player.getBattleGround();
        if (battleground != null) {
            battleground.broadcastToBattleGround(player.getName() + " has left the battleground.", player.getCommonData().getRace());
            battleground.removePlayer(player);
            player.setBattleGround(null);
            if (player.battlegroundObserve != 0) {
                if (player.battlegroundBetE > 0) {
                    player.getStorage(StorageType.CUBE.getId()).increaseKinah(player.battlegroundBetE);
                    player.battlegroundBetE = 0;
                } else if (player.battlegroundBetA > 0) {
                    player.getStorage(StorageType.CUBE.getId()).increaseKinah(player.battlegroundBetA);
                    player.battlegroundBetA = 0;
                }

                player.battlegroundObserve = 0;
            }
        }
        
        TeleportService2.moveToBindLocation((Player) effect.getEffector(), true);
    }

    @Override
    public void calculate(Effect effect) {
        //cannot use return inside FFA!
        if(effect.getEffected() instanceof Player && (((Player) effect.getEffected()).isInFFA())){
            return;
        }
        //cannot use return inside 1vs1
        if(effect.getEffected() instanceof Player && ((Player) effect.getEffected()).isInDuelArena()){
            return;
        }
        if (effect.getEffected().isSpawned()) {
            effect.addSucessEffect(this);
        }
    }
}
