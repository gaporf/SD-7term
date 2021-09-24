package ru.ifmo.rain.akimov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LRUCacheTest {

    @Test
    public void createLRUCache() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>();
    }

    @Test
    public void returnNull() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>(1337);
        final Integer result = cache.get(5);
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void justPut() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>();
        final Integer result = cache.put(1, 1);
        assertThat(result).isEqualTo(null);
    }

    @Test
    public void putAndGet() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>();
        Integer result = cache.put(1, 1);
        assertThat(result).isNull();
        result = cache.get(1);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void checkCapacity() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>(1);
        cache.put(1, 1);
        cache.put(2, 2);
        Integer result = cache.get(1);
        assertThat(result).isNull();
        result = cache.get(2);
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void checkNullValue() {
        final LRUCache<Integer, Integer> cache = new LRUCache<>();
        cache.put(5, null);
        final Integer result = cache.get(5);
        assertThat(result).isNull();
    }

    private static class KeyValuePair<K, V> {
        public final K key;
        public final V value;
        public KeyValuePair(final K key, final V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Test
    public void stressTest() {
        final int capacity = 100;
        final LRUCache<Integer, Integer> cache = new LRUCache<>(capacity);
        final List<KeyValuePair<Integer, Integer>> realCache = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            if (i % 19 == 5) {
                int index = 50 % realCache.size();
                final KeyValuePair<Integer, Integer> keyValuePair = realCache.get(index);
                realCache.remove(index);
                final Integer result = cache.get(keyValuePair.key);
                assertThat(result).isEqualTo(keyValuePair.value);
                realCache.add(keyValuePair);
            } else if (i % 26 == 13) {
                int index = 35 % realCache.size();
                final KeyValuePair<Integer, Integer> keyValuePair = realCache.get(index);
                realCache.remove(index);
                final Integer result = cache.put(keyValuePair.key, keyValuePair.value + 5);
                assertThat(result).isEqualTo(keyValuePair.value);
                realCache.add(new KeyValuePair<>(keyValuePair.key, keyValuePair.value + 5));
            } else {
                final KeyValuePair<Integer, Integer> keyValuePair = new KeyValuePair<>(i, i);
                final Integer result = cache.put(keyValuePair.key, keyValuePair.value);
                assertThat(result).isNull();
                realCache.add(keyValuePair);
                if (realCache.size() > capacity) {
                    realCache.remove(0);
                }
            }
        }
        for (int i = 0; i < 1_000_000; i++) {
            Integer expectedResult = null;
            for (KeyValuePair<Integer, Integer> integerIntegerKeyValuePair : realCache) {
                if (integerIntegerKeyValuePair.key.equals(i)) {
                    expectedResult = integerIntegerKeyValuePair.value;
                    break;
                }
            }
            assertThat(cache.get(i)).isEqualTo(expectedResult);
        }
    }
}
