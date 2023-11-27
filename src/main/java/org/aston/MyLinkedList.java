package org.aston;

import java.util.Comparator;

/**
 * @param <T> stored data type
 *            first - first node in the linked list
 *            last - last node in the linked list
 *            size - size linked list
 */
public class MyLinkedList<T> implements MyList<T> {
    Node<T> first;
    Node<T> last;
    int size;

    @Override
    public void add(T element) {
        final Node<T> newNode = new Node<>(null, last, element);
        if (last == null) {
            first = newNode;
            last = newNode;
        } else {
            last.next = newNode;
            last = newNode;
        }
        size++;
    }

    @Override
    public void add(T element, int index) {
        checkIndex(index);
        Node<T> tmp = getNodeByIndex(index);
        Node<T> newNode = new Node<>(tmp, tmp.previous, element);
        tmp.previous.next = newNode;
        tmp.previous = newNode;
        size++;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getNodeByIndex(index).element;
    }

    private Node<T> getNodeByIndex(int index) {
        checkIndex(index);
        Node<T> tmp = first;
        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    @Override
    public void delete(int index) {
        checkIndex(index);
        Node<T> tmp = getNodeByIndex(index - 1);

        tmp.element = null;
        tmp.previous.next = tmp.next;
        tmp.next.previous = tmp.previous;
        size--;
    }

    @Override
    public void deleteAll() {
        for (Node<T> node = first; node.next != null; ) {
            Node<T> next = node.next;
            node.element = null;
            node.next = null;
            node.previous = null;
            node = next;
        }
        size = 0;
    }

    @Override
    public void sort(Comparator<T> comparator) {
        if (size > 1) {
            return;
        }
        for (Node<T> a = first; a != null; a = a.next) {
            for (Node<T> b = a.next; b != null; b = b.next) {
                if (comparator.compare(a.element, b.element) > 0) {
                    T tmp = a.element;
                    a.element = b.element;
                    b.element = tmp;
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

    private static class Node<T> {
        Node<T> next;
        Node<T> previous;
        T element;

        public Node(Node<T> next, Node<T> previous, T element) {
            this.next = next;
            this.previous = previous;
            this.element = element;
        }
    }
}
