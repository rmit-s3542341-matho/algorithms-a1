import java.io.*;
import java.util.*;

/**
 * Adjacency matrix implementation for the FriendshipGraph interface.
 * 
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjMatrix <T extends Object> implements FriendshipGraph<T>
{
	private static final int ARRAY_SIZE_MULTIPLIER = 2;
	
	private int size;
	public int[][] matrix;
	public int verticesCounter;
	public HashMap<T, Integer> labels; //ideally would be a bi-directional hashmap
	public HashMap<Integer, T> indices;
	
	/**
	 * Contructs empty graph.
	 */	
    public AdjMatrix() {
		size = 10;
		matrix = new int[size][size]; 
		verticesCounter = 0;
		labels = new HashMap<T, Integer>();
		indices = new HashMap<Integer, T>();
    } // end of AdjMatrix()
    
    
    public void addVertex(T vertLabel) {
    	// Check that vertex does not exists
		if (labels.containsKey(vertLabel)) {
			return;
		}
    	
    	// Check if array is big enough
    	if ( !(verticesCounter < size) ) {
			// Increase the size of the array
			size *= ARRAY_SIZE_MULTIPLIER;
			int newMatrix[][] = new int[size][size];
			
			// Copy contents of old array to new array
			for (int i = 0; i < size/2; i++) {
				for (int j = 0; j < size/2; j++) {
					newMatrix[i][j] = matrix[i][j];
				}
			}
			matrix = newMatrix;			
		}
		
		labels.put(vertLabel, verticesCounter);
		indices.put(verticesCounter, vertLabel);
		verticesCounter++;
	} // end of addVertex()
	
    
    public void addEdge(T srcLabel, T tarLabel) {
    	// Check that vertices exist
    	if ( !labels.containsKey(tarLabel) && !labels.containsKey(srcLabel)) {
    		System.err.println("A vertex does not exist");
    		throw new IllegalArgumentException("Vertex does not exist");
		}
    	
    	// Set the edge on both "rows" of the matrix
    	matrix[labels.get(srcLabel)][labels.get(tarLabel)] = 1;
    	matrix[labels.get(tarLabel)][labels.get(srcLabel)] = 1;			
    } // end of addEdge()
	

    public ArrayList<T> neighbours(T vertLabel) {
    	if ( !labels.containsKey(vertLabel) ) {
    		System.err.println("Vertex does not exist");
    		throw new IllegalArgumentException("Vertex does not exist");
    	}
    	
        ArrayList<T> neighbours = new ArrayList<T>();
		final int indexOfV = labels.get(vertLabel);
		
		// Iterate through all vertices aka the neighbours of V (Length of |V|)
		for (int i = 0; i < verticesCounter; i ++) {
			// Check for neighbour (value is 1)
			if (matrix[indexOfV][i] == 1) {
				neighbours.add(indices.get(i));
			}
		}  	
        return neighbours;
    } // end of neighbours()
    
    /***
     * Flatten out the position of the vertex - not truly removing it (a.k.a it still exists but with no reference).
     * An ArrayList would easily resolve this issue
     */
    public void removeVertex(T vertLabel) {
    	if ( !labels.containsKey(vertLabel) ) {
        	System.err.println("Vertex does not exist");
    		throw new IllegalArgumentException("Vertex does not exist");
        }
    	
		final int indexOfV = labels.get(vertLabel);
		
		// Iterate through all vertices (Length of |V|)
		for (int i = 0; i < verticesCounter; i ++) {
			// 0 out the row
			matrix[indexOfV][i] = 0;
		}
		// Remove column reference from vertices
		for (int i = 0; i < verticesCounter; i++) {
			matrix[i][indexOfV] = 0;
		}
		
		// Remove from both HashMaps (order sensitive) 
		indices.remove(labels.get(vertLabel));
		labels.remove(vertLabel);
		
    } // end of removeVertex()
	
    
    public void removeEdge(T srcLabel, T tarLabel) {
    	// Check if vertices exist
    	if ( !labels.containsKey(tarLabel) && !labels.containsKey(srcLabel)) {
    		System.err.println("A vertex does not exist");
    		throw new IllegalArgumentException("A vertex does not exist");    		
    	}
    	
		// Check if there is an edge between vertices 
		if ( (matrix[labels.get(srcLabel)][labels.get(tarLabel)] == 1) && 
				(matrix[labels.get(tarLabel)][labels.get(srcLabel)] == 1) ) {
			// Edge has been found, remove it (i.e. both references to 0)
			matrix[labels.get(srcLabel)][labels.get(tarLabel)] = 0;
			matrix[labels.get(tarLabel)][labels.get(srcLabel)] = 0;
		}
		else {
			System.err.println("No edge to remove");
		}
    } // end of removeEdges()
	
    
    public void printVertices(PrintWriter os) {
    	for (T label : labels.keySet()) {
    		os.printf("%s ", label.toString());
    	}
    	os.println();
    } // end of printVertices()
	
    
    public void printEdges(PrintWriter os) {
    	// Run through vertices, act on any edges (i.e. found a 1)
    	for (int i = 0; i < verticesCounter; i++) {
    		for (int j = 0; j < verticesCounter; j++) {
    			// Locate edges
    			if (matrix[i][j] == 1) {
    				// Print the corresponding Label for Vertex i & j
    				os.printf("%s %s\n", indices.get(i).toString(), indices.get(j).toString());
    			}
    		}
    	}
    } // end of printEdges()
    
    
    public int shortestPathDistance(T vertLabel1, T vertLabel2) {    	
    	if ( !(labels.containsKey(vertLabel1)) && !(labels.containsKey(vertLabel2)) ) {
    		System.err.println("A vertex does not exist");
    		throw new IllegalArgumentException("A vertex does not exist");
    	}
    	
		// TODO Jeff has said we are allowed to use the native Queue implementations
		LinkedList<Pair> queue = new LinkedList<Pair>();
		ArrayList<T> checked = new ArrayList<T>();
		int neighbourDistance = 0;
		
		queue.add(new Pair(vertLabel1, 0));
		checked.add(vertLabel1);
		
		while (queue.size() > 0)
		{
			Pair node = queue.pop();
			
			if (node.vertex.equals(vertLabel2)) {
				return node.distance;
			}
			    			
			// Run through the neighbours of the current node and add them to the 
			// queue if they haven't been checked already
			neighbourDistance = node.distance += 1;
			for (T neighbour : neighbours(node.vertex)) {
				if ( !checked.contains(neighbour) ) {
					checked.add(neighbour);
					queue.add(new Pair(neighbour, neighbourDistance));
				}
			}
    	}
    	
        // if we reach this point, source and target are disconnected
        return disconnectedDist;    	
    } // end of shortestPathDistance()
    
    class Pair
	{
		public T vertex;
		public int distance;
		public Pair(T vertex, int distance) {
			this.vertex = vertex;
			this.distance = distance;
		}
	}
    
} // end of class AdjMatrix
