package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/shield.png";
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setImage(new Image(String.valueOf(getClass().getResource(IMAGE_NAME))));
		this.setVisible(false);
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setFitHeight(200);
		this.setFitWidth(600);
	}

	public void showShield() {
		this.setVisible(true);
	}
	
	public void hideShield() {
		this.setVisible(false);
	}

}
