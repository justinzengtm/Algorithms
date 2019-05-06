package directeddfs;

import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {
    private Node first; //链表头结点
    private int N;

    //链表结点的数据结构
    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;// or N == 0
    }

    public int size() {
        return N;
    }

    public void add(Item item) {
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }
}

