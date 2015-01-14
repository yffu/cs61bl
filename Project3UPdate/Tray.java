import java.util.ArrayList;
import java.util.LinkedList;
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

	// Queue of all the integer representation of all the white spaces, used to iterate through
	// the white spaces and generate the moves around each whitespace
	private LinkedList<int[]> whiteSpace;

	private int hashCode = -1;

	public Tray(int height, int width, ArrayList<int[]> blocks) throws Exception {
		this.width = width; this.height = height;
		blockPositions = new int[height][width];
		// Initialize block positions
		for (int[] block : blocks) {
			int x1 = block[1];  // start column
			int y1 = block[0];  // start row
			int x2 = block[3];  // end column
			int y2 = block[2];  // end row
			int headCellPosition = y1*width+x1+1;
			int tailCellPosition = y2*width+x2+1;

			// Set all elements of the block to the head cell position
			for (int y = y1; y <= y2; y++) {
				for (int x = x1; x <= x2; x++) {
					if (isOccupied(y, x)) {
						throw new Exception("cell (" + x + "," + y + ") is already taken");
					} else {
						blockPositions[y][x] = headCellPosition;
					}
				}
			}

			// Overwrite the head node's pointer to the tail
			blockPositions[y1][x1] = tailCellPosition;

			// for debugging purposes;
			// printRepresentation();
		}
		/* goes through the matrix of all spaces, and collects all white spaces into an arrayList 
		 holding their representation in linear index */

		// we can do a priority queue or something like that to make the search more efficient
		// i.e. if there is only a single block within a big swath of white space, go for the whitespace near the block first
		whiteSpace = new LinkedList<int[]>();
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				if (blockPositions[i][j]==0) {
					// for debugging
					System.out.print(i+""+j+" ");
					int[] wsLocation = {i,j};
					whiteSpace.add(wsLocation);
				}

			}

		}
		System.out.println();
		// after the list of whitespace is created, take out one and find possible moves around space

	}	

	public Tray(int h, int w, int[][] representation) {
		height = h;
		width = w;
		blockPositions = representation;
		whiteSpace = new LinkedList<int[]>();
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				if (blockPositions[i][j]==0) {
					// for debugging
					System.out.print(i+""+j+" ");
					int[] wsLocation = {i,j};
					whiteSpace.add(wsLocation);			}			
			}
		}
		System.out.println();
	}

	// debugging aid, prints tray representation
	public void printRepresentation() {
		for (int i = 0; i < height; i ++) {
			for (int j = 0; j < width; j++) {
				System.out.print(blockPositions[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	public boolean equals(Tray o) {
		// Check dimensions
		if (height != o.height || width != o.width)
			return false;
		// Compare every element for equality
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				if (blockPositions[row][col] != o.blockPositions[row][col])
					return false;
			}
		}
		return true;
	}

	// Pick a random number column in each row
	public boolean quickEquals(Tray o) {
		for (int row = 0; row < height; row++) {
			int col = (int)((Math.random()*width)-1);
			if (blockPositions[row][col] != o.blockPositions[row][col])
				return false;
		}
		return true;
	}

	// Hash Code
	public int hashCode() {
		if (hashCode != -1)
			return hashCode;
		else
			return generateHash();
	}

	// If a hash code has not been generated, then generate one	// This prevents calculating a hash code multiple times
	public int generateHash() {
		int sum = 0;
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				sum += blockPositions[row][col];
			}
		}
		hashCode = sum;
		return hashCode;
	}


	// Mutates the tray by making a move
	public void makeMove(int[] move) {


	}

	public Tray makeNextTray(int[] move) {
		// direction to move the block : 
		// -shiftX --> move right; +shiftX --> move left; -shiftY --> move down; +shiftY --> move up
		int shiftX = move[5];
		int shiftY = move[4];
		// start coordinates of the block to be moved
		int startY = move[0]-shiftY;
		int startX = move[1]-shiftX;
		// end coordinates of the block to be moved
		int endY = move[2]-shiftY;
		int endX = move[3]-shiftX;
		// integer value for the head and tail pointer
		int headCellPosition = linearIndex(startY, startX);
		int tailCellPosition = linearIndex(endY, endX);
		// create a copy of the current matrix representation of the board
		int[][] newBoard = blockPositions.clone();
		for (int i = startY; i <= endY; i++) {
			for (int j = startX; j <= endX; j++) {
				newBoard[i][j] = headCellPosition;
			}
		}
		// Overwrite the head node's pointer to the tail
		newBoard[startY][startX] = tailCellPosition;
		// this part restores the values of the new white spaces to 0
		if (shiftY != 0 ) {
			int emptyRow;
			if (shiftY == -1) 
				emptyRow = move[0];
			else if (shiftY == 1)
				emptyRow = move[2];
			else {
				throw new IllegalStateException("wrong number of y shifts : " + shiftY);
			}
			for (int i = startX; i <= endX; i++)
				newBoard[emptyRow][i] = 0;
		}
		else if (shiftX != 0) {
			int emptyCol;
			if (shiftX == -1) 
				emptyCol = move[1];
			else if (shiftX == 1)
				emptyCol = move[3];
			else {
				throw new IllegalStateException("wrong number of x shifts : " + shiftX);
			}
			for (int i = startY; i <= endY; i++)
				newBoard[i][emptyCol] = 0;
		}
		else {
			throw new IllegalStateException("wrong shift values for a block");
		}

		return new Tray(height, width, newBoard);

	}

	// Returns true if the given coordinates are white space
	public boolean isOccupied(int row, int col) {
		return blockPositions[row][col] != 0;
	}

	public Tray previous() {
		return prevTray;
	}

	// Returns the pointer of the given cell
	public int getPointer(int row, int col) {
		return blockPositions[row][col];
	}

	public Iterator<int[]> getMoves() {
		// to be implemented
		return new TrayIterator();
	}

	public boolean equals() {
		// to be implemented
		return false;
	}

	// converts from 2D matrix indices to linear index
	public int linearIndex(int y, int x) {
		return y*width + x+1;
	}

	// and vice-versa

	public int[] matrixIndex(int index) {
		int[] mIndex = {((index-1)/width),((index % width)-1)};
		return mIndex;
	}

	public boolean goalCheck(ArrayList<int[]> goals){
		Iterator<int[]> iter= goals.iterator();
		while (iter.hasNext()){
			int[] currentgoal=iter.next();
			if(!(blockPositions[currentgoal[0]][currentgoal[1]]==linearIndex(currentgoal[2],currentgoal[3]) && // the two corners should point to each other
					blockPositions[currentgoal[2]][currentgoal[3]]==linearIndex(currentgoal[0],currentgoal[1]))){
				return false;
			}
		}
		return true; // if we found matches for all our goals, return true

	}

	private class TrayIterator implements Iterator<int[]> {
		// to be implemented
		private Tray previous;
		private Stack<int[]> moves;
		private Iterator<int[]> wsIterator;

		public TrayIterator() {

			// creates an iterator that returns the y,x coordinates of white-spaces from the upper-left to the lower-right
			wsIterator = whiteSpace.iterator();
			moves = new Stack<int[]>();
			while (wsIterator.hasNext()) {
				populateMoveStack(wsIterator.next());
				// while more white spaces can be provided by the iterator,
				// use each white space to find possible move around it, 
				// and push the moves onto the move stack using populateMoveStack()
			}
		}

		public void populateMoveStack(int[] next) {
			// gets white space location and populates move stack
			int wsY = next[0];
			int wsX = next[1];
			int rowSize = 0;
			int colSize = 0;

			// find possible up/down moves into the whitespace

			// first check if current white space is the head of a row,
			// i.e. it is not preceded by a whitespace to its left
			// if true, search for a contiguous column of whitespace
			// then search around the whitespace row for possible up/down moves

			// the following clause checks if whitespace is either at the beginning of a row,
			// or its neighbor to the left is occupied
			if (wsX == 0 || isOccupied(wsY, wsX-1)) {
				// if true, whitespace is at head of a whitespace row, we start search for contiguous whitespaces
				// to its right, keeping track of the width of the row using rowSize;
				rowSize++;
				while (wsX+rowSize < width && !isOccupied(wsY, wsX+rowSize))
					rowSize ++;
			}

			// similarly, this clause checks if the white space is at the head of a column of contiguous white spaces
			// i.e. if there is no whitespace on top of it, or is at row 0
			// this checks for a contiguous column of whitespace, whose neighboring blocks are allowed to make left/right moves.

			if (wsY == 0 || isOccupied(wsY-1, wsX)) {
				colSize++;
				while (wsY+colSize < height && !isOccupied(wsY+colSize, wsX))
					colSize ++;
			}

			// if rowSize =/= 0, we consider moving blocks from the top/bottom
			// if colSize =/= 0, consider moving blocks from the left/right
			// otherwise do nothing (the white space has already been considered as a part of a row/
			// column as their headers were processed.

			if (rowSize != 0) {
				// check all spaces above and below the row of whitespace, see if any blocks can be accomodated
				// in the space

				if (wsY > 0) {
					tbMoves(wsY, wsX, rowSize, (byte) -1);
				}

				if (wsY < height-1) {
					tbMoves(wsY, wsX, rowSize, (byte) 1);
				}
			}

			if (colSize != 0){
				// check all spaces left and right of the column of whitespace, see if any blocks can be accomodated
				// in the space

				if (wsX > 0) {
					lrMoves(wsY, wsX, colSize, (byte) -1);
				}

				if (wsX < width-1) {
					lrMoves(wsY, wsX, colSize, (byte) 1);
				}
			}
		}

		// possible to encounter non-header elements of larger blocks, we differentiate by the
		// relation of the (x,y) index with the content of the matrix
		// if the space contains the head of a 1X1 block, linearIndex equals content
		// for larger blocks
		// if content > linearIndex, the space contains the head, content is tail index
		// if content < linearIndex, the space is not the head, content is head index

		public void tbMoves(int y, int x, int count, byte shift) {
			int newY = y + shift;
			for (int i = x; i < x+count; i++) {
				int content = blockPositions[newY][i];
				int index = linearIndex (newY, i);
				if (content == 0) {
					continue;
					// found another whitespace --> skip
				}
				else if (content > index) {
					// found the head, content is tail pointer
					int endX = (content-1)%width;
					int endY = (content-1)/width;
					if (endX < x+count) {
						// you know the block fits : this is a possible move
						int[] newMove = {newY, i, endY, endX, shift, 0};
						moves.push(newMove);
					}
					// and you update the i value to pass over that large block you found
					i = endX;
				}
				else if (content < index) {
					// found non-head, content is head pointer.
					int startX = (content-1)%width; // in index terms
					int startY = (content-1)/width;
					content = blockPositions[startY][startX];
					int endX = ((content-1)%width);
					int endY = ((content-1)/width);
					if (startX >= x && endX < x+count) {
						// you know the block fits at its beginning and end: this is a possible move
						int[] newMove = {startY, startX, endY, endX, shift, 0};
						moves.push(newMove);
					}
					i = endX;
				}
				else {
					// single block whitespace (content == index) can definitely move it down
					int[] newMove = {newY, i, newY, i, shift, 0};
					moves.push(newMove);
				}
			}
		}

		public void lrMoves(int y, int x, int count, byte shift) {
			int newX = x+shift;
			for (int i = y; i < y+count; i++) {
				int content = blockPositions[i][newX];
				int index = linearIndex (i, newX);
				if (content == 0) {
					continue;
					// found a whitespace, now skip it
				}
				else if (content > index) {
					// found the head, content is tail pointer
					int endX = (content-1)%width;
					int endY = (content-1)/width;
					if (endY < y+count) {
						// you know the block fits : this is a possible move
						int[] newMove = {i, newX,endY, endX, 0 , shift};
						moves.push(newMove);
					}
					// and you update the i value to pass over that large block you found
					i = endY;
				}
				else if (content < index) {
					// found non-head, content is head pointer.
					int startX = (content-1)%width; // in index terms
					int startY = (content-1)/width;
					content = blockPositions[startY][startX];
					int endY = (content-1)/width;
					int endX = (content-1)%width;
					if (startX >= x && endY < y+count) {
						// you know the block fits at its beginning and end: this is a possible move
						int[] newMove = {startY, startX, endY, endX,0 , shift};
						moves.push(newMove);
					}
					i = endY;
				}
				else {
					// single block (content == index)
					int[] newMove = {i, newX, i, newX, 0 , shift};
					moves.push(newMove);
				}
			}
		}

		// the Tray has no more moves when both the moves stack and the whiteSpace stack is empty
		// the moves stack should feature all the possible moves around a single whiteSpace
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return !moves.isEmpty();
		}

		public int[] next() {
			// TODO Auto-generated method stub
			// if moves is empty, whiteSpace should not be empty (see hasNext()) and should hold the next whitespace
			// the next whitespace is popped to use to re-populate the moves stack

			return moves.pop();
		}

		public void remove() {
			// TODO Auto-generated method stub	
		}
	}
}


