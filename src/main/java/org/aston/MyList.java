package org.aston;

import java.util.Comparator;

public interface MyList<E> {
    void add(E element);

    void add(E element, int index);

    E get(int index);

    void delete(int index);

    void deleteAll();

    void sort(Comparator<E> comparator);

    int size();

}
