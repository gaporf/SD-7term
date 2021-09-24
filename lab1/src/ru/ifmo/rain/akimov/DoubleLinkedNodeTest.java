package ru.ifmo.rain.akimov;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DoubleLinkedNodeTest {
    @Test
    public void createHeadAndTail() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
    }

    @Test
    public void insertValue() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final DoubleLinkedNode<Integer> newValue = headAndTail.insertNext(1337);
        assertThat(newValue.getValue()).isEqualTo(1337);
    }

    @Test
    public void severalInsertions() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        for (int i = 1; i <= 100; i++) {
            final DoubleLinkedNode<Integer> newValue = headAndTail.insertNext(i * i);
            assertThat(newValue.getValue()).isEqualTo(i * i);
        }
    }

    @Test
    public void insertAndRemoveValue() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final DoubleLinkedNode<Integer> newValue = headAndTail.insertNext(34381);
        assertThat(newValue.isRemoved()).isFalse();
        headAndTail.removePrev();
        assertThat(newValue.isRemoved()).isTrue();
    }

    @Test
    public void severalInsertionsAndRemovals() {
        final List<DoubleLinkedNode<Integer>> values = new ArrayList<>();
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        for (int i = 1; i <= 100; i++) {
            values.add(headAndTail.insertNext(3 * i));
            assertThat(values.get(values.size() - 1).getValue()).isEqualTo(3 * i);
        }
        for (int i = 0; i < 100; i++) {
            headAndTail.removePrev();
            for (int j = 0; j < 100; j++) {
                assertThat(values.get(j).isRemoved()).isEqualTo(j <= i);
            }
        }
    }

    @Test
    public void pushToFront() {
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final DoubleLinkedNode<Integer> firstValue = headAndTail.insertNext(1);
        final DoubleLinkedNode<Integer> secondValue = headAndTail.insertNext(2);
        firstValue.pushToFront(headAndTail, 5);
        headAndTail.removePrev();
        assertThat(secondValue.isRemoved()).isTrue();
        assertThat(firstValue.isRemoved()).isFalse();
    }

    @Test
    public void firstLRUCacheTest() {
        final int capacity = 10;
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final List<DoubleLinkedNode<Integer>> insertedValues = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            insertedValues.add(headAndTail.insertNext(5 + i));
            if (i >= capacity) {
                headAndTail.removePrev();
            }
        }
        for (int i = 0; i < 100; i++) {
            assertThat(insertedValues.get(i).isRemoved()).isEqualTo(i < 90);
        }
    }

    @Test
    public void secondLRUCacheTest() {
        final int capacity = 10;
        final DoubleLinkedNode<Integer> headAndTail = new DoubleLinkedNode<>();
        final List<DoubleLinkedNode<Integer>> insertedValues = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            insertedValues.add(headAndTail.insertNext(-i));
            if (i % 7 == 0) {
                final int index = (i / 7) % insertedValues.size();
                final DoubleLinkedNode<Integer> movedValue = insertedValues.get(index);
                insertedValues.remove(index);
                movedValue.pushToFront(headAndTail, 2021);
                insertedValues.add(movedValue);
            }
            if (i >= capacity) {
                headAndTail.removePrev();
                insertedValues.remove(0);
            }
        }
        for (DoubleLinkedNode<Integer> insertedValue : insertedValues) {
            assertThat(insertedValue.isRemoved()).isFalse();
        }
    }
}
