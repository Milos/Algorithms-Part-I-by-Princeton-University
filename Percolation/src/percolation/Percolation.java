package percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/****************************************************************
 * 
 * Percolation system Model
 * 
 ****************************************************************/

/**
 *
 * @author milos
 */
public class Percolation {

    private boolean[][] grid;
    private int size;
    private int numberOfOpenSites;
    private WeightedQuickUnionUF wqf;
    private final int topVirtualSite = 0;
    private int bottomVirtualSite;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottomVirtualSite = size * size + 1;
        wqf = new WeightedQuickUnionUF(size * size + 2);        
        grid = new boolean[size][size];

    }

    public void open(int row, int col) {

        //1 <= row <= n, 1 <= col<= n 
        if (!(0 < row && row <= size) || !(0 < col && col <= size)) {
            throw new IndexOutOfBoundsException("Wrong input my friend");
        }
        grid[row - 1][col - 1] = true; //because grid starts at (1,1)
                
        while (!isOpen(row, col)) {
            numberOfOpenSites++;
            //If site is in the first row, merge the site with top virtual site.        
            if (row == 1) {
                wqf.union(getWQFIndex(row, col), topVirtualSite);
            }
            //If site is in the last row, connect the site with bottom virtual site
            if (row == size) {
                wqf.union(getWQFIndex(row, col), bottomVirtualSite);
            }

            if (row > 1 && isOpen(row - 1, col)) {
                wqf.union(getWQFIndex(row, col), getWQFIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                wqf.union(getWQFIndex(row, col), getWQFIndex(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                wqf.union(getWQFIndex(row, col), getWQFIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                wqf.union(getWQFIndex(row, col), getWQFIndex(row, col + 1));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (!(0 < row && row <= size) || !(0 < col && col <= size)) {
            throw new IndexOutOfBoundsException("Wrong input my friend");
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        if (!(0 < row && row <= size) || !(0 < col && col <= size)) {
            throw new IndexOutOfBoundsException("Wrong input my friend");
        }
        return wqf.connected(topVirtualSite, getWQFIndex(row, col));
    }

    public boolean percolates() {
        return wqf.connected(topVirtualSite, bottomVirtualSite);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    private int getWQFIndex(int row, int col) {
        return size * (row - 1) + col;
    }

    public static void main(String[] args) {
    }

}
