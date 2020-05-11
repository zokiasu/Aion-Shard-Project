package com.aionemu.gameserver.geoEngine.scene.mesh;

import java.nio.Buffer;
import java.nio.IntBuffer;

/**
 * @author lex
 */
public class IndexIntBuffer extends IndexBuffer {

    private IntBuffer buf;

    public IndexIntBuffer(IntBuffer buffer) {
        this.buf = buffer;
    }

    @Override
    public int get(int i) {
        return buf.get(i);
    }

    @Override
    public void put(int i, int value) {
        buf.put(i, value);
    }

    @Override
    public int size() {
        return buf.limit();
    }

    @Override
    public Buffer getBuffer() {
        return buf;
    }
}
