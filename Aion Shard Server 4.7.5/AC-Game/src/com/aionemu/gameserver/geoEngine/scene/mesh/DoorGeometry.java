package com.aionemu.gameserver.geoEngine.scene.mesh;

import com.aionemu.gameserver.geoEngine.collision.Collidable;
import com.aionemu.gameserver.geoEngine.collision.CollisionResults;
import com.aionemu.gameserver.geoEngine.math.Ray;
import com.aionemu.gameserver.geoEngine.scene.Geometry;
import com.aionemu.gameserver.geoEngine.scene.Mesh;

import java.util.BitSet;

/**
 * @author MrPoke, Rolandas
 */
public class DoorGeometry extends Geometry {

    BitSet instances = new BitSet();
    private boolean foundTemplate = false;

    public DoorGeometry(String name, Mesh mesh) {
        super(name, mesh);
    }

    public void setDoorState(int instanceId, boolean isOpened) {
        instances.set(instanceId, isOpened);
    }

    @Override
    public int collideWith(Collidable other, CollisionResults results) {
        if (foundTemplate && instances.get(results.getInstanceId())) {
            return 0;
        }
        if (other instanceof Ray) {
            // no collision if inside arena spheres, so just check volume
            return getWorldBound().collideWith(other, results);
        }
        return super.collideWith(other, results);
    }

    public boolean isFoundTemplate() {
        return foundTemplate;
    }

    public void setFoundTemplate(boolean foundTemplate) {
        this.foundTemplate = foundTemplate;
    }

    @Override
    public void updateModelBound() {
        // duplicate call distorts world bounds, thus do only once
        if (worldBound == null) {
            mesh.updateBound();
            worldBound = getModelBound().transform(cachedWorldMat, worldBound);
        }
    }
}
