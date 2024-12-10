package com.example.demo;

import javafx.scene.image.*;

/**
 * abstract display/image class. basic structure of all actors
 */

public abstract class ActiveActor extends ImageView {
	
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * takes arious values to build an actor and position them correctly within a scene (level)
	 * @param imageName name of the image for this image view to set its image to. passed from various actors.
	 * @param imageHeight height of the image for image view to set its image to.
	 * @param initialXPos initial x position for image view to place its image relative to the scene.
	 * @param initialYPos initial y position for image view to place its image relative to the scene.
	 */

	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		//this.setImage(new Image(IMAGE_LOCATION + imageName));
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}

	/**
	 * abstract function which determines the actor's next action. called continuously while a level is running.
	 */

	public abstract void updatePosition();

	/**
	 * basic horizontal movement function. sets an actors position to a new position and is affected everything updatePosition() is called.
	 * @param horizontalMove translates an actor's current x position relative to the scene with this value.
	 */

	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}

	/**
	 * basic vertical movement function.
	 * @param verticalMove translates an actor's current y position relative to the scene with this value.
	 */

	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
