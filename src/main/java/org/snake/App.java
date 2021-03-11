package org.snake;

import org.snake.game.GameMenu;
import org.snake.settings.GameConfiguration;

public class App {

    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        // Date
        String menuTitle = "Snake Game Menu";
        String gameWindowTitle = "Snake";

       // Setup
        final GameConfiguration gameConfiguration = new GameConfiguration();

        new GameMenu(menuTitle, gameConfiguration).start(gameWindowTitle);
    }

}