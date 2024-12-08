package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class WinImage extends ImageView {
	
	private static final String IMAGE_NAME = "/com/example/demo/images/youwin.png";
	
	public WinImage() {
		setImage(new Image(String.valueOf(getClass().getResource(IMAGE_NAME))));
		setFitWidth(1300);
		setFitHeight(750);
		setLayoutX(0);
		setLayoutY(0);
		setVisible(false);
		setPreserveRatio(true);
	}
	
	public void showWinImage() {
		setVisible(true);
	}

}
