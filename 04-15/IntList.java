public class IntList {

	private static boolean iAmDebugging = false;
	
	DListNode myHead;
	DListNode myTail;
	int mySize;
	
	public IntList ( ) {
		myHead = null;
		myTail = null;
		mySize = 0;
	}
	
	// head points to a 1-element list.
	public IntList (DListNode head) {
		myHead = myTail = head;
	}
	
	// Add a node with the given value to the front of this list.
	public void addToFront (int k) {
		if (myHead == null) {
			myHead = myTail = new DListNode (k);
		} else {
			myHead = new DListNode (k, null, myHead);
			myHead.myNext.myPrev = myHead;
		}
		mySize++;
	}
	
	// Add a node with the given value to the end of this list.
	public void addToEnd (int k) {
		if (myHead == null) {
			myHead = myTail = new DListNode (k);
		} else {
			myTail.myNext = new DListNode (k, myTail, null);
			myTail = myTail.myNext;
		}
		mySize++;
	}
	
	// Return true if this list is empty, false otherwise.
	public boolean isEmpty ( ) {
		return mySize == 0;
	}
	
	// The argument is concatenated to this list.
	public void append (IntList list) {
		if (list.isEmpty ( )) {
			return;
		}
		if (isEmpty ( )) {
			myHead = list.myHead;
			myTail = list.myTail;
			mySize = list.mySize;
			return;
		}
		myTail.myNext = list.myHead;
		list.myHead.myPrev = myTail;
		myTail = list.myTail;
		mySize += list.mySize;
	}
	
	public String toString ( ) {
		String s = "";
		for (DListNode p=myHead; p!=null; p=p.myNext) {
			s = s + p.myItem + " ";
		}
		return s;
	}
	
	private class DListNode {
	
		int myItem;
		DListNode myPrev;
		DListNode myNext;
	
		public DListNode (int k) {
			myItem = k;
			myPrev = myNext = null;
		}
		
		public DListNode (int k, DListNode prev, DListNode next) {
			myItem = k;
			myPrev = prev;
			myNext = next;
		}
	}
	
	public static int randomInt ( ) {
		return (int) (100 * Math.random ( ));
	}

	// Sort this list using the selection sort algorithm
	public void selectionSort ( ) {
		IntList sorted = new IntList ( );
		while (myHead != null) {
			int maxSoFar = myHead.myItem;
			DListNode maxPtr = myHead;
			// Find the node in the list pointed to by myHead
			// whose value is largest.
			for (DListNode p=myHead; p!=null; p=p.myNext) {
				if (p.myItem > maxSoFar) {
					maxSoFar = p.myItem;
					maxPtr = p;
				}
			}
			sorted.addToEnd (maxSoFar);
			remove (maxPtr);
		}
		myHead = sorted.myHead;
	}
	
	// Remove the node referenced by p from this list.
	private void remove (DListNode p) {
		if (myHead == myTail) {
			myHead = myTail = null;
		} else if (p == myHead) {
			myHead = myHead.myNext;
			myHead.myPrev = null;
		} else if (p == myTail) {
			myTail = myTail.myPrev;
			myTail.myNext = null;
		} else {
			p.myNext.myPrev = p.myPrev;
			p.myPrev.myNext = p.myNext;
		}
	}
	
	// Return the result of sorting the values in this list 
	// using the insertion sort algorithm.
	// This list is no longer usable after this operation.
	public IntList insertionSort ( ) {
		DListNode soFar = null;
		for (DListNode p=myHead; p!=null; p=p.myNext) {
			soFar = insert (p, soFar);
		}
		return new IntList (soFar);
	}
	
	// Insert the value contained in p into the list headed by head
	// so that the node values are in decreasing order.
	private DListNode insert (DListNode p, DListNode head) {
		// you fill this in
	}
	
	// Return the result of sorting the values in this list 
	// using the Quicksort algorithm.
	// This list is no longer usable after this operation.
	public IntList quicksort ( ) {
		if (mySize <= 1) {
			return this;
		}
		// Assume first element is the divider.
		IntList larges = new IntList ( );
		IntList smalls = new IntList ( );
		int divider = myHead.myItem;
		// You fill in code here.

		larges = larges.quicksort ( );
		smalls = smalls.quicksort ( );
		smalls.addToFront (divider);
		larges.append (smalls);
		return larges;
	}
	
	// Return the result of sorting the values in this list 
	// using the merge sort algorithm.
	// This list is no longer usable after this operation.
	public IntList mergesort ( ) {
		if (mySize <= 1) {
			return this;
		}
		IntList oneHalf = new IntList ( );
		IntList otherHalf = new IntList ( );
		DListNode p = myHead;
		while (p != null) {
			oneHalf.addToEnd (p.myItem);
			p = p.myNext;
			if (p != null) {
				otherHalf.addToEnd (p.myItem);
				p = p.myNext;
			}
		}
		oneHalf = oneHalf.mergesort ( );
		otherHalf = otherHalf.mergesort ( );
		return merge (oneHalf.myHead, otherHalf.myHead);
	}
	
	// Return the result of merging the two sorted lists
	// represented by list1 and list2.
	private static IntList merge (DListNode list1, DListNode list2) {
		IntList rtn = new IntList ( );
		while (list1 != null && list2 != null) {
			if (list1.myItem > list2.myItem) {
				rtn.addToEnd (list1.myItem);
				list1 = list1.myNext;
			} else {
				rtn.addToEnd (list2.myItem);
				list2 = list2.myNext;
			}
		}
		while (list1 != null) {
			rtn.addToEnd (list1.myItem);
			list1 = list1.myNext;
		}
		while (list2 != null) {
			rtn.addToEnd (list2.myItem);
			list2 = list2.myNext;
		}
		return rtn;
	}
			
	public static void main (String [ ] args) {
		IntList values;
		IntList sortedValues;
		values = new IntList ( );
		System.out.print ("Before selection sort: ");
		for (int k=0; k<10; k++) {
			values.addToFront (randomInt ( ));
		}
		System.out.println (values);
		values.selectionSort ( );
		System.out.print ("After selection sort: ");
		System.out.println (values);
		
		values = new IntList ( );
		System.out.print ("Before insertion sort: ");
		for (int k=0; k<10; k++) {
			values.addToFront (randomInt ( ));
		}
		System.out.println (values);
		sortedValues = values.insertionSort ( );
		System.out.print ("After insertion sort: ");
		System.out.println (sortedValues);
		
		values = new IntList ( );
		System.out.print ("Before quicksort: ");
		for (int k=0; k<10; k++) {
			values.addToFront (randomInt ( ));
		}
		System.out.println (values);
		sortedValues = values.quicksort ( );
		System.out.print ("After quicksort: ");
		System.out.println (sortedValues);
		
		values = new IntList ( );
		System.out.print ("Before merge sort: ");
		for (int k=0; k<10; k++) {
			values.addToFront (randomInt ( ));
		}
		System.out.println (values);
		sortedValues = values.mergesort ( );
		System.out.print ("After merge sort: ");
		System.out.println (sortedValues);
	}		
}
