package com.example.demo;

public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 125;
	private static final double VELOCITY_MULTIPLIER = 2;
	private double HORIZONTAL_VELOCITY = 10;
	private static final double MAX_VELOCITY = 50;

	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		if (HORIZONTAL_VELOCITY < MAX_VELOCITY) {
			HORIZONTAL_VELOCITY += VELOCITY_MULTIPLIER;
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
