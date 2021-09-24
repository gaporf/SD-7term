package ru.ifmo.rain.akimov;

import java.util.Objects;

public class LRUCache<K, V> {
    private final int capacity;
    private int curElements = 0;
    private final DoubleLinkedNode<V> headAndTail = new DoubleLinkedNode<>();
    private final MyHashMap<K, V> map = new MyHashMap<>(headAndTail);

    public LRUCache() {
        this(30);
    }

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
    }

    public V get(final K key) {
        return map.get(key);
    }

    public V put(final K key, final V value) {
        final V oldValue = map.get(key);
        final V returnValue = map.put(key, value);
        if (returnValue == null) {
            curElements++;
            if (curElements > capacity) {
                headAndTail.removePrev();
                curElements--;
            }
        }
        assert Objects.equals(oldValue, returnValue) && Objects.equals(map.get(key), value);
        return returnValue;
    }
}
