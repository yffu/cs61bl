import java.util.*;

public class Graph {

	private LinkedList<Edge> [ ] myAdjLists;
	private int myVertexCount;
	
	// Initialize a graph with the given number of vertices and no edges.
	public Graph (int numVertices) {
		myAdjLists = new LinkedList [numVertices];
		for (int k=0; k<numVertices; k++) {
			myAdjLists[k] = new LinkedList<Edge> ( );
		}
		myVertexCount = numVertices;
	}
	
	// Add to the graph a directed edge from vertex v1 to vertex v2.
	public void addEdge (int v1, int v2) {
		addEdge (v1, v2, null);
	}
	
	// Add to the graph an undirected edge from vertex v1 to vertex v2.
	public void addUndirectedEdge (int v1, int v2) {
		addUndirectedEdge (v1, v2, null);
	}
	
	// Add to the graph a directed edge from vertex v1 to vertex v2,
	// with the given edge information.
	public void addEdge (int v1, int v2, Object edgeInfo) {
		myAdjLists[v1].add (new Edge (v1, v2, edgeInfo));
	}
	
	// Add to the graph an undirected edge from vertex v1 to vertex v2,
	// with the given edge information.
	public void addUndirectedEdge (int v1, int v2, Object edgeInfo) {
		addEdge (v1, v2, edgeInfo);
		addEdge (v2, v1, edgeInfo);
	}
	
	// Return true if there is an edge from vertex "from" to vertex "to";
	// return false otherwise.
	public boolean isAdjacent (int from, int to) {
		Iterator<Edge> iter = myAdjLists[from].iterator ( );
		while (iter.hasNext ( )) {
			Edge e = iter.next ( );
			if (e.to ( ) == to) {
				return true;
			}
		}
		return false;
	}
	
	// Return the number of incoming vertices for the given vertex,
	// i.e. the number of vertices v such that (v, vertex) is an edge.
	public int inDegree (int vertex) {
		int count = 0;
		for (int k=0; k<myVertexCount; k++) {
			if (isAdjacent (k, vertex)) {
				count++;
			}
		}
		return count;
	}
	
	// A class that iterates through the vertices of this graph.
	private class VertexIterator implements Iterator<Integer> {
		
		private Scheduler fringe;
		private HashSet<Integer> visited;
		
		public VertexIterator (Integer start) {
			fringe = new Scheduler ( );
			fringe.add (start);
			visited = new HashSet<Integer> ( );
		}
		
		public boolean hasNext ( ) {
			return !fringe.isEmpty();
		}
		
		public Integer next ( ) {
			if (!hasNext ( )) {
				throw new NoSuchElementException ("ran out of vertices");
			}
			Integer toReturn = fringe.remove();
			Iterator<Edge> nbrIter = myAdjLists[toReturn.intValue()].iterator();
			while (nbrIter.hasNext()) {
				Edge e = nbrIter.next();
				if (!visited.contains(e.to())) {
					fringe.add(e.to());
				}
			}
			visited.add(toReturn);
			return toReturn;
		}
		
		public void remove ( ) {
			throw new UnsupportedOperationException ("vertex removal not implemented");
		}
	}
	
	// A structure that represents the "fringe" in an iteration.
	private class Scheduler {
		
		private Stack<Integer> myValues;
		
		public Scheduler ( ) {
			myValues = new Stack<Integer> ( );
		}
		
		public boolean isEmpty ( ) {
			return myValues.isEmpty();
		}
		
		public void add (Integer x) {
			myValues.push(x);
		}
		
		public Integer remove ( ) {
			return myValues.pop();
		}
	}
	
	// Return the collected result of iterating through this graph's
	// vertices.
	public ArrayList<Integer> visitAll (int startVertex) {
		ArrayList<Integer> result = new ArrayList<Integer> ( );
		Iterator<Integer> iter = new VertexIterator (startVertex);
		
		while (iter.hasNext()) {
			result.add (iter.next());
		}
		return result;
	}
	
	public ArrayList<Integer> path (int startVertex, int stopVertex) {
		return new ArrayList<Integer> ( );
		// you supply the body of this method
	}

	public ArrayList<Integer> topologicalSort ( ) {
		ArrayList<Integer> result = new ArrayList<Integer> ( );
		Iterator<Integer> iter = new TopologicalIterator ( );
		while (iter.hasNext()) {
			result.add(iter.next());
		}
		return result;
	}
	
	private class TopologicalIterator implements Iterator<Integer> {
		
		private Scheduler fringe;
		// more instance variables go here
		
		public TopologicalIterator ( ) {
			fringe = new Scheduler ( );
			// more statements go here
		}
		
		public boolean hasNext ( ) {
			return !fringe.isEmpty();
		}
		
		public Integer next ( ) {
			return new Integer (0);
			// you supply the real body of this method
		}
		
		public void remove ( ) {
			throw new UnsupportedOperationException ("vertex removal not implemented");
		}
	}
		
	private class Edge {
	
		private Integer myFrom;
		private Integer myTo;
		private Object myEdgeInfo;
		
		public Edge (int from, int to, Object info) {
			myFrom = new Integer (from);
			myTo = new Integer (to);
			myEdgeInfo = info;
		}
		
		public Integer to ( ) {
			return myTo;
		}
		
		public Object info ( ) {
			return myEdgeInfo;
		}
		
		public String toString ( ) {
			return "(" + myFrom + "," + myTo + ",dist=" + myEdgeInfo + ")";
		}
	}
	
	public static void main (String [ ] args) {
		ArrayList<Integer> result;
		
		Graph g1 = new Graph (5);
		g1.addEdge (0, 1);
		g1.addEdge (0, 2);
		g1.addEdge (0, 4);
		g1.addEdge (1, 2);
		g1.addEdge (2, 0);
		g1.addEdge (2, 3);
		g1.addEdge (4, 3);
		System.out.println ("Traversal starting at 0");
		result = g1.visitAll (0);
		Iterator<Integer> iter;
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println ("Traversal starting at 2");
		result = g1.visitAll (2);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println ("Traversal starting at 3");
		result = g1.visitAll (3);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println ("Traversal starting at 4");
		result = g1.visitAll (4);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println("Path from 0 to 3");
		result = g1.path(0, 3);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println("Path from 0 to 4");
		result = g1.path(0, 4);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println("Path from 1 to 3");
		result = g1.path(1, 3);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println("Path from 1 to 4");
		result = g1.path(1, 4);
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
		System.out.println ( );
		System.out.println ( );
		System.out.println("Path from 4 to 0");
		result = g1.path(4, 0);
		if (result != null) {
			System.out.println("*** should be no path!");
		}
		
		Graph g2 = new Graph (5);
		g2.addEdge (0, 1);
		g2.addEdge (0, 2);
		g2.addEdge (0, 4);
		g2.addEdge (1, 2);
		g2.addEdge (2, 3);
		g2.addEdge (4, 3);
		System.out.println ( );
		System.out.println ( );
		System.out.println("Topological sort");
		result = g2.topologicalSort();
		iter = result.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next() + " ");
		}
	}
}
