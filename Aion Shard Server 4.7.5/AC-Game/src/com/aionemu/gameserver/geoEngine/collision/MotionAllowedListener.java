package com.aionemu.gameserver.geoEngine.collision;

import com.aionemu.gameserver.geoEngine.math.Vector3f;

public interface MotionAllowedListener {

    /**
     * Check if motion allowed. Modify position and velocity vectors
     * appropriately if not allowed..
     *
     * @param position
     * @param velocity
     */
    public void checkMotionAllowed(Vector3f position, Vector3f velocity);
}
