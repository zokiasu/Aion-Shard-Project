package com.aionemu.gameserver.geoEngine.collision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class CollisionResults implements Iterable<CollisionResult> {

    private final ArrayList<CollisionResult> results = new ArrayList<CollisionResult>();
    private boolean sorted = true;
    private final boolean onlyFirst;
    private final byte intentions;
    private final int instanceId;

    public CollisionResults(byte intentions, boolean searchFirst, int instanceId) {
        this.intentions = intentions;
        this.onlyFirst = searchFirst;
        this.instanceId = instanceId;
    }

    public void clear() {
        results.clear();
    }

    public Iterator<CollisionResult> iterator() {
        if (!sorted) {
            Collections.sort(results);
            sorted = true;
        }

        return results.iterator();
    }

    public void addCollision(CollisionResult result) {
        if (Float.isNaN(result.getDistance())) {
            return;
        }
        results.add(result);
        if (!onlyFirst) {
            sorted = false;
        }
    }

    public int size() {
        return results.size();
    }

    public CollisionResult getClosestCollision() {
        if (size() == 0) {
            return null;
        }

        if (!sorted) {
            Collections.sort(results);
            sorted = true;
        }

        return results.get(0);
    }

    public CollisionResult getFarthestCollision() {
        if (size() == 0) {
            return null;
        }

        if (!sorted) {
            Collections.sort(results);
            sorted = true;
        }

        return results.get(size() - 1);
    }

    public CollisionResult getCollision(int index) {
        if (!sorted) {
            Collections.sort(results);
            sorted = true;
        }

        return results.get(index);
    }

    /**
     * Internal use only.
     *
     * @param index
     * @return
     */
    public CollisionResult getCollisionDirect(int index) {
        return results.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CollisionResults[");
        for (CollisionResult result : results) {
            sb.append(result).append(", ");
        }
        if (results.size() > 0) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * @return Returns the onlyFirst.
     */
    public boolean isOnlyFirst() {
        return onlyFirst;
    }

    /**
     * @return the intention
     */
    public byte getIntentions() {
        return intentions;
    }

    public int getInstanceId() {
        return instanceId;
    }
}
