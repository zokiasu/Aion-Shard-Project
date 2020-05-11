package com.aionemu.gameserver.geoEngine.scene.mesh;

import java.nio.Buffer;

/**
 * <code>IndexBuffer</code> is an abstraction for integer index buffers, it is
 * used to retrieve indices without knowing in which format they are stored
 * (ushort or uint).
 *
 * @author lex
 */
public abstract class IndexBuffer {

    public abstract int get(int i);

    public abstract void put(int i, int value);

    public abstract int size();

    public abstract Buffer getBuffer();
}
