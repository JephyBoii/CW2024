package com.example.demo;

/**
 * class extending projectile.java. fired by enemyplane.java.
 */

public class EnemyProjectile extends Projectile {
	
	private static final String IMAGE_NAME = "enemyfire.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = -10;

	/**
	 * initializes values by passing to superclass.
	 * @param initialXPos
	 * @param initialYPos
	 */

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * simply moves the projectile constantly horizontally towards the left from its initial position.
	 */

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * calledd by LevelParent.java to update the position of the projectile.
	 */

	@Override
	public void updateActor() {
		updatePosition();
	}


}
