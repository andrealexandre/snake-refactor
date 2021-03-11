package org.snake.utils;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

@SuppressWarnings({"unchecked cast", "rawtypes"})
public class FNil<A> implements FList<A> {
    private static final FList<?> INSTANCE = new FNil();

    public static <A> FNil<A> instance() {
        return (FNil<A>) INSTANCE;
    }

    private FNil() { }

    @Override
    public A head() {
        throw new NoSuchElementException("no element in this list");
    }

    @Override
    public FList<A> tail() {
        return instance();
    }

    @Override
    public A getLast() {
        return head();
    }

    @Override
    public FList<A> removeLast() {
        return instance();
    }

    @Override
    public void foreach(Consumer<A> consumer) { }
}
