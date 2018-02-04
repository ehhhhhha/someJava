import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	   private WeightedQuickUnionUF sites;
	   private WeightedQuickUnionUF sites2;
	   private boolean grid[]; // n-by-n grid, counting from 0. 
	   private int len; // length of grid.
	   private int opens; // how many sites opened.

	   public Percolation(int n) {
		   // create n-by-n grid, with all sites blocked
		   if (n <= 0) throw new IllegalArgumentException();
		   sites = new WeightedQuickUnionUF(n*n + 2); // n*n sites on the grid plus 2 virtual sites.
		   sites2 = new WeightedQuickUnionUF(n*n + 1);
		   grid = new boolean[n*n]; //use boolean array to display the status of site: open or close.
		   len = n;
		   for(int i = 0; i<n*n; i++) {
			   grid[i] = false; // close all the sites on the grid.
		   }
	   } 
	   
	   public void open(int row, int col) {
		   // open site (row, col) if it is not open already
		   if (row <= 0 || col <= 0 || row > len || col > len) throw new IllegalArgumentException();
		   

		   int index = (row-1)*len + col -1; // I admit that this way of transforming indices is ugly.
		   if(grid[index]== true) {
			   return; // 'true' means this site is open, no need to open again so we quit.
		   }
		   if (len ==1 ) {
			   grid[0] = true;
			   sites.union(0, 2);
			   sites2.union(0, 1);
			   opens = 1;			   
			   return;
		   }

		   grid[index] = true;       // mark this site as open so we won't process it again.		   
		   opens++;	
		   
		   // this is the most ugly part of my code, I hate this. But anyway it works.

		   if(row == 1) {
			   if(col != len && grid[index+1] == true) {				   
				    sites.union(index + 2, index + 1);
				    sites2.union(index + 2, index + 1);
			   } 
			   if(col != 1 && grid[index-1] == true ) {
				    sites.union(index, index + 1);
				    sites2.union(index, index + 1);
			   }
			   if(grid[index+ len] == true) {
				    sites.union(index+1, index + len+1);
				    sites2.union(index+1, index + len+1);
			   }
			   
			   sites.union(col , 0); 
			   sites2.union(col , 0);
		   }else if(row == len) {
			   if(col != len && grid[index+1] == true) {
				    sites.union(index+1+1, index + 1);
				    sites2.union(index+1+1, index + 1);
			   } 
			   if(col != 1 && grid[index-1] == true) {
				    sites.union(index, index + 1);
				    sites2.union(index, index + 1);
			   }
			   if(grid[index- len] == true) {
				    sites.union(index+1, index - len+1);
				    sites2.union(index+1, index - len+1);
			   }

			   sites.union(index+1, len*len + 1);
		   }else if(col == 1) {
			   if(grid[index+1] == true) {
				    sites.union(index+1+1, index + 1);
				    sites2.union(index+1+1, index + 1);
			   } 
			   if(row != len && grid[index+ len] == true ) {
				    sites.union(index+1, index + len+1);
				    sites2.union(index+1, index + len+1);
			   }
			   if(row != 1 && grid[index- len] == true) {
				    sites.union(index+1, index - len+1);
				    sites2.union(index+1, index - len+1);
			   }
		   }else if(col == len) {
			   if(grid[index-1] == true) {
				    sites.union(index, index + 1);
				    sites2.union(index, index + 1);
				    
			   } 
			   if(row != len && grid[index+ len] == true) {
				    sites.union(index+1, index + len+1);
				    sites2.union(index+1, index + len+1);
			   }
			   if(row != 1 && grid[index- len] == true) {
				    sites.union(index+1, index - len+1);
				    sites2.union(index+1, index - len+1);
			   }
		   }else {
			   if(grid[index-1] == true) {
				    sites.union(index, index + 1);
				    sites2.union(index, index + 1);
			   }
			   if(grid[index+1] == true) {
				    sites.union(index+1, index + 1+1);
				    sites2.union(index+1, index + 1+1);
			   }
			   if(grid[index- len] == true) {
				    sites.union(index+1, index - len+1);
				    sites2.union(index+1, index - len+1);
			   }
			   if(grid[index+ len] == true) {
				   sites.union(index+1, index + len+1);
				   sites2.union(index+1, index + len+1);
			   }
			   
		   }
		   
	   }
	   
	   public boolean isOpen(int row, int col) {
		   // is site (row, col) open?
		   if (row <= 0 || col <= 0 || row > len || col > len) throw new IllegalArgumentException();
		   if(grid[(row-1)*len + col -1] == true) {  // this is why I use boolean arrays.
			   return true;
		   }else{
			   return false;
		   }
	   }
	   
	   public boolean isFull(int row, int col) {
		   // is site (row, col) full? 	
		   if (row <= 0 || col <= 0 || row > len || col > len) throw new IllegalArgumentException();
		   int index = (row-1)*len + col -1;
		   
		   if(sites2.connected(index + 1, 0)) {   
			   return true;
		   }else {
			   return false;
		   }


	   }
	   
	   public int numberOfOpenSites() {
		   // number of open sites
		   return opens;
	   }
	   
	   public boolean percolates() {
		   // does the system percolate?		   
		   if(sites.connected(0, len*len+1)) {   // check the connection between the top and bottom virtual points.
			   return true;
		   }else {
			   return false;
		   }
	   }

	   public static void main(String[] args) {
		   
	   }	   

	}