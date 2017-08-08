package lab8;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    private class Node {

        private K key;
        private V value;
        private Node leftNode;
        private Node rightNode;
        Node(K k, V v, Node left, Node right) {
            key  = k;
            value = v;
            leftNode = left;
            rightNode = right;
        }


    }

    public BSTMap() {
        root = null;
        size = 0;
    }

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException("argument to contains() is null");
        }
        return get(key) != null;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if (x == null) {
            return null;
        }

        int cmp = key.compareTo(x.key);

        if (cmp < 0) {
            return get(x.leftNode, key);
        } else if (cmp > 0) {
            return get(x.rightNode, key);
        } else {
            return x.value;
        }
    }

    public int size() {
        return size;
    }

    public void put(K key, V val) {
        size += 1;
        if (key == null) {
            throw new NullPointerException();
        }
        root = put(root, key, val);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, null, null);
        }
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.leftNode  = put(x.leftNode,  key, val);
        } else if (cmp > 0) {
            x.rightNode = put(x.rightNode, key, val);
        } else {
            x.value = val;
        }
        return x;
    }

    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

}
