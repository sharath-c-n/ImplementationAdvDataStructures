package cs6301.g26;

/**
 * DisjointSet: ADT for sets
 * @author : Sharath
 * 03/10/2017
 */
public interface DisjointSet<T>  {
        Object findSet(T u);

        void union(T u, T v);

}
