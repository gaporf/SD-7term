package ru.ifmo.rain.akimov;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MyHashMapTest {

    @Test
    public void createHashMap() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
    }

    @Test
    public void putHashMap() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        final Integer result = map.put(4, 16);
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void getNull() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        final Integer result = map.get(-5);
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void putAndGet() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        Integer result = map.put(16, 256);
        assertThat(result).isEqualTo(null);
        result = map.get(16);
        assertThat(result).isEqualTo(256);
    }

    @Test
    public void severalPutsAndGets() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        for (int i = 0; i < 100; i++) {
            final Integer result = map.put(i, i * i);
            assertThat(result).isNull();
        }
        for (int i = 0; i < 100; i++) {
            final Integer result = map.get(i);
            assertThat(result).isEqualTo(i * i);
        }
    }

    @Test
    public void manyPutsAndGets() {
        final DoubleLinkedNode<Long> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Long, Long> map = new MyHashMap<>(headAndTail);
        for (long i = 0; i < 1_000_000; i++) {
            final Long result = map.put(i, 2 * i * i);
            assertThat(result).isNull();
        }
        for (long i = 0; i < 1_000_000; i++) {
            final Long result = map.get(i);
            assertThat(result).isEqualTo(2 * i * i);
        }
    }

    @Test
    public void putSameKey() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        map.put(5, 5);
        Integer result = map.put(5, 10);
        assertThat(result).isEqualTo(5);
        result = map.get(5);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void removeLRUElements() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final MyHashMap<Integer, Integer> map = new MyHashMap<>(headAndTail);
        final int capacity = 20;
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
            if (i >= capacity) {
                headAndTail.removePrev();
            }
        }
        for (int i = 0; i < 100; i++) {
            assertThat(map.get(i)).isEqualTo(i < 100 - capacity ? null : i);
        }
    }
}
