package org.aston;

import java.util.*;

/**
 * @param <E> stored data type
 *            capacity - array capacity
 *            elementsArr - array data
 *            size - number of elements in the array
 */
public class MyArrayList<E> implements MyList<E> {
    private static final int CAPACITY = 10;

    private Object[] elementsArr;

    private int size;

    public MyArrayList() {
        elementsArr = new Object[CAPACITY];
    }

    public MyArrayList(int newCapacity) {
        if (newCapacity < 0) {
            throw new IllegalArgumentException("The size of MyArrayList cannot be negative");
        }
        elementsArr = new Object[newCapacity];
    }

    @Override
    public void add(E element) {
        if (size == elementsArr.length) {
            grow();
        }
        elementsArr[size++] = element;
    }

    @Override
    public void add(E element, int index) {
        checkIndex(index);
        if (size + 1 == elementsArr.length) {
            grow();
        }
        if (size - index >= 0) {
            System.arraycopy(elementsArr, index, elementsArr, index + 1, size - index);
        }
        elementsArr[index] = element;
        size++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) elementsArr[index];
    }

    @Override
    public void delete(int index) {
        checkIndex(index);

        for (int i = index; i < size - 1; i++) {
            elementsArr[i] = elementsArr[i + 1];
        }

        size--;
    }

    @Override
    public void deleteAll() {
        for (int i = 0; i < size; i++) {
            elementsArr[i] = null;
        }
        size = 0;
    }


    @Override
    public void sort(Comparator<E> comparator) {
        if (size == 0) {
            return;
        }
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (comparator.compare((E) elementsArr[i], (E) elementsArr[j]) > 0) {
                    E tmp = (E) elementsArr[i];
                    elementsArr[i] = elementsArr[j];
                    elementsArr[j] = tmp;
                }
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    private void checkIndex(int index) {
        if (index > size || index < 0) {
            throw new IllegalArgumentException("the index cannot be smaller than the size of the array");
        }
    }

    private void grow() {
        elementsArr = Arrays.copyOf(elementsArr, size + size / 2);
    }
}
