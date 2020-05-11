package com.aionemu.gameserver.utils.collections;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author xTz
 */
public class ListSplitter<T> {

    private T[] objects;
    private Class<?> componentType;
    private int splitCount;
    private int curentIndex = 0;
    private int length = 0;

    @SuppressWarnings("unchecked")
    public ListSplitter(Collection<T> collection, int splitCount) {
        if (collection != null && collection.size() > 0) {
            this.splitCount = splitCount;
            length = collection.size();
            this.objects = collection.toArray((T[]) new Object[length]);
            componentType = objects.getClass().getComponentType();
        }
    }

    public List<T> getNext(int splitCount) {
        this.splitCount = splitCount;
        return getNext();
    }

    public List<T> getNext() {
        @SuppressWarnings("unchecked")
        T[] subArray = (T[]) Array.newInstance(componentType, Math.min(splitCount, length - curentIndex));
        if (subArray.length > 0) {
            System.arraycopy(objects, curentIndex, subArray, 0, subArray.length);
            curentIndex += subArray.length;
        }
        return Arrays.asList(subArray);
    }

    public int size() {
        return length;
    }

    public boolean isLast() {
        return curentIndex == length;
    }
}
