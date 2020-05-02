package com.aionemu.gameserver.controllers;

import javolution.util.FastMap;

import com.aionemu.gameserver.controllers.observer.RoadObserver;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.road.Road;

/**
 * @author SheppeR
 */
public class RoadController extends VisibleObjectController<Road> {

    FastMap<Integer, RoadObserver> observed = new FastMap<Integer, RoadObserver>().shared();

    @Override
    public void see(VisibleObject object) {
        Player p = (Player) object;
        RoadObserver observer = new RoadObserver(getOwner(), p);
        p.getObserveController().addObserver(observer);
        observed.put(p.getObjectId(), observer);
    }

    @Override
    public void notSee(VisibleObject object, boolean isOutOfRange) {
        Player p = (Player) object;
        RoadObserver observer = observed.remove(p.getObjectId());
        if (isOutOfRange) {
            observer.moved();
        }
        p.getObserveController().removeObserver(observer);
    }
}
