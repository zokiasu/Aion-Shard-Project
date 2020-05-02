package com.aionemu.gameserver.services.player;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.world.knownlist.Visitor;

/**
 * @author Source
 */
public class PlayerVisualStateService {

    public static void hideValidate(final Player hiden) {
        hiden.getKnownList().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player observer) {
                boolean canSee = observer.canSee(hiden);
                boolean isSee = observer.isSeePlayer(hiden);

                if (canSee && !isSee) {
                    observer.getKnownList().addVisualObject(hiden);
                } else if (!canSee && isSee) {
                    observer.getKnownList().delVisualObject(hiden, false);
                }
            }
        });
    }

    public static void seeValidate(final Player search) {
        search.getKnownList().doOnAllPlayers(new Visitor<Player>() {
            @Override
            public void visit(Player hide) {
                boolean canSee = search.canSee(hide);
                boolean isSee = search.isSeePlayer(hide);

                if (canSee && !isSee) {
                    search.getKnownList().addVisualObject(hide);
                } else if (!canSee && isSee) {
                    search.getKnownList().delVisualObject(hide, false);
                }
            }
        });
    }
}
