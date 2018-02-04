import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] results;
	private int len;
	private double mean;
	private double stddev;
	
	public PercolationStats(int n, int trials) {
	   // perform trials independent experiments on an n-by-n grid
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
		results = new double[trials];
		len = results.length;
		double total = n*n;
		for(int i=0; i<trials; i++) {
			Percolation test = new Percolation(n);
			while(test.percolates() != true) {
				test.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);			   
			}
			results[i] = test.numberOfOpenSites()/total;
		}
		stddev = StdStats.stddev(results);
		mean =StdStats.mean(results);
	}

	public double mean() {
		// sample mean of percolation threshold
		return 	mean;
	}
	public double stddev() {
		// sample standard deviation of percolation threshold

		return stddev;
	}
    public double confidenceLo() {
    	// low  endpoint of 95% confidence interval

    	return mean - 1.96*stddev/Math.sqrt(len);
    }
    public double confidenceHi() {
    	// high endpoint of 95% confidence interval
    	return mean + 1.96*stddev/Math.sqrt(len);
    }

	public static void main(String[] args) {
//		// test client (described below)
//		PercolationStats test = new PercolationStats(10,10);
//		System.out.println(test.mean());
//		System.out.println(test.stddev());
//		System.out.println(test.confidenceHi());
//		System.out.println(test.confidenceLo());
	}
}