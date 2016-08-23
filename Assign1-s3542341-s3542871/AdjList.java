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
    private LinkedVertex[] vertices;

    /**
     * Amount of vertices currently in the list (not always equal to list
     * length)
     */
    private int numVertices;

    /**
     * The amount of 'room' to make in the vertices list each time it is
     * filled
     */
    private int STAGGER = 10;

    /**
	 * Contructs empty graph and initializes the HashMap.
	 */
    public AdjList() {
        vertices = new LinkedVertex[STAGGER];

        numVertices = 0;
    }

    /**
     * Get the index of a LinkedVertex key in the array
     * -1 if not found
     */
    public int indexOf(LinkedVertex lv) {

        return indexOf((T) lv.getLabel());

    }

    public int indexOf(T label) {

        for (int i = 0; i < numVertices; i++) {

            if (areEqual(vertices[i].getLabel(), label)) return i;

        }

        // will only reach here if not found
        return -1;

    }

    public boolean hasKey(T label) {

        return indexOf((T) label) != -1;

    }

    public LinkedVertex vertFromLabel(T label) {

        int i = indexOf((T) label);

        if (i != -1) return vertices[i];
        else return null;

    }


    public void addVertex(T vertLabel) {

        // ensure label is not already in vertices array
        if (indexOf(vertLabel) == -1) {

            // if the array is out of space, stagger the length of it
            // some more
            if (numVertices % STAGGER == 0 && numVertices >= STAGGER) {

                LinkedVertex[] oldVertices = vertices;
                vertices = new LinkedVertex[numVertices + STAGGER];

                System.arraycopy(oldVertices, 0, vertices, 0,
                    oldVertices.length);

            }

            vertices[numVertices++] = new LinkedVertex(vertLabel);

        }

    }


    public void addEdge(T srcLabel, T tarLabel) {

        if (!hasKey(srcLabel)) {
            System.out.println("srclabel " + srcLabel.toString() + " not in hashmap");
        }

        if (!hasKey(tarLabel)) {
            System.out.println("tarlabel " + tarLabel.toString() + " not in hashmap");
        }

        LinkedVertex src = vertFromLabel(srcLabel);
        LinkedVertex tar = vertFromLabel(tarLabel);

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

        LinkedVertex curr = vertFromLabel(vertLabel);

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
        int i = indexOf(vertLabel);

        // fill in the gap with the very last vertex, then
        // decrement the number of vertices
        vertices[i] = vertices[--numVertices];

        // do not need to actually remove the last vertex because it shouldn't
        // be accessed (as long as numVertices is being used)

        if (vertLabel.toString().equals("997")) {
            System.out.println(Arrays.toString(Thre‌​ad.currentThread().ge‌​tStackTrace()));
        }

    }


    public void removeEdge(T srcLabel, T tarLabel) {

        LinkedVertex src = vertFromLabel(srcLabel);
        LinkedVertex tar = vertFromLabel(tarLabel);

        if (src == null || tar == null) {
            System.out.println("Source or target vertex was not found.");
            System.out.println("For src " + srcLabel.toString() + " and tar " + tarLabel.toString());
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

        for (int i = 0; i < numVertices; i++) {

            os.print(vertices[i].getLabel() + " ");

        }

        os.println();

    }


    public void printEdges(PrintWriter os) {

        for (int i = 0; i < numVertices; i++) {

            LinkedVertex curr = vertices[i].getNext();

            while (curr != null) {
                os.print(vertices[i].getLabel() + " " + curr.getLabel());
                curr = curr.getNext();
            }

            os.println();

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
        LinkedVertex curr = vertFromLabel(startVertex);

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
