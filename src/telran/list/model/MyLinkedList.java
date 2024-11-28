package telran.list.model;

import telran.list.interfaces.IList;

import java.util.Iterator;

public class MyLinkedList<E> implements IList<E> {
    private Node<E> first;
    private Node<E> last;
    private int size;

    @Override
    public int size() {
        return size;
    }
//O(n)
    @Override
    public boolean add(int index, E element) {
        if (index == size) {
            Node<E> newNode = new Node<>(last,element,null);
            if (last != null) {
                last.next = newNode;
            } else {
                first = newNode;
            }
            last = newNode;
        } else {
            Node<E> node = getNodeByIndex(index);
            Node<E> newNode = new Node<>(node.prev,element,node);
            node.prev = newNode;
            if (index != 0) {
                newNode.prev.next = newNode;
            } else {
                first = newNode;
            }
        }
        size++;
        return true;
    }
//O(n)
    private Node<E> getNodeByIndex(int index) {
        checkIndex(index);
        Node<E> curr;
        if (index < size / 2) {
            curr = first;
            for (int i = 0; i < index; i++) {
                curr = curr.next;
            }
        } else {
            curr = last;
            for (int i = size - 1; i > index; i--) {
                curr = curr.prev;
            }
        }
        return curr;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index);
        }
    }
//O(n)
    @Override
    public E get(int index) {
        return getNodeByIndex(index).payload;
    }
//O(n)
    @Override
    public int indexOf(Object o) {
        int index = 0;
        if (o != null) {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (o.equals(node.payload)) {
                    return index;
                }
            }
            return -1;
        } else {
            for (Node<E> node = first; node != null; node = node.next, index++) {
                if (node.payload == null) {
                    return index;
                }
            }
            return -1;
        }
    }
    //O(n)
    @Override
    public int lastIndexOf(Object o) {
        int index = size - 1;
        if (o != null) {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (o.equals(node.payload)) {
                    return index;
                }
            }
            return -1;
        } else {
            for (Node<E> node = last; node != null; node = node.prev, index--) {
                if (node.payload == null) {
                    return index;
                }
            }
            return -1;
        }
    }
    //O(n)
    @Override
    public E remove(int index) {
        checkIndex(index);
        Node<E> node = getNodeByIndex(index);
        if (index != 0) {
            node.prev.next = node.next;
        } else {
            first = first.next;
        }
        if (index != size - 1) {
            node.next.prev = node.prev;
        } else {
            last = last.prev;
        }
        node.prev = node.next = null;
        size--;
        return node.payload;
    }
    //O(n)
    @Override
    public E set(int index, E element) {
        Node<E> node = getNodeByIndex(index);
        E res = node.payload;
        node.payload = element;
        return res;
    }

    @Override
    public void clear() {
        //If we want to save the list, O(n)
//        for (Node<E> node = first; node != null; node = node.next) {
//            node.payload = null;
//        }
//        size = 0;

        //Else, O(1)
        first = null;
        last = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int i = 0;
            Node<E> curr = first;
            @Override
            public boolean hasNext() {
                return i < size;
            }

            @Override
            public E next() {
                E res = null;
                if (curr != null) {
                    res = curr.payload;
                    curr = curr.next;
                }
                i++;
                return res;
            }
        };
    }
    private static class Node <E>{
        E payload;
        Node<E> prev;
        Node<E> next;

        public Node(Node<E> prev, E payload, Node<E> next) {
            this.prev = prev;
            this.payload = payload;
            this.next = next;
        }
    }
}
