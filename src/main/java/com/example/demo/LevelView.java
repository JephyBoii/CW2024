package com.example.demo;

import javafx.scene.Group;

/**
 * level view class which displays various ui elements such as the boss shield and player health.
 */

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 10;
	private static final int SHIELD_X_POSITION = 700;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final HeartDisplay heartDisplay;
	private final ShieldImage shieldImage;
	private boolean tryOnce = false;

	/**
	 * instantiates the level with a root to work with and integer of hearts to display.
	 * instantiates a heart display and shield image class with the positions.
	 * @param root
	 * @param heartsToDisplay
	 */
	
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
	}

	/**
	 * function to display number of hearts to represent player health.
	 */
	
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}

	/**
	 * function to appropriately adjust player heart display to reflect change in player health.
	 * @param heartsRemaining
	 */

	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

	/**
	 * function to show boss shield when necessary.
	 * called when the boss shield should be activated.
	 */

	public void showShield() {
		if (!tryOnce) {
			root.getChildren().add(shieldImage);
			tryOnce = true;
		}
		shieldImage.showShield();
	}

	/**
	 * function to hide the boss shield when necessary.
	 * called when the boss shield should be deactivated.
	 */

	public void hideShield() {
		shieldImage.hideShield();
	}

}
