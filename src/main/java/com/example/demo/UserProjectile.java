package com.example.demo;

/**
 * class extending projectile.java. fired by userplane.java. is fired in bursts of 2 at a time with a 2 frame gap between them and a 10 frame gap between bursts.
 */

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final double VELOCITY_MULTIPLIER = 2;
	private double HORIZONTAL_VELOCITY = 10;
	private static final double MAX_VELOCITY = 50;

	/**
	 * initializes the class by passing values to  the superclass.
	 * @param initialXPos initial x position for image view to place its image relative to the scene.
	 * @param initialYPos initial y position for image view to place its image relative to the scene.
	 */

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * updates the current position of the bullet, initially moving at a velocity of 10 while accelerating rapidly until max velocity.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		if (HORIZONTAL_VELOCITY < MAX_VELOCITY) {
			HORIZONTAL_VELOCITY += VELOCITY_MULTIPLIER;
		}
	}

	/**
	 * called by levelparent.java to update the position of a userprojectile.
	 */
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
