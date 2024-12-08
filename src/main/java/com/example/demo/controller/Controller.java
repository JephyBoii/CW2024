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

public class Controller implements LevelParent.Listener, MenuScreen.Listener {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private static final int PLAYER_INITIAL_HEALTH = 10;
	private final Stage stage;
	private MenuScreen menu;

	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {

			stage.show();
			this.menu = new MenuScreen(LEVEL_ONE_CLASS_NAME, PLAYER_INITIAL_HEALTH);
			menu.setMenuListener(this);
			stage.setScene(menu.getStart());

	}

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

	@Override
	public void gameEnded(boolean n) {
		stage.setScene(menu.getEnd(n));
	}
}
