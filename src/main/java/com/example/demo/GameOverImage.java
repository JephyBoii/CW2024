package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameOverImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/gameover.png";

	public GameOverImage() {
		setImage(new Image(String.valueOf(getClass().getResource(IMAGE_NAME))) );
		setFitWidth(1300);
		setFitHeight(750);
		setLayoutX(0);
		setLayoutY(0);
		setPreserveRatio(true);
		setVisible(false);
	}

	public void showGameOverImage() {
		setVisible(true);
	}

}
