package com.quew8.gutils.collections;

import com.quew8.gutils.ArrayUtils;

/**
 *
 * @author Quew8
 * 
 * @param <T>
 */
public class Bag<T> extends AbstractCollection {
	private final Class<T> clazz;
    private T[] data;
    private int size = 0;
    
    /**
     * 
     * @param data 
     */
    public Bag(Class<T> clazz, T[] data) {
    	this.clazz = clazz;
        this.data = data;
    }
    
    /**
     * 
     * @param data 
     */
    public Bag(T[] data) {
    	this(ArrayUtils.getClass(data[0]), data);
    }
    
    /**
     * 
     * @param clazz
     * @param capacity 
     */
    public Bag(Class<T> clazz, int capacity) {
    	this(clazz, ArrayUtils.createArray(clazz, capacity));
    }
    
    /**
     * 
     * @param clazz
     */
    public Bag(Class<T> clazz) {
        this(clazz, 10);
    }
    
    /**
     * Removes the element at the specified position in this Bag. does this by
     * overwriting it was last element then removing last element
     *
     * @param index the index of element to be removed
     * @return element that was removed from the Bag
     */
    public T remove(int index) {
        T t = data[index];
        data[index] = data[--size];
        data[size] = null;
        return t;
    }
    
    /**
     * Removes the first occurrence of the specified element from this Bag, if
     * it is present. If the Bag does not contain the element, it is unchanged.
     * does this by overwriting it was last element then removing last element
     *
     * @param t element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(T t) {
        for (int i = 0; i < size; i++) {
            if (t == data[i]) {
                data[i] = data[--size];
                data[size] = null;
                return true;
            }
        }

        return false;
    }
    
    /**
     * Removes all of the elements from this bag that are also in the
     * specified bag.
     *
     * @param bag the bag containing elements to be removed from this bag
     * @return <tt>true</tt> if this Bag changed as a result of the call
     */
    public boolean removeAll(Bag<T> bag) {
        boolean modified = false;
        for (int i = 0; i < bag.size(); i++) {
            T o1 = bag.get(i);
            for (int j = 0; j < size; j++) {
                T o2 = data[j];
                if (o1 == o2) {
                    remove(j);
                    j--;
                    modified = true;
                    break;
                }
            }
        }
        return modified;
    }
    
    /**
     *
     * @param index the index of the element to return
     * @return the element at the specified position in bag
     */
    public T get(int index) {
        return data[index];
    }

    @Override
    public int capacity() {
        return data.length;
    }

    @Override
    public int used() {
        return size;
    }
    
    /**
     *
     * @return the number of elements in this bag
     */
    public int size() {
        return size;
    }
    
    /**
     * Adds the specified element to the end of this bag. Will expand the bag as
     * needed.
     *
     * @param t element to be added to this list
     */
    public void add(T t) {
        if (size == data.length) {
            expand();
        }
        data[size++] = t;
    }
    
    public void addAll(T[] ta, int offset, int length) {
        if(length + size < data.length) {
            expand(length + size);
        }
        System.arraycopy(ta, offset, data, size, length);
        size += length;
    }
    
    public void addAll(T[] ta) {
        addAll(ta, 0, ta.length);
    }
    
    public void ensureCapacity(int capacity) {
        if(data.length < capacity) {
            expand(capacity);
        }
    }
    
    public void trim() {
    	if(capacity() > size()) {
    		expand(size());
    	}
    }
    
    public T[] getBackingArray() {
    	return data;
    }
    
    private void expand() {
        expand((( data.length * 3 ) / 2 ) + 1);
    }
    
    private void expand( int newCapacity) {
        T[] oldData = data;
        data = ArrayUtils.createArray(clazz, newCapacity);
        System.arraycopy(oldData, 0, data, 0, oldData.length);
    }
    
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }
    
    public T[] copy(T[] dest, int soff, int doff, int length) {
        System.arraycopy(data, soff, dest, doff, length);
        return dest;
    }
    
    public T[] copy(T[] dest, int length) {
        return copy(dest, 0, 0, length);
    }
    
    public T[] copy(T[] dest) {
        return copy(dest, dest.length);
    }
}
