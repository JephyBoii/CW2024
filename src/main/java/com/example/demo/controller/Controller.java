package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.example.demo.MenuScreen;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

/**
 * controls the entire flow of the game, changing the scene between levels and menus, instantiates all levels and menus
 */

public class Controller implements LevelParent.Listener, MenuScreen.Listener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private static final int PLAYER_INITIAL_HEALTH = 10;
	private final Stage stage;
	private MenuScreen menu;

	/**
	 *declares the stage
	 * @param stage takes a stage from main. primary display of window.
	 */

	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * shows the stage and displays the first scene: the main menu
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */

	public void launchGame() throws SecurityException, IllegalArgumentException {

		stage.show();
		this.menu = new MenuScreen(LEVEL_ONE_CLASS_NAME, PLAYER_INITIAL_HEALTH);
		menu.setMenuListener(this);
		stage.setScene(menu.getStart());

	}

	/**
	 * the call to instantiate a new level, takes data that is the class name, calls its constructor and passes appropriate values (health). sets the scene and begins the level.
	 * @param className name of the class of the level for its constructor to be called in the function.
	 * @param health value for the player health, carries over between levels.
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */

	private void goToLevel(String className, int health) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> myClass = Class.forName(className);
		Constructor<?> constructor = myClass.getConstructor(double.class, double.class, int.class);
		LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), health);
		myLevel.setListener(this);
		Scene scene = myLevel.initializeScene();
		stage.setScene(scene);
		myLevel.startGame();

	}

	/**
	 * called by listened classes StartMenu.java and LevelParent.java. passes the next level name from those classes.
	 * @param data name of the class of the level for its constructor to be called in the function.
	 * @param health value for the player health, carries over between levels.
	 */

	@Override
	public void fetch(String data, int health) {
		try {
			goToLevel(data, health);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();

			Throwable exec = e.getCause();
			exec.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * called LevelParent.java to set the scene to a win or lose menu screen. passes a boolean to decide which screen to display.
	 * @param n boolean determining whether the menu should display a win or loss screen.
	 */

	@Override
	public void gameEnded(boolean n) {
		stage.setScene(menu.getEnd(n));
	}
}
