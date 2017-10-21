package cs6301.g26;

/**
 * TreeEntry:
 *
 * @author : Sharath
 * 12/10/2017
 */
public interface TreeEntry<T>  {
    TreeEntry<T> getLeft();
    TreeEntry<T> getRight();
    T getKey();
}
