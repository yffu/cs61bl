import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class Tray {

	// The position of the blocks
	// 1st Index => Row
	// 2nd Index ==> Column
	// 3rd Index , Value ==> Reference to cell number of either the head or tail
	// of the block.
	private int[][] blockPositions;

	// Width and height of tray
	private int width, height;

	// Pointer to previous tray
	private Tray prevTray;

	// Position of all the white spaces
	private boolean[][] occupiedSpace;

	public Tray(int height, int width, ArrayList<int[]> blocks) throws Exception {
		this.width = width; this.height = height;
		blockPositions = new int[height][width];
		occupiedSpace = new boolean[height][width];
		// Initialize block positions
		for (int[] block : blocks) {
			int x1 = block[1];
			int y1 = block[0];
			int x2 = block[3];
			int y2 = block[2];
			int headCellPosition = y1*height+x1;
			int tailCellPosition = y2*height+x2;

			// Set all elements of the block to the head cell position
			for (int y = y1; y <= y2; y++) {
				for (int x = x1; x <= x2; x++) {
					if (occupiedSpace[x][y]) {
						throw new Exception("cell (" + x + "," + y + ") is already taken");
					} else {
						occupiedSpace[x][y] = true;
						blockPositions[x][y] = headCellPosition;
					}
				}
			}

			// Overwrite the head node's pointer to the tail
			blockPositions[x1][y1] = tailCellPosition;
		}
	}	


	// Mutates the tray by making a move
	public void makeMove(int[] move) {

	}

	// Returns true if the given coordinates are white space
	public boolean isOccupied(int row, int col) {
		return occupiedSpace[col][row];
	}

	public Tray previous() {
		return prevTray;
	}

	public Iterator<Tray> getMoves() {
		// to be implemented
		return null;
	}

	public boolean equals() {
		// to be implemented
		return false;
	}

	public boolean goalCheck(ArrayList<int[]> goals){
		Iterator<int[]> iter= goals.iterator();
		boolean flag;
		while (iter.hasNext()){
			flag=false;
			int[] nextGoal= iter.next(); //brings up the next goal to be checked

			for(int a=0; a<blockPositions.length;a++){
				if(blockPositions[a]==nextGoal){
					flag=true; //if we have a match, stop checking this goal
					break;
				}
			}
			if(!flag){return false;}// if we didn't have a match, we didn't meet all of our goals

		}
		return true; // if we found matches for all our goals, return true

		//If we change blockPositions to an ArrayList,we don't have to check one by one with the for loop.
		//In each while loop, check to see if blockPositions has nextGoal with the contains() method; if it doesn't return false, if it does
		// continue, and keep the return true after the while loop.
	}

	private class TrayIterator implements Iterator<Tray> {
		// to be implemented
		private Tray previous;
		private Stack<Tray> moves;

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public Tray next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub	
		}
	}
}


