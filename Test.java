import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class Test {
//	private HashMap<String, Integer> vs;
//	private ArrayList<TestV> vList;
//	private ArrayList<ArrayList<TestV>> vMatrix = new ArrayList();
//	private static int[][] doubleArray = new int[100][100];
//	private static HashMap<Integer, String> vNames;
//	private static vNames
//	private static ArrayList[][] arrayli = new ArrayList[10][10];
	public int[][] matrix;
	public int matrixCounter;
	public String[] matrixNames;
	public HashMap<String, Integer> matrixLabels; //ideally would be a bi-directional hashmap
	public HashMap<Integer, String> matrixIndices;
	private static final int ARRAY_SIZE_MULTIPLIER = 2;
    public static final int disconnectedDist = -1;
	private int matrixSize;
	
	// TODO - ask what Map.Entry does, maybe also ask how he is so familiar with HashMaps and the build in data structures
	
	public Test() 
	{
//		vs = new HashMap();
//		vList = new ArrayList();
//		doubleArray = new int[100][100];
		matrixSize = 10; // need
		matrix = new int[matrixSize][matrixSize]; // need
		matrixCounter = 0; // need
		matrixNames = new String[matrixSize];
		matrixLabels = new HashMap<String, Integer>(); // need
		matrixIndices = new HashMap<Integer, String>(); // need
	}
	
	public void addV(String label)
	{
		// Check if array is big enough
		if ( !(matrixCounter < matrixSize) ) {
			// Increase the size of the array
			matrixSize *= ARRAY_SIZE_MULTIPLIER;
			int newMatrix[][] = new int[matrixSize][matrixSize];
			
			// Copy contents of old array to new array
			for (int i = 0; i < matrixSize/2; i++) {
				for (int j = 0; j < matrixSize/2; j++) {
					newMatrix[i][j] = matrix[i][j];
				}
			}
			matrix = newMatrix;			
		}
		
		matrixLabels.put(label, matrixCounter);
		matrixIndices.put(matrixCounter, label);
		matrixCounter++;
		
//		vList.add(new TestV(label));
//		vList.add();
//		doubleArray[0] = 5;
		
//		if (matrixCounter < matrixSize) {
////			matrixNames[matrixCounter] = label;
//			matrixLabels.put(label, matrixCounter);
//			matrixCounter++;
//		}
//		else {
//			// Increase the size of the array
//			matrixSize *= ARRAY_SIZE_MULTIPLIER;
//			int newMatrix[][] = new int[matrixSize][matrixSize];
//			
//			// Copy contents of old array to new array
//			for (int i = 0; i < matrixSize/2; i++) {
//				for (int j = 0; j < matrixSize/2; j++) {
//					newMatrix[i][j] = matrix[i][j];
//				}
//			}
//			matrix = newMatrix;
//			
////			matrixNames[matrixCounter] = label;
//			matrixLabels.put(label, matrixCounter);
//			matrixCounter++;
//		}
		
		
//		try {
//			matrixNames[matrixCounter] = label;
//			matrixLabels.put(label, matrixCounter);
//			matrixCounter++;			
//		}
//		catch (ArrayIndexOutOfBoundsException e){
//			System.out.println(">>>Array is full, need to increase size<<<");
//		}
		
	}
	
	public void addE(String srcLabel, String tarLabel)
	{
		if (matrixLabels.containsKey(tarLabel) && matrixLabels.containsKey(srcLabel)) {
			// Set the edge on both "rows" of the matrix
			matrix[matrixLabels.get(srcLabel)][matrixLabels.get(tarLabel)] = 1;
			matrix[matrixLabels.get(tarLabel)][matrixLabels.get(srcLabel)] = 1;			
		}
		else {
			// Throw incorrect input exception - Vertex entered does not exist
			System.out.println(">>> ERROR : Vertex entered does not exist <<<");
		}
	}
	
	public ArrayList<String> checkN(String label)
	{
//        ArrayList<Integer> neighbours = new ArrayList<Integer>();
//        
//        int indexOfV = matrixLabels.get(label);
//        
//        // Iterate throw entire "row" aka the neighbours of V
//        for (int i = 0; i < matrix[indexOfV].length; i ++) {
//        	// Check for neighbour (value is 1)
//        	if (matrix[indexOfV][i] == 1) {
//        		System.out.printf("Neighbour at i = %d, ", i);
//        		neighbours.add(i);
//        	}
//        }
//        System.out.println();
//        
////        // Find the keys for the neighbours
////        Set keys = matrixLabels.keySet();
//////        System.out.printf("Key = %s", keys. neighbours.get(0));
////        for (int i = 0; i < neighbours.size(); i++) {
////        	
////        }
//
//        // Determine what indices match what labels
//        System.out.println("Values within neighbours ArrayList");
//        for (int value : neighbours) {
//        	System.out.printf("%d, ", value);
//        }
//        
//        // Iterate over set to determine what vertex labels are neighbours
//        for (String vLabel : matrixLabels.keySet()) {
//        	for (int vPosLabel : neighbours) {
//        		if (matrixLabels.get(vLabel) == vPosLabel) {
//        			System.out.printf("%d = %s\n", vPosLabel, vLabel);
//        		}
//        	}
//        }
        
        ArrayList<Integer> neighbours = new ArrayList<Integer>();
        ArrayList<String> nLabels = new ArrayList<String>();
        
        // Check if entered vertex label exists
        if ( !matrixLabels.containsKey(label) ) {
        	// Throw an exception here
        	System.out.println(">>> ERROR: Vertex label does not exist");
        }
        else {
        	int indexOfV = matrixLabels.get(label);
        	
        	// Iterate through entire "row" aka the neighbours of V (Length of |V|)
        	for (int i = 0; i < matrix[indexOfV].length; i ++) {
        		// Check for neighbour (value is 1)
        		if (matrix[indexOfV][i] == 1) {
//        			neighbours.add(i);
        			nLabels.add(matrixIndices.get(i));
        		}
        	}
        	
//        	If not using two HashMaps (another for indices of Vertices)
//        	// Iterate over set to determine what vertex labels are neighbours
//        	// TODO - could use Map.Entry here. Not really sure how to though
//        	for (String vLabel : matrixLabels.keySet()) {
//        		for (int vPosLabel : neighbours) {
//        			if (matrixLabels.get(vLabel) == vPosLabel) {
//        				nLabels.add(vLabel);
//        			}
//        		}
//        	}
        	
        	
        	
        }
        return nLabels;        	
        
	}
	
	// Flatten out the position of the vertex - not truely removing it (aka it still exists but with no reference)
	// A ArrayList would easily resolve the issue ^
	public void removeVertex(String vertLabel) {
		
		if ( !matrixLabels.containsKey(vertLabel) ) {
        	// Throw an exception here
        	System.out.println(">>> ERROR: Vertex label does not exist");
        }
		else {
			final int indexOfV = matrixLabels.get(vertLabel);
			
			// Iterate through entire "row" (Length of |V|)
			for (int i = 0; i < matrix[indexOfV].length; i ++) {
				// 0 out the row
				matrix[indexOfV][i] = 0;
			}
			// Remove column reference from every other row (vertex)
			for (int i = 0; i < matrix.length; i++) {
				matrix[i][indexOfV] = 0;
			}
			
			// Remove from HashMap 
			matrixIndices.remove(matrixLabels.get(vertLabel));
			matrixLabels.remove(vertLabel);
		}
		
    } // end of removeVertex()
	
    public void removeEdge(String srcLabel, String tarLabel) {
    	if (matrixLabels.containsKey(tarLabel) && matrixLabels.containsKey(srcLabel)) {
    		// Check if there is an edge between vertices 
    		if ( (matrix[matrixLabels.get(srcLabel)][matrixLabels.get(tarLabel)] == 1) && 
    				(matrix[matrixLabels.get(tarLabel)][matrixLabels.get(srcLabel)] == 1) ) {
    			// Edge has been found, remove it (i.e. both references to 0)
    			matrix[matrixLabels.get(srcLabel)][matrixLabels.get(tarLabel)] = 0;
    			matrix[matrixLabels.get(tarLabel)][matrixLabels.get(srcLabel)] = 0;
    		}
    		else {
    			// Throw an error as there is no edge to remove
    			System.out.println(">>> ERROR : There is no edge to remove <<<");
    		}
		}
		else {
			// Throw incorrect input exception - one of the vertices entered does not exist
			System.out.println(">>> ERROR : Vertex entered does not exist <<<");
		}
    } // end of removeEdges()
	
    public void printVertices() {
    	for (String entry : matrixLabels.keySet()) {
    		System.out.print(entry + " ");
    	}
    } // end of printVertices()
    
    public void printEdges() {	
    	// Run through matrix and report any edges (i.e. found a 1)
    	for (int i = 0; i < matrix.length; i++) {
    		for (int j = 0; j < matrix[i].length; j++) {
    			// Locate edges
    			if (matrix[i][j] == 1) {
    				// Print the corresponding Label for Vertex i & j
    				System.out.printf("%s %s\n", matrixIndices.get(i), matrixIndices.get(j));
    			}
    		}
    	}
    } // end of printEdges()
    
    public int shortestPathDistance(String vertLabel1, String vertLabel2) {    	
    	// Check if vertices exist
    	if ( !(matrixLabels.containsKey(vertLabel1)) && !(matrixLabels.containsKey(vertLabel2)) ) {
    		// Vertices do not exist - throw to System.err
    		System.out.println(">>> ERROR: Vertices do not exist <<<");
    	}
    	
		/* Breadth-First Search (BFS)
		 1. Choose an arbitrary vertex V and mark it visited
		 --- This part could be a recursive function ---
		 2. Visit and mark (visited) each of the adjacent (neighbour) vertices of v in turn.
		 3. Once all neighbours of v have been visited, select the first neighbour that was visited,
		 	and visit all its (unmarked) neighbours.
		 4. Then select the second visited neighbour of v, and visit all its unmarked neighbours.
		 --- End of recursive ---
		 5. The algorithm halts when we visited all vertices.*/
//    		ArrayList<String> visited = new ArrayList<String>();
//    		// 1.
//    		visited.add(vertLabel1);
//    		// 2. 
//    		// Checks the current vertex's neighbours
////    		for (String vertex : checkN(vertLabel1)) {
////    			if (vertex == vertLabel2) {
////    				return distance += 1;
////    			}
////    			visited.add(vertex);
////    		}
////    		System.out.println(visited.toString());
////    		distance++;
//    		distance = shortestPathDistanceR(visited, 0, vertLabel2, distance);
//    		return distance;
    	
		/*// mark all vertices unvisited
		for i = 0 to v do
		Marked[i] = 0
		end for
		// initial distance from source vertex is 0
		Queue.enqueue((s, 0))
		while not Stack.isEmpty() do
			(v,d) = Queue.deque()
			if v == t then
				return d
			end if
			Marked[v] = order
			for w subset V adjacent to v do
				if not Marked[w] then
					Queue.enqueue((w, d +1)
				end if
			end for
		end while*/
    	
    	// TODO Jeff has said we are allowed to use the native Queue implementations
    	LinkedList<Pair> queue = new LinkedList<Pair>();
    	ArrayList<String> checked = new ArrayList<String>();
    	int tempDistance = 0;
    	    	
    	queue.add(new Pair(vertLabel1, 0));
    	checked.add(vertLabel1);
    	
    	while (queue.size() > 0)
    	{
    		Pair node = queue.pop();
    		System.out.println("At " + node.vertex + "... dist = " + node.distance);
    		
    		if (node.vertex.equals(vertLabel2)) {
    			System.out.println("Match!");
    			return node.distance;
    		}
    		
    		// Marked[current.vertex] = order?
    		
    		// Run through the neighbours of the current node and add them to the 
    		// queue if they haven't been checked
			tempDistance = node.distance += 1;
    		for (String neighbour : checkN(node.vertex)) {
    			if ( !checked.contains(neighbour) ) {
    				checked.add(neighbour);
    				queue.add(new Pair(neighbour, tempDistance));
    			}
    		}
    		System.out.println(checked.toString());
    	}
    	
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    class Pair
	{
		public String vertex;
		public int distance;
		public Pair(String vertex, int distance) {
			this.vertex = vertex;
			this.distance = distance;
		}
	}
    
    private int shortestPathDistanceR(ArrayList<String> visited, int visitedPos, String searchLabel, int distance) 
    {
    	System.out.println("Running");
		// Checks the current vertex's neighbours
    	for (String vertex : checkN(visited.get(visitedPos))) {
    		System.out.println(vertex);
			if (vertex.equals(searchLabel)) {
				System.out.printf("Item found! D = %d\n", distance);
				return distance;
			}
			visited.add(vertex);
		}
    	shortestPathDistanceR(visited, ++visitedPos, searchLabel, ++distance);
    	
    	return disconnectedDist;
    }

    
	public void printMatrix()
	{
		for (int i = 0; i < matrix.length; i++) {
//			System.out.printf("i = %d\n", i);
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%d,%d ", i, j);
			}
			System.out.println();
		}
	}
	
	public void printMatrixContents()
	{
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf("%d, ", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printMatrixContentsWHeadings()
	{
		for (int i = 0; i < matrix.length; i++) {
			if (i == 0) {
				System.out.print("   ");
				for (int j = 0; j < matrix[i].length; j++) {
					System.out.printf("%s  ", matrixIndices.get(j));					
				}
				System.out.println();
			}
			System.out.printf("%s: ", matrixIndices.get(i));
			for (int j = 0; j < matrix[i].length; j++) {				
				System.out.printf("%d, ", matrix[i][j]);
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		Test g = new Test();
		// Adding vertices
		g.addV("A");
		g.addV("B");
		g.addV("C");
		g.addV("D");
		g.addV("E");
		g.addV("F");
		g.addV("G");
		g.addV("H");
		g.addV("I");
		g.addV("J");
		
		// Adding edges
		g.addE("A", "B");
		g.addE("A", "C");
		g.addE("C", "B");
		g.addE("G", "B");
		g.addE("H", "A");
		g.addE("A", "H");
		g.addE("C", "D");
		
		g.printMatrixContentsWHeadings();
		
		
		System.out.println();
				
		// Testing shortest path
		int result = g.shortestPathDistance("A", "A");
		System.out.println("Shortest distance - A A");
		System.out.printf("Shortest Path A A: %d (should be 0)\n", result);
		
		System.out.println();
		
		result = g.shortestPathDistance("A", "B");
		System.out.println("Shortest distance - A B");
		System.out.printf("Shortest Path A B: %d (should be 1)\n", result);
		
		System.out.println();
		
		result = g.shortestPathDistance("A", "G");
		System.out.println("Shortest distance - A G");
		System.out.printf("Shortest Path A G: %d (should be 2)\n", result);
		
		
		/*DumbQueue q = new DumbQueue();
		
		q.enqueue("Hey");
		q.enqueue("I'm");
		q.enqueue("Dylan");
		
		for (String e : q.elements) { 
			System.out.println(e);
		}
		
		System.out.println();
		
		System.out.println("Head = " + q.getHead());
		
		q.dequeue();
		q.dequeue();
		
		for (String e : q.elements) { 
			System.out.println(e);
		}
		System.out.println("Head = " + q.getHead());*/
		
		/*System.out.println("Test");
		
		ArrayList<String> names = new ArrayList();
		
		names.add("Dylan");
		names.add("Ryan");
		
		for (String name : names) {
			System.out.printf("Name = %s\n", name);
		}
		
		HashMap<String, Integer> vertices = new HashMap();
		vertices.put("A", 0);
		vertices.put("B", 1);
		
		System.out.printf("Value of A = %d\n", vertices.get("A"));
		System.out.printf("Value of B = %d\n", vertices.get("B"));
		
//		doubleArray[0][0] = 1;
		
//		System.out.printf("Number is = %d\n", doubleArray[0][0]);
		
//		arrayli[][]
		
		Test g = new Test();
		
		System.out.printf("Counter: %d\n", g.matrixCounter);
		System.out.printf("Matrix pos 0,0 = %d\n", g.matrix[0][0]);
//		g.matrix[0][0] = 1;
		System.out.printf("Matrix pos 0,0 = %d\n", g.matrix[0][0]);
		
		System.out.println();
		
		g.printMatrix();
		
		System.out.println();
		
		g.printMatrixContents();
		
		g.addV("A");
		
		System.out.println();
		
		for (String label : g.matrixNames) {
			if (label instanceof String) {
				System.out.printf("%s, ", label);
			}
			else {
				System.out.print("null, ");
			}
		}
		
		g.addV("B");
		g.addV("C");
		g.addV("D");
		g.addV("E");
		g.addV("F");
		g.addV("G");
		g.addV("H");
		g.addV("I");
		g.addV("J");
		g.addV("K"); // Too long, need to increase size of matrix and label array
		System.out.println();
		
		for (String label : g.matrixNames) {
			if (label instanceof String) {
				System.out.printf("%s, ", label);
			}
			else {
				System.out.print("null, ");
			}
		}
		System.out.println();
		
		g.matrixLabels.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));
		System.out.println();
		
		g.addE("A", "B");
		g.addE("A", "C");
		g.addE("A", "D");
		g.addE("A", "E");
		g.addE("A", "F");

		
		
		
		g.addE("C", "G");
		g.addE("E", "g");
		g.addE("A", "K");
		g.printMatrixContents();
		
		
		ArrayList<String> returnedN;
		returnedN = g.checkN("A");
		System.out.printf("%s\n", returnedN.toString());
		
		returnedN = g.checkN("B");
		System.out.printf("%s\n", returnedN.toString());
		
		returnedN = g.checkN("G");
		System.out.printf("%s\n", returnedN.toString());
		
		returnedN = g.checkN("c");
		System.out.printf("%s\n", returnedN.toString());

		g.addE("C", "B");
		
		g.printEdges();
		
		g.removeVertex("A");
		g.printMatrixContents();
		
		returnedN = g.checkN("B");
		System.out.printf("%s\n", returnedN.toString());
		g.addE("A", "B");

		g.removeEdge("B", "C");
		g.printMatrixContents();
		
		g.printVertices();
		System.out.println();
		g.printEdges();*/
	}
}

class TestV < T extends Object>
{
	private T label;
	private ArrayList<Integer> relationships;
	
	public TestV(T label)
	{
		this.label = label;
		this.relationships = new ArrayList();
	}
}

class DumbQueue
{
	// elements
	public String[] elements;
	private int size = 10;
	private int enqCounter = 0, deqCounter = 0;
	
	public DumbQueue()
	{
		elements = new String[size];
	}
	
	public void enqueue(String e)
	{
		if ( !(enqCounter < elements.length) ) {
			// Increase the size of the array
			size *= 2;
			String newQueue[] = new String[size];
			
			// Copy contents of old array to new array
			for (int i = 0; i < size/2; i++) {
				newQueue[i] = elements[i];
			}
			elements = newQueue;			
		}
		
		elements[enqCounter++] = e;
	}
	
	public void dequeue()
	{
		elements[deqCounter++] = null;
	}
	
	public String getHead()
	{
		return elements[--enqCounter];
	}
}
