package com.aionemu.gameserver.controllers;

import javolution.util.FastMap;

import com.aionemu.gameserver.controllers.observer.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.shield.Shield;
import com.aionemu.gameserver.model.siege.FortressLocation;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.services.ShieldService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.world.World;

/**
 * @author Source
 */
public class ShieldController extends VisibleObjectController<Shield> {

    FastMap<Integer, ActionObserver> observed = new FastMap<Integer, ActionObserver>().shared();

    @Override
    public void see(VisibleObject object) {
        FortressLocation loc = SiegeService.getInstance().getFortress(getOwner().getId());
        Player player = (Player) object;

        if (loc.isUnderShield()) {
            if (loc.getRace() != SiegeRace.getByRace(player.getRace())) {
                ActionObserver observer = ShieldService.getInstance().createShieldObserver(loc.getLocationId(), player);
                if (observer != null) {
                    player.getObserveController().addObserver(observer);
                    observed.put(player.getObjectId(), observer);
                }
            }
        }
    }

    @Override
    public void notSee(VisibleObject object, boolean isOutOfRange) {
        FortressLocation loc = SiegeService.getInstance().getFortress(getOwner().getId());
        Player player = (Player) object;

        if (loc.isUnderShield()) {
            if (loc.getRace() != SiegeRace.getByRace(player.getRace())) {
                ActionObserver observer = observed.remove(player.getObjectId());
                if (observer != null) {
                    if (isOutOfRange) {
                        observer.moved();
                    }

                    player.getObserveController().removeObserver(observer);
                }
            }
        }
    }

    public void disable() {
        for (FastMap.Entry<Integer, ActionObserver> e = observed.head(), mapEnd = observed.tail(); (e = e.getNext()) != mapEnd; ) {
            ActionObserver observer = observed.remove(e.getKey());
            Player player = World.getInstance().findPlayer(e.getKey());
            if (player != null) {
                player.getObserveController().removeObserver(observer);
            }
        }
    }
}
