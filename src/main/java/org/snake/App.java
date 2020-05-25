package org.snake;

import org.snake.game.GameMenu;

public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        new GameMenu();
        System.out.println(new App().getGreeting());
    }
}