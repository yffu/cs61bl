import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.Iterator;

public class TrayTest extends TestCase {
	public void testNoBlockConstructor() {
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			Tray tray = new Tray(5,3,blocks);
			assertFalse(tray.isOccupied(2, 1));
			assertFalse(tray.isOccupied(0, 1));
			assertFalse(tray.isOccupied(2, 0));
			assertFalse(tray.isOccupied(4, 2));
		} catch (Exception e) {
			e.printStackTrace();
			fail("A fail should not have been thrown. You are experiencing issues");
		}
	}
	
	// Tests a generic one block constructor and checks
	// if white space is configured correctly
	public void testOneBlockConstructor() {
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA = {1,0,2,1};
			blocks.add(blockA);
			
			Tray tray = new Tray(5,3,blocks);
			// Check to see that the block correctly occupies the tray
			assertTrue(tray.isOccupied(1, 0));
			assertTrue(tray.isOccupied(1, 1));
			assertTrue(tray.isOccupied(2, 0));
			assertTrue(tray.isOccupied(2, 1));
			
			// Check to see that all surrounding space is not occupied
			assertFalse(tray.isOccupied(0, 0));
			assertFalse(tray.isOccupied(0, 1));
			assertFalse(tray.isOccupied(1, 2));
			assertFalse(tray.isOccupied(2, 2));
			assertFalse(tray.isOccupied(3, 0));
			assertFalse(tray.isOccupied(3, 1));
			assertFalse(tray.isOccupied(3, 2));
			
		} catch(Exception e) {
			fail("A fail should not have been thrown. You are experiencing issues");
		}
	}
	
	// Tests to see if positions within the block refer to the correct head or tail pointer
	public void testHeadAndTailPointers() {
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA ={1,0,2,1};
			blocks.add(blockA);
			
			Tray tray = new Tray(5,3,blocks);
			
			// Assert that the upper left position of block A refers to the 
			// cell number of the lower right position of block A
			assertEquals(tray.getPointer(1, 0),8);
			
			// Assert that all other positions of block A refer to the cell number
			// of the upper right position of block A
			assertEquals(tray.getPointer(1, 1),4);
			assertEquals(tray.getPointer(2, 0),4);
			assertEquals(tray.getPointer(2, 1),4);
			
			// Assert that all other positions not on block A have 0 as their pointer
			assertEquals(tray.getPointer(0, 0),0);
			assertEquals(tray.getPointer(0, 1),0);
			assertEquals(tray.getPointer(0, 2),0);
			assertEquals(tray.getPointer(1, 2),0);
			assertEquals(tray.getPointer(2, 2),0);
			assertEquals(tray.getPointer(3, 0),0);
			assertEquals(tray.getPointer(3, 1),0);
			
		} catch (Exception e) {
			fail("A fail should not have been thrown. You are experiencing issues");
		}
	}
	
	// Tests to see if adding a block into non-white space generates the appropriate error
	public void testOverridingBlocks() {
		// Complete overlap
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA = {1,0,2,1};
			int[] blockB = {1,0,2,1};
			blocks.add(blockA);
			blocks.add(blockB);
			Tray tray = new Tray(5,3,blocks);
			fail("A block collision exception should have been thrown");
			
		} catch (Exception e) {
			assertTrue(true);
		}
		
		// Overlap of one space
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA = {1,0,2,1};
			int[] blockB = {2,1,2,2};
			blocks.add(blockA);
			blocks.add(blockB);
			Tray tray = new Tray(5,3,blocks);
			fail("A block collision exception should have been thrown");
			
		} catch (Exception e) {
			assertTrue(true);
		}
		
		// No Overlap; Adjacently placed 
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA = {1,0,2,1};
			int[] blockB = {3,0,3,2};
			blocks.add(blockA);
			blocks.add(blockB);
			Tray tray = new Tray(5,3,blocks);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("There should not be a block collision exception");
		}
		
		try {
			ArrayList<int[]> blocks = new ArrayList<int[]>();
			int[] blockA = {1,0,2,1};
			int[] blockB = {1,2,2,2};
			blocks.add(blockA);
			blocks.add(blockB);
			Tray tray = new Tray(5,3,blocks);
			assertTrue(true);
			
		} catch (Exception e) {
			fail("There should not be a block collision exception");
		}
	}
	
	public void testTray() throws Exception {
		ArrayList<int[]> coordinates = new ArrayList<int[]>();
		int[] block1 = {0, 0, 1 , 0};
		int[] block2 = {0, 3, 1, 3};
		int[] block3 = {2, 0, 3, 0};
		int[] block4 = {2, 3, 3, 3};
		int[] block5 = {1, 1, 2, 2};
		int[] block6 = {3, 1, 3, 2};
		int[] block7 = {4, 0, 4, 0};
		int[] block8 = {4, 1, 4, 1};
		int[] block9 = {4, 2, 4, 2};
		int[] block10 = {4, 3, 4, 3};

		coordinates.add(block1);
		coordinates.add(block2);
		coordinates.add(block3);
		coordinates.add(block4);
		coordinates.add(block5);
		coordinates.add(block6);
		coordinates.add(block7);
		coordinates.add(block8);
		coordinates.add(block9);
		coordinates.add(block10);

		Iterator<int[]> iter = coordinates.iterator();
		while(iter.hasNext()) {
			int[] temp = iter.next();
			for ( int i = 0; i <4 ; i ++)
				System.out.print(temp[i]+ " ");
			System.out.println();
		}
		Tray trey;
		trey = new Tray(5, 4, coordinates);

		Iterator<int[]> iterMoves = trey.getMoves();
		while (iterMoves.hasNext()) {
			int[] temp = iterMoves.next();
			System.out.println( "next move :" + temp[0] + " " + temp[1] + " "+ temp[2] + " "+ temp[3] + " ");
		}


	}

}
