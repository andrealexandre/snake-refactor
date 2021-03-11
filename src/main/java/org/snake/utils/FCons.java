package org.snake.utils;

import java.util.function.Consumer;

public class FCons<A> implements FList<A> {

    private final A head;
    private final FList<A> tail;

    public FCons(A head, FList<A> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public A head() {
        return head;
    }

    @Override
    public FList<A> tail() {
        return tail;
    }

    @Override
    public A getLast() {
        if(tail instanceof FNil) {
            return head;
        }
        else {
            return tail.getLast();
        }
    }

    @Override
    public FList<A> removeLast() {
        if(tail instanceof FNil) {
            return FNil.instance();
        } else {
            return new FCons<>(head, tail.removeLast());
        }
    }

    @Override
    public void foreach(Consumer<A> consumer) {
        consumer.accept(head);
        tail().foreach(consumer);
    }
}
