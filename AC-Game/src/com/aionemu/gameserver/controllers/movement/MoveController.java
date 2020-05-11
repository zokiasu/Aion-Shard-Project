package com.aionemu.gameserver.controllers.movement;

/**
 * @author ATracer
 */
public interface MoveController {

    void moveToDestination();

    float getTargetX2();

    float getTargetY2();

    float getTargetZ2();

    void setNewDirection(float x, float y, float z, byte heading);

    void startMovingToDestination();
    
    void skillMovement();

    void abortMove();

    byte getMovementMask();

    boolean isInMove();

    void setInMove(boolean value);
}
