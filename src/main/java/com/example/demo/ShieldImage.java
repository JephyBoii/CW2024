package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * class which extend image view. creates a new image view of shield.png to be displayed in levelview.java.
 */

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";

	/**
	 * instantiates the image with the image name, its dimensions and various properties of the image.
	 */
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(String.valueOf(getClass().getResource(IMAGE_NAME))));
		this.setVisible(false);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setFitHeight(200);
		this.setFitWidth(600);
	}

	/**
	 * function to set the visibility of the image to true.
	 */

	public void showShield() {
		this.setVisible(true);
	}

	/**
	 * function to set the visibility of the image to false.
	 */
	
	public void hideShield() {
		this.setVisible(false);
	}

}
