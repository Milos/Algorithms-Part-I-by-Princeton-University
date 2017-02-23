package percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/************************************************************
 * 
 * Performs a series of computational experiments
 * Monte Carlo simulation
 * 
 ************************************************************/

/**
 *
 * @author milos
 */
public class PercolationStats {

    private int numberOfExperiments;
    private Percolation p;
    private double[] fractions;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        numberOfExperiments = trials;
        fractions = new double[trials];
        System.out.println(numberOfExperiments);
        
         
        for (int experiment = 0; experiment < 5; experiment++) {
            p = new Percolation(n);
            int openedSites = 0;
            
            while (!p.percolates()) {
                int i = StdRandom.uniform(1, n + 1);
                int j = StdRandom.uniform(1, n + 1);
                
                if (!p.isOpen(i, j)) {
                    p.open(i, j);
                    openedSites++;
                }
            }
            // percolation treshold 
            double fraction = (double) openedSites / (n * n);
            fractions[experiment] = fraction;
        }

    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(numberOfExperiments);
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(numberOfExperiments);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        String confidence = "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]";
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " + confidence);
    }
}
