package com.aionemu.gameserver.geoEngine.scene;

import com.aionemu.gameserver.geoEngine.bounding.BoundingVolume;
import com.aionemu.gameserver.geoEngine.collision.Collidable;
import com.aionemu.gameserver.geoEngine.collision.CollisionResults;
import com.aionemu.gameserver.geoEngine.math.Matrix4f;

/**
 * <code>CollisionData</code> is an interface that can be used to do
 * triangle-accurate collision between bounding volumes and rays.
 *
 * @author Kirill Vainer
 */
public interface CollisionData {

    public int collideWith(Collidable other,
                           Matrix4f worldMatrix,
                           BoundingVolume worldBound,
                           CollisionResults results);
}
