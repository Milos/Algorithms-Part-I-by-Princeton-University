import java.util.Iterator;
import java.util.NoSuchElementException;

/***************************************************************
 * 
 * A double-ended queue or deque (pronounced "deck") is a generalization of a 
 * stack and a queue that supports adding and removing items from either the 
 * front or the back of the data structure
 * 
 ***************************************************************/

/**
 *
 * @author milos
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size; // number of items

    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        if (oldFirst != null) {
            oldFirst.previous = first;
        }
        if (last == null) {
            last = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (isEmpty()) {
            addFirst(item);
            return;
        }
        
        Node oldLast = last;
        last = new Node();
        last.item = item;
        oldLast.next = last;
        last.previous = oldLast;
        size++;

    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        if (size == 1) {
            first = last = null;
//            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (size == 1) {
            Item item = removeFirst();
            return item;
        } else {
            Item item = last.item;
            last = last.previous;
            last.next = null;
            size--;
            return item;
        }
    }

    // return an iterator over items in order from front to end    
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class Node {

        Item item;
        Node previous;
        Node next;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
//        Deque<String> deque = new Deque<>();
//        deque.addLast("0");
//        deque.addFirst("1");
//        
//        
//        
//        for (String node : deque) {
//            System.out.print(node + "-> ");
//        }
//        
//        System.out.print("null,  ");
//        System.out.println("Size: " + deque.size);
//        System.out.println("deque.removeLast(): " + deque.removeLast());
//        System.out.println("deque.removeLast(): " + deque.removeLast());
//
//        
//        for (String node : deque) {
//            System.out.print(node + "-> ");
//        }
//        System.out.print("null,  ");
//        System.out.println("Size: " + deque.size);
//        
          Deque<Integer> deque = new Deque<>();
          deque.addLast(1);
          deque.addFirst(2);
          deque.addLast(3);
          deque.addFirst(4);
          deque.addLast(5);
         System.out.println("deque.size = " + deque.size); 
        for (int node : deque) {
            System.out.print(node + "-> ");
        }
        System.out.println();  
        System.out.println("deque.removeFirst(): " + deque.removeFirst());
        System.out.println("deque.removeFirst(): " + deque.removeFirst());
        System.out.println("deque.removeFirst(): " + deque.removeFirst());
        System.out.println("deque.size = " + deque.size);
    }
}
