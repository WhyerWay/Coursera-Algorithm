import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node begin, end;
    private int sz;
    private class Node {
        Item item;
        Node next, prev;
    }
    public Deque() {
        begin = new Node();
        end = new Node();
        begin.next = end;
        end.prev = begin;
    }
    public boolean isEmpty() {
        return (begin.next == end);
    }
    public int size() {
        return sz;
    }
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node add = new Node();
        add.item = item;
        add.next = begin.next;
        add.prev = begin;
        begin.next.prev = add;
        begin.next = add;
        sz++;
    }
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node add = new Node();
        add.item = item;
        add.prev = end.prev;
        add.next = end;
        end.prev.next = add;
        end.prev = add;
        sz++;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = begin.next.item;
        begin.next.next.prev = begin;
        begin.next = begin.next.next;
        sz--;
        return res;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item res = end.prev.item;
        end.prev.prev.next = end;
        end.prev = end.prev.prev;
        sz--;
        return res;
    }
    private class DequeIterator implements Iterator<Item> {
        private Node it;
        public DequeIterator() {
            it = begin;
        }
        public boolean hasNext() {
            return (it.next != end);
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            it = it.next;
            return it.item;
        }
    }
//    static void test (Deque<Integer> d) {
//        for (int it : d) {
//            StdOut.print(it + " ");
//        }
//        StdOut.println();
//    }
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }
    public static void main(String[] args) {
    }

}