package com.aionemu.gameserver.taskmanager.tasks;

import javolution.util.FastMap;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.taskmanager.AbstractPeriodicTaskManager;

/**
 * @author ATracer
 */
public class PlayerMoveTaskManager extends AbstractPeriodicTaskManager {

    private final FastMap<Integer, Creature> movingPlayers = new FastMap<Integer, Creature>().shared();

    private PlayerMoveTaskManager() {
        super(200);
    }

    public void addPlayer(Creature player) {
        movingPlayers.put(player.getObjectId(), player);
    }

    public void removePlayer(Creature player) {
        movingPlayers.remove(player.getObjectId());
    }

    @Override
    public void run() {
        for (FastMap.Entry<Integer, Creature> e = movingPlayers.head(), mapEnd = movingPlayers.tail(); (e = e.getNext()) != mapEnd; ) {
            Creature player = e.getValue();
            player.getMoveController().moveToDestination();
        }
    }

    public static final PlayerMoveTaskManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static final class SingletonHolder {

        private static final PlayerMoveTaskManager INSTANCE = new PlayerMoveTaskManager();
    }
}
