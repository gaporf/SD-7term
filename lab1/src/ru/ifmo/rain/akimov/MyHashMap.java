package ru.ifmo.rain.akimov;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyHashMap<K, V> {
    private class KeyNodePair {
        public final K key;
        public final DoubleLinkedNode<V> node;

        public KeyNodePair(final K key, final DoubleLinkedNode<V> node) {
            this.key = key;
            this.node = node;
        }
    }

    final int BUCKET_SIZE = 10;
    final DoubleLinkedNode<V> headAndTail;
    List<List<KeyNodePair>> buckets = new ArrayList<>();
    int n = 10;

    public MyHashMap(final DoubleLinkedNode<V> headAndTail) {
        assert headAndTail != null;
        this.headAndTail = headAndTail;
        for (int i = 0; i < n; i++) {
            buckets.add(new ArrayList<>());
            for (int j = 0; j < BUCKET_SIZE; j++) {
                buckets.get(i).add(null);
            }
        }
    }

    private void deleteRemovedNodes(final int bucketNum) {
        assert 0 <= bucketNum && bucketNum < n;
        final List<KeyNodePair> curBucket = buckets.get(bucketNum);
        for (int j = 0; j < BUCKET_SIZE; j++) {
            final KeyNodePair pair = curBucket.get(j);
            if (pair != null && pair.node.isRemoved()) {
                curBucket.set(j, null);
            }
        }
        assert buckets.get(bucketNum).stream().allMatch(
                keyNodePair -> keyNodePair == null || !keyNodePair.node.isRemoved());
    }

    private int getHashCode(final Object object, final int mod) {
        assert mod >= n;
        int hashCode = object.hashCode() % mod;
        if (hashCode < 0) {
            hashCode += mod;
        }
        assert 0 <= hashCode && hashCode < mod;
        return hashCode;
    }

    private void increaseN() {
        int m = n;
        while (true) {
            m *= 2;
            final List<List<KeyNodePair>> newBuckets = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                newBuckets.add(new ArrayList<>());
            }
            boolean isEnough = true;
            for (int i = 0; i < n && isEnough; i++) {
                final List<KeyNodePair> curBucket = buckets.get(i);
                for (int j = 0; j < BUCKET_SIZE; j++) {
                    final KeyNodePair pair = curBucket.get(j);
                    if (pair != null) {
                        int newHash = getHashCode(pair.key, m);
                        newBuckets.get(newHash).add(pair);
                        if (newBuckets.get(newHash).size() >= BUCKET_SIZE) {
                            isEnough = false;
                            break;
                        }
                    }
                }
            }
            if (!isEnough) {
                continue;
            }
            for (int i = 0; i < m; i++) {
                while (newBuckets.get(i).size() < BUCKET_SIZE) {
                    newBuckets.get(i).add(null);
                }
            }
            n = m;
            buckets = newBuckets;
            break;
        }
    }

    public V put(final K key, final V value) {
        final V oldValue = get(key);
        while (true) {
            int bucketNum = getHashCode(key, n);
            deleteRemovedNodes(bucketNum);
            final List<KeyNodePair> curBucket = buckets.get(bucketNum);
            for (int j = 0; j < BUCKET_SIZE; j++) {
                final KeyNodePair keyNodePair = curBucket.get(j);
                if (keyNodePair != null && keyNodePair.key.equals(key)) {
                    final DoubleLinkedNode<V> curNode = keyNodePair.node;
                    final V returnValue = curNode.getValue();
                    curNode.pushToFront(headAndTail, value);
                    assert Objects.equals(oldValue, returnValue);
                    return returnValue;
                }
            }
            for (int j = 0; j < BUCKET_SIZE; j++) {
                final KeyNodePair pair = curBucket.get(j);
                if (pair == null) {
                    curBucket.set(j, new KeyNodePair(key, headAndTail.insertNext(value)));
                    assert oldValue == null;
                    return null;
                }
            }
            increaseN();
        }
    }

    public V get(final K key) {
        int bucketNum = getHashCode(key, n);
        deleteRemovedNodes(bucketNum);
        final List<KeyNodePair> curBucket = buckets.get(bucketNum);
        for (int j = 0; j < BUCKET_SIZE; j++) {
            final KeyNodePair pair = curBucket.get(j);
            if (pair != null && pair.key.equals(key)) {
                pair.node.pushToFront(headAndTail, pair.node.getValue());
                return pair.node.getValue();
            }
        }
        return null;
    }
}
