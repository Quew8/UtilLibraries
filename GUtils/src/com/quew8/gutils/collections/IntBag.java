package com.quew8.gutils.collections;

import java.util.Arrays;

/**
 *
 * @author Quew8
 * 
 */
public class IntBag extends AbstractCollection {
    private int[] data;
    private int size = 0;
    
    /**
     * 
     * @param data 
     */
    public IntBag(int[] data) {
        this.data = data;
    }
    
    /**
     * 
     * @param capacity 
     */
    public IntBag(int capacity) {
    	this(new int[capacity]);
    }
    
    /**
     * 
     */
    public IntBag() {
        this(10);
    }
    
    /**
     * Removes the element at the specified position in this Bag.
     *
     * @param index the index of element to be removed
     * @return element that was removed from the Bag
     */
    public int remove(int index) {
        int t = data[index];
        data[index] = data[--size];
        //data[size] = null;
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
    public boolean removeElement(int t) {
        for(int i = 0; i < size; i++) {
            if(t == data[i]) {
                data[i] = data[--size];
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
    public boolean removeAll(IntBag bag) {
        boolean modified = false;
        for (int i = 0; i < bag.size(); i++) {
            int o1 = bag.get(i);
            for (int j = 0; j < size; j++) {
                int o2 = data[j];
                if(o1 == o2) {
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
    public int get(int index) {
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
    public void add(int t) {
        if(size == data.length) {
            expand();
        }
        data[size++] = t;
    }
    
    public void addAll(int[] ta, int offset, int length) {
        if(length + size < data.length) {
            expand(length + size);
        }
        System.arraycopy(ta, offset, data, size, length);
        size += length;
    }
    
    public void addAll(int[] ta) {
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
    
    public int[] getBackingArray() {
    	return data;
    }
    
    private void expand() {
        expand((( data.length * 3 ) / 2 ) + 1);
    }
    
    private void expand(int newCapacity) {
        this.data = Arrays.copyOf(data, newCapacity);
    }
    
    public void clear() {
        size = 0;
    }
    
    public int[] copy(int[] dest, int soff, int doff, int length) {
        System.arraycopy(data, soff, dest, doff, length);
        return dest;
    }
    
    public int[] copy(int[] dest, int length) {
        return copy(dest, 0, 0, length);
    }
    
    public int[] copy(int[] dest) {
        return copy(dest, dest.length);
    }
}
