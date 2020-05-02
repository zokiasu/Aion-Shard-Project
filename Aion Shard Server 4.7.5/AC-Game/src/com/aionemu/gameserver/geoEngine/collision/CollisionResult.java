package com.aionemu.gameserver.geoEngine.collision;

import com.aionemu.gameserver.geoEngine.math.Vector3f;
import com.aionemu.gameserver.geoEngine.scene.Geometry;

/**
 * @author Kirill
 */
public class CollisionResult implements Comparable<CollisionResult> {

    private Geometry geometry;
    private Vector3f contactPoint;
    private Vector3f contactNormal;
    private float distance;

    public CollisionResult(Vector3f contactPoint, float distance) {
        this.contactPoint = contactPoint;
        this.distance = distance;
    }

    public CollisionResult() {
    }

    public void setContactPoint(Vector3f point) {
        this.contactPoint = point;
    }

    public void setDistance(float dist) {
        this.distance = dist;
    }

    public int compareTo(CollisionResult other) {
        if (distance < other.distance) {
            return -1;
        } else if (distance > other.distance) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setContactNormal(Vector3f norm) {
        this.contactNormal = norm;
    }

    public void setGeometry(Geometry geom) {
        this.geometry = geom;
    }

    public Vector3f getContactNormal() {
        return contactNormal;
    }

    public Vector3f getContactPoint() {
        return contactPoint;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public float getDistance() {
        return distance;
    }
}
