import java.io.*;
import java.util.*;

/**
 * Adjacency list implementation for the FriendshipGraph interface.
 *
 * Your task is to complete the implementation of this class.  You may add methods, but ensure your modified class compiles and runs.
 *
 * @author Jeffrey Chan, 2016.
 */
public class AdjList <T extends Object> implements FriendshipGraph<T> {

    /**
     * HashMap of vertex names to their linked list headers.
     * Can be replaced with an array list, but will need a method
     * of searching it.
     */
    private HashMap<T, LinkedVertex> vertices;

    /**
	 * Contructs empty graph and initializes the HashMap.
	 */
    public AdjList() {
        vertices = new HashMap<T, LinkedVertex>();
    }


    public void addVertex(T vertLabel) {

        // add a new key, value pair to the map
        vertices.put(vertLabel, new LinkedVertex(vertLabel));

        // if we move to arraylist, use this
        // vertices.add(new LinkedVertex(vertLabel));

    }


    public void addEdge(T srcLabel, T tarLabel) {

        LinkedVertex src = (LinkedVertex) vertices.get(srcLabel);
        LinkedVertex tar = (LinkedVertex) vertices.get(tarLabel);

        if (src == null || tar == null) {
            System.out.println("Source or target vertex was not found.");
            return;
        }

        LinkedVertex curr = src;
        // go to the end of the linked list to find the last vertex
        while (curr.getNext() != null) {
            // kill if target is already present
            if (areEqual(curr.getLabel(), tarLabel)) return;
            curr = curr.getNext();
        }

        curr.setNext(tar.copy());

        // graphs are undirected, so do the same for tar -> src

        curr = tar;
        while (curr.getNext() != null) {
            if (areEqual(curr.getLabel(), srcLabel)) return;
            curr = curr.getNext();
        }

        curr.setNext(src.copy());

    }


    public ArrayList<T> neighbours(T vertLabel) {
        ArrayList<T> neighbours = new ArrayList<T>();

        LinkedVertex curr = vertices.get(vertLabel);

        while (curr.getNext() != null) {
            curr = curr.getNext();
            neighbours.add((T) curr.getLabel());
        }

        return neighbours;
    }


    public void removeVertex(T vertLabel) {

        // remove all edges containing vertLabel
        for (T vert : neighbours(vertLabel)) {
            removeEdge(vertLabel, vert);
        }

        // should now be OK to remove it from the map
        vertices.remove(vertLabel);

    }


    public void removeEdge(T srcLabel, T tarLabel) {

        LinkedVertex src = (LinkedVertex) vertices.get(srcLabel);
        LinkedVertex tar = (LinkedVertex) vertices.get(tarLabel);

        if (src == null || tar == null) {
            System.out.println("Source or target vertex was not found.");
            return;
        }

        LinkedVertex curr = src.getNext();
        LinkedVertex prev = src;

        while (curr != null && !areEqual(curr.getLabel(), tarLabel)) {
            prev = curr;
            curr = curr.getNext();
        }

        if (curr == null) {
            System.out.println("Could not find target label in source list.");
            return;
        }

        // close the gap
        prev.setNext(curr.getNext());

        // repeat for tar -> src

        curr = tar.getNext();
        prev = tar;

        while (curr != null && !areEqual(curr.getLabel(), srcLabel)) {
            prev = curr;
            curr = curr.getNext();
        }

        if (curr == null) {
            System.out.println("Could not find source label in target list.");
            return;
        }

        prev.setNext(curr.getNext());

    }


    public void printVertices(PrintWriter os) {

        for (Map.Entry<T, LinkedVertex> entry : vertices.entrySet()) {

            os.println(entry.getKey());

        }

    }


    public void printEdges(PrintWriter os) {

        for (Map.Entry<T, LinkedVertex> entry : vertices.entrySet()) {

            os.print(entry.getKey() + ":");

            LinkedVertex curr = vertices.get(entry.getKey()).getNext();

            while (curr != null) {
                os.print(" -> " + curr.getLabel());
                curr = curr.getNext();
            }

            os.println("");

        }

    }


    public int shortestPathDistance(T vertLabel1, T vertLabel2) {

        return shortestPathDistance(vertLabel1, vertLabel2,
                                    new ArrayList<String>());

    }

    public int shortestPathDistance(T startVertex, T findVertex,
                                    ArrayList<String> checked) {

        // list of distances from all child nodes to findVertex,
        // shortest will be determined and propagate back up the
        // recursion
        ArrayList<Integer> distances = new ArrayList<Integer>();
        LinkedVertex curr = (LinkedVertex) vertices.get(startVertex);

        // do not check the starting node, only the rest
        curr = curr.getNext();

        // check all children
        while (curr != null) {

            // if findVertex is found, return 1
            // (1 step from startVertex)
            if (areEqual(curr.getLabel(), findVertex)) {
                return 1;
            }

            // if not already checked, do the recursion and save to
            // distances
            if (!checked.contains(curr.getLabel().toString())) {

                checked.add(curr.getLabel().toString());
                distances.add(shortestPathDistance((T) curr.getLabel(),
                              findVertex, checked));

            }

            curr = curr.getNext();
        }

        // no children (and findVertex not found), must be disconnected.
        if (distances.size() == 0) return disconnectedDist;

        // find the minimum of the distances (that is not -1)
        int min = -1;
        for (int i = 0; i < distances.size(); i++) {
            int val = (int) distances.get(i);
            // always allow the val to replace min if it is -1
            if (val > 0 && (min > val || min == -1)) min = val;
        }

        // all chilren are disconnected
        if (min == disconnectedDist) return disconnectedDist;

        // findVertex can be found in min+1 steps from startVertex
        return min + 1;

    }


    /**
     * Compare two object's string representations to see if they are equal
     */
    private boolean areEqual(Object vertLabel1, Object vertLabel2) {
        return vertLabel1.toString().equals(vertLabel2.toString());
    }

}

class LinkedVertex <T extends Object> {

    private LinkedVertex<T> next;
    private T label;


    public LinkedVertex(T label) {
        this.next = null;
        this.label = label;
    }


    public void setNext(LinkedVertex next) {
        this.next = next;
    }


    public LinkedVertex<T> getNext() {
        return this.next;
    }


    public T getLabel() {
        return this.label;
    }


    public void setLabel(T label) {
        this.label = label;
    }


    public LinkedVertex<T> copy() {
        return new LinkedVertex(this.label);
    }

}
