package cs6301.g26;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * DisjointSet:
 *
 * @author : Sharath
 * 03/10/2017
 */
public class DisjointSet<T> implements Iterable<DisjointSet.Node> {
    ArrayList<Node> nodes;

    @NotNull
    @Override
    public Iterator<Node> iterator() {
        return nodes.iterator();
    }

    public static class Node<T> {
        T parent;
        T data;

        Node(T data) {
            this.data = data;
            this.parent = data;
        }
    }

    public DisjointSet() {
        nodes = new ArrayList<>();
    }

    public void add(T value) {
        Node node = new Node(value);
        nodes.add(node);
    }

    public static DisjointSet makeSet(Object value) {
        DisjointSet disjointSet = new DisjointSet();
        disjointSet.add(value);
        return disjointSet;
    }

    public DisjointSet union(DisjointSet b) {
        return null;
    }

}
