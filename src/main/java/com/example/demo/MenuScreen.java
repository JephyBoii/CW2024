package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;

/**
 * menu screen main display. displays the main menu, win screen and game over screen.
 * includes buttons which start game, restart game and exit game.
 * includes an interface to communicate with controller.java.
 */

public class MenuScreen {

    /**
     * listener interface to fetch data to pass to next level function of controller.java.
     */

    public interface Listener {

        /**
         * used by listened class to send a string data and health integer value to listener class.
         * @param Data name of level.
         * @param health user health value.
         */

        void fetch(String Data, int health);
    }
    private Listener listener;

    private final String LevelOneName;
    private final int PlayerInitialHealth;

    private final Group root;
    private final Scene scene;

    private final WinImage winimage;
    private final GameOverImage gameoverimage;
    private final MainMenuImage mainmenuimage;

    /**
     * instantiates the menuscreen class by passing initial health and level name values.
     * creates a new group and scene as well as images for the main, win and lose menu screen.
     * @param levelName name of first level to be called when event occurs.
     * @param health initial user health value to be set for level instantiation.
     */

    public MenuScreen(String levelName, int health){

        this.LevelOneName = levelName;
        this.PlayerInitialHealth = health;
        this.root = new Group();
        this.scene = new Scene(root);
        this.winimage = new WinImage();
        this.gameoverimage = new GameOverImage();
        this.mainmenuimage = new MainMenuImage();

    }

    /**
     * event handler to call fetch() and pass level name and initial health value to controller.java.
     */

    EventHandler<ActionEvent> start = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            listener.fetch(LevelOneName, PlayerInitialHealth);
        }
    };

    /**
     * event handler to exit game.
     */

    EventHandler<ActionEvent> end = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.exit(0);
        }
    };

    /**
     * sets an implementing class as a listener of the interface.
     * @param listener assigns as a listener to the implementing class.
     */

    public void setMenuListener(MenuScreen.Listener listener) {
        this.listener = listener;
    }

    /**
     * returns a scene of a win or lose screen.
     * determined by its boolean n value.
     * restart game takes user to first level.
     * exit game exits the program.
     * buttons manually positioned and styled.
     * @param n determines whether a win or loss screen should be displayed.
     * @return win or loss screen scene.
     */

    public Scene getEnd(boolean n) {

        if (n) {
            root.getChildren().add(winimage);
            winimage.showWinImage();
        } else {
            root.getChildren().add(gameoverimage);
            gameoverimage.showGameOverImage();
        }

        Button b1 = new Button("restart game");
        b1.setOnAction(start);
        root.getChildren().add(b1);
        b1.setLayoutX(150);
        b1.setLayoutY(300);
        b1.setMinHeight(55);
        b1.setMinWidth(200);

        Button b2 = new Button("exit game");
        b2.setOnAction(end);
        root.getChildren().add(b2);
        b2.setLayoutX(175);
        b2.setLayoutY(365);
        b2.setMinHeight(35);
        b2.setMinWidth(150);

        return scene;

    }

    /**
     * returns a scene of the main menu screen.
     * start game take user to first level.
     * exit game exits the program.
     * buttons are manually positioned and styled.
     * @return main menu screen scene.
     */

    public Scene getStart() {

        root.getChildren().add(mainmenuimage);
        mainmenuimage.showMainMenuImage();

        Button b1 = new Button("start game");
        b1.setOnAction(start);
        root.getChildren().add(b1);
        b1.setLayoutX(550);
        b1.setLayoutY(600);
        b1.setMinHeight(55);
        b1.setMinWidth(200);

        Button b2 = new Button("exit game");
        b2.setOnAction(end);
        root.getChildren().add(b2);
        b2.setMinHeight(30);
        b2.setMinWidth(70);
        b2.setLayoutX(760);
        b2.setLayoutY(625);

        return scene;

    }

}
