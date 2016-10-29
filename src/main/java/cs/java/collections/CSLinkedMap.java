package cs.java.collections;

public interface CSLinkedMap<K, V> extends CSMap<K, V> {

    V getValue(int index);

    int indexOf(K key);

}