package com.example.demo.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main, where the program starts and runs.
 * instantiates a new controller and passes a new stage.
 */

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Fly to the North Pole in a car and defeat Santa Claus because he gave you coal for Christmas";
	private Controller myController;

	/**
	 * Begins the whole program. sets the stage (window) height and width, its title and disables resizing the stage.
	 * @param stage the main stage (window) of the entire program.
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */

	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}

	/**
	 * main, starts the program by calling launch() from application.
	 * @param args standard main method.
	 */

	public static void main(String[] args) {
		launch();
	}
}