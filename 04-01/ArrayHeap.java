import java.util.ArrayList;

public class ArrayHeap {
	private ArrayList<Node> contents = new ArrayList<Node>();

	public ArrayHeap() {
		// add a blank spot for the unused index
		contents.add(null);
	}

	public void insert(int value) {
		Node newNode = new Node(value, this);
		newNode.myIndex = contents.size();
		contents.add(newNode);
		newNode.bubbleUp();
	}

	public Node removeMax() {
		if (this.contents.size() == 1) {
			return null;
		}
		Node max = this.contents.get(1);
		if (this.contents.size() == 2) {
			this.contents.remove(1);
			return max;
		}
		int lowestLocation = this.contents.size() - 1;
		Node lowest = this.contents.get(lowestLocation);
		this.contents.remove(lowestLocation);
		lowest.myIndex = 1;
		this.contents.set(1, lowest);
		lowest.bubbleDown();
		return max;
	}

	private Node getNode(int index) {
		if (index >= contents.size()) {
			return null;
		} else {
			return contents.get(index);
		}
	}

	private void swap(Node node1, Node node2) {
		int index1 = node1.myIndex;
		int index2 = node2.myIndex;
		node1.myIndex = index2;
		node2.myIndex = index1;
		this.contents.set(index1, node2);
		this.contents.set(index2, node1);
	}

	private class Node {
		private int myValue;
		private int myIndex;
		private ArrayHeap myTree;

		private Node(int value, ArrayHeap tree) {
			this.myValue = value;
			this.myTree = tree;
		}

		/*
		 * get the left child of this node
		 */
		private Node getLeft() {
			return this.myTree.getNode(this.myIndex * 2);
		}

		/*
		 * get the right child of this node
		 */
		private Node getRight() {
			return this.myTree.getNode(this.myIndex * 2 + 1);
		}

		private Node getParent() {
			return this.myTree.getNode(this.myIndex / 2);
		}

		/*
		 * Bubble up a recently added node.
		 */
		private void bubbleUp() {

		}

		/*
		 * Bubble down a swapped element after a call to removeMax
		 */
		private void bubbleDown() {
			Node left = this.getLeft();
			Node right = this.getRight();
			if (left == null && right == null) {
				// it is as far down as it can go.
				return;
			}
			Node maxChild = this.max(left, right);
			if (maxChild.myValue > this.myValue) {
				this.myTree.swap(maxChild, this);
				this.bubbleDown();
			}
		}

		/*
		 * Determine the maximum of the two children Invariant: Only one of
		 * node1 and node2 can be null.
		 */
		private Node max(Node node1, Node node2) {
			if (node1 == null) {
				return node2;
			} else if (node2 == null) {
				return node1;
			} else if (node1.myValue > node2.myValue) {
				return node1;
			} else {
				return node2;
			}
		}
	}

	public static void main(String[] args) {
		ArrayHeap heap  = new ArrayHeap();
		heap.insert(3);
		heap.insert(9);
		heap.insert(7);
		heap.insert(4);
		heap.insert(1);
		heap.insert(8);
		heap.insert(5);
		heap.insert(2);
		heap.insert(3);
		heap.insert(4);
	}
}
