import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] ar;
    private int end;

    public RandomizedQueue() {
        ar = (Item[]) new Object[2];
        end = 0;
    }
    public boolean isEmpty() {
        return end == 0;
    }
    public int size() {
        return end;
    }

    private void resize(int sz) {
        if (sz < 2) {
            return;
        }
        Item[] copy = (Item[]) new Object[sz];
        for (int i = 0; i < end; i++) {
            copy[i] = ar[i];
        }
        ar = copy;
    }
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (end == ar.length) {
            resize(2 * ar.length);
        }
        ar[end++] = item;
    }
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (end == ar.length / 4) {
            resize(ar.length / 2);
        }
        int i = StdRandom.uniform(end);
        Item res =  ar[i];
        ar[i] = ar[end - 1];
        ar[end - 1] = null;
        end--;
        return res;
    }
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int i = StdRandom.uniform(end);
        return ar[i];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;
        private int count;
        private int[] order;
        RandomizedQueueIterator() {
            i = 0;
//            StdRandom.shuffle(ar, 0, Math.max(end - 1, 0));
            count = end;
            order = new int[end];
            for (int j = 0; j < end; j++) {
                order[j] = j;
            }
            StdRandom.shuffle(order);
        }
        public boolean hasNext() {
            return count > 0;
        }
        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            count--;
            return ar[order[i++]];
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
//    static void test (RandomizedQueue<Integer> d) {
//        for (int it : d) {
//            StdOut.print(it + " ");
//        }
//        StdOut.println();
//    }

    // unit testing (required)
    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }

}
