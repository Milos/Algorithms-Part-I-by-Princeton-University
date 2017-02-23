import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/****************************************************************
 * 
 * Takes a command-line integer k; reads in a sequence of strings from 
 * standard input using StdIn.readString(); and prints exactly k of them, 
 * uniformly at random
 * Prints each item from the sequence at most once
 * Assuming that 0 ≤ k ≤ n, where n is the number of string on standard input
 * 
 ****************************************************************/

/**
 *
 * @author milos
 */
public class Permutation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        
        while (!StdIn.isEmpty()) {            
            String s = StdIn.readString();
            queue.enqueue(s);
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue()); 
        }
    }
    
}
