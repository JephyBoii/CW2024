package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class which extend image view. creates a new image view of youwin.png to be displayed in menuscreen.java.
 */

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";

	/**
	 * instantiates the image with the image name, its dimensions and various properties of the image.
	 */
	
	public WinImage() {
		setImage(new Image(String.valueOf(getClass().getResource(IMAGE_NAME))));
		setFitWidth(1300);
		setFitHeight(750);
		setLayoutX(0);
		setLayoutY(0);
		setVisible(false);
		setPreserveRatio(true);
	}

	/**
	 * function to set the visibility of the image to true.
	 */
	
	public void showWinImage() {
		setVisible(true);
	}

}
