package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Button;

public class MenuScreen {

    public interface Listener {
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

    public MenuScreen(String levelName, int health){

        this.LevelOneName = levelName;
        this.PlayerInitialHealth = health;
        this.root = new Group();
        this.scene = new Scene(root);
        this.winimage = new WinImage();
        this.gameoverimage = new GameOverImage();
        this.mainmenuimage = new MainMenuImage();

    }

    EventHandler<ActionEvent> start = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            listener.fetch(LevelOneName, PlayerInitialHealth);
        }
    };

    EventHandler<ActionEvent> end = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            System.exit(0);
        }
    };

    public void setMenuListener(MenuScreen.Listener listener) {
        this.listener = listener;
    }


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
