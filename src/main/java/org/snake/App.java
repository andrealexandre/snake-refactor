package org.snake;

import org.snake.game.GameMenu;
import org.snake.settings.GameConfiguration;

public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        String menuTitle = "Snake Game Menu";
        final GameConfiguration gameConfiguration = new GameConfiguration();
        new GameMenu(menuTitle, gameConfiguration).start();

        System.out.println(new App().getGreeting());
    }
}