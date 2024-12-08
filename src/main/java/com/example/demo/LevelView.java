package com.example.demo;

import javafx.scene.Group;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 10;
	private static final int SHIELD_X_POSITION = 700;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final HeartDisplay heartDisplay;
	private final ShieldImage shieldImage;
	private boolean tryOnce = false;
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	public void showShield() {
		if (!tryOnce) {
			root.getChildren().add(shieldImage);
			tryOnce = true;
		}
		shieldImage.showShield();
	}

	public void hideShield() {
		shieldImage.hideShield();
	}

}
