package org.snake.utils;

import java.util.function.Consumer;

public interface FList<A> {
    A head();
    FList<A> tail();

    // Utils methods
    A getLast();
    FList<A> removeLast();

    void foreach(Consumer<A> consumer);

}