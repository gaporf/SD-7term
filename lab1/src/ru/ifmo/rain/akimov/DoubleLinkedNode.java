package ru.ifmo.rain.akimov;

public class DoubleLinkedNode<V> {
    private V value;
    private DoubleLinkedNode<V> prevNode;
    private DoubleLinkedNode<V> nextNode;

    public DoubleLinkedNode() {
        this.value = null;
        prevNode = nextNode = this;
    }

    private DoubleLinkedNode(final V value, final DoubleLinkedNode<V> prevNode, final DoubleLinkedNode<V> nextNode) {
        assert prevNode != null && nextNode != null;
        this.value = value;
        this.prevNode = prevNode;
        this.nextNode = nextNode;
    }

    public V getValue() {
        return value;
    }

    public DoubleLinkedNode<V> insertNext(final V value) {
        assert this.value == null;
        final DoubleLinkedNode<V> oldNextNode = nextNode;
        nextNode = new DoubleLinkedNode<>(value, this, oldNextNode);
        oldNextNode.prevNode = nextNode;
        return nextNode;
    }

    public void pushToFront(final DoubleLinkedNode<V> headAndTail, final V newValue) {
        assert headAndTail.getValue() == null;
        value = newValue;
        final DoubleLinkedNode<V> oldPrevNode = prevNode;
        final DoubleLinkedNode<V> oldNextNode = nextNode;
        oldPrevNode.nextNode = oldNextNode;
        oldNextNode.prevNode = oldPrevNode;
        final DoubleLinkedNode<V> oldHeadNextNode = headAndTail.nextNode;
        headAndTail.nextNode = oldHeadNextNode.prevNode = this;
        this.prevNode = headAndTail;
        this.nextNode = oldHeadNextNode;
    }

    public void removePrev() {
        assert prevNode != null;
        final DoubleLinkedNode<V> oldPrevNode = prevNode;
        prevNode = prevNode.prevNode;
        prevNode.nextNode = this;
        oldPrevNode.prevNode = oldPrevNode.nextNode = null;
    }

    public boolean isRemoved() {
        assert (prevNode == null) == (nextNode == null);
        return prevNode == null;
    }
}
