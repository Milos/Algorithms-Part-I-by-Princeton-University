import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/****************************************************************
 * 
 * A randomized queue, similar to a stack or queue, except that the item
 * removed is chosen uniformly at random from items in the data structure
 * 
****************************************************************/

/**
 *
 * @author milos
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        n = 0;
        first = 0;
        last = 0;
    }

    /**
     * Is this queue empty?
     *
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in this queue.
     *
     * @return the number of items in this queue
     */
    public int size() {
        return n;
    }

    // resize the underlying array
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[(first + i) % q.length];
        }
        q = temp;
        first = 0;
        last = n;
    }

    /**
     * Adds the item to this queue.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        // double size of array if necessary and recopy to front of array
        if (n == q.length) {
            resize(2 * q.length);   // double size of array if necessary
        }
        q[last++] = item;                        // add item
        if (last == q.length) {
            last = 0;          // wrap-around
        }
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        
        int randomIndex = StdRandom.uniform(n);

        Item item = q[randomIndex];
        q[randomIndex] = q[--n];
        q[n] = null;
        last--;
        
        // shrink size of array if necessary
        if (n > 0 && n == q.length / 4) {
            resize(q.length / 2);
        }
        return item;
    }

    // return (but do not remove) a random item    
    public Item sample() {
        int randomIndex = StdRandom.uniform(n);
        return q[randomIndex];

    }

    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] temp;
        public RandomIterator() {
            temp = (Item[]) new Object[n];
            
            for (int j = 0; j < n; j++) {
                temp[j] = q[(first + j) % q.length];
            }
            StdRandom.shuffle(temp);            
        }
              
        public boolean hasNext() { return i < n; }

        public void remove() { throw new UnsupportedOperationException(); }
        
        public Item next() {
            
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            
            Item item = temp[(i + first) % temp.length];
            i++;            
            return item;
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
                
        Iterator<Integer> it = queue.iterator();
        
        while (it.hasNext()) {
            Integer next = it.next();
            System.out.println("next: " + next);            
        }
        
        System.out.println("queue.dequeue() " + queue.dequeue());
//        System.out.println("queue.dequeue() " + queue.dequeue());
//        System.out.println("queue.dequeue() " + queue.dequeue());
//        System.out.println("queue.dequeue() " + queue.dequeue());
//        System.out.println("queue.dequeue() " + queue.dequeue());
        it = queue.iterator();
        while (it.hasNext()) {
            Integer next = it.next();
            System.out.println("next: " + next);            
        }        
    }
}
