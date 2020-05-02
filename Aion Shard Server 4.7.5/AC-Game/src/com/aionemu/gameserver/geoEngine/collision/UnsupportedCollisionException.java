package com.aionemu.gameserver.geoEngine.collision;

/**
 * @author Kirill
 */
@SuppressWarnings("serial")
public class UnsupportedCollisionException extends UnsupportedOperationException {

    public UnsupportedCollisionException(Throwable arg0) {
        super(arg0);
    }

    public UnsupportedCollisionException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public UnsupportedCollisionException(String arg0) {
        super(arg0);
    }

    public UnsupportedCollisionException() {
        super();
    }
}
