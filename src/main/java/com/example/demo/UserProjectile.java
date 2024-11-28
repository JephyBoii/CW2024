package com.example.demo;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final double VELOCITY_MULTIPLIER = 1.1;
	private double HORIZONTAL_VELOCITY = 10;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		if (HORIZONTAL_VELOCITY * VELOCITY_MULTIPLIER < 45) {
			HORIZONTAL_VELOCITY = HORIZONTAL_VELOCITY * VELOCITY_MULTIPLIER;
			moveHorizontally(HORIZONTAL_VELOCITY);
		} else {
			moveHorizontally(HORIZONTAL_VELOCITY);
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
