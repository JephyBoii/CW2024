package com.example.demo;

/**
 * extends projectile, the bullets that the boss shoots out and damages the user plane upon contact.
 */

public class BossProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "bossfire.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * initializes values which are passed to the superclass
	 * @param initialYPos
	 */

	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * updates the projectile's next move, which is horizontally towards the player
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * calls function which updates the actor's current position
	 */
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
