package com.example.demo;

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double X_UPPER_BOUND = 1300.0;
	private static final double X_LOWER_BOUND = 0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 45;
	private int VERTICAL_VELOCITY = 0;
	private int HORIZONTAL_VELOCITY = 0;
	private static final int PROJECTILE_X_POSITION_OFFSET = 120;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int numberOfKills;

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
	}
	
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			double initialTranslateX = getTranslateX();
			moveVertically(VERTICAL_VELOCITY );//* velocityMultiplier);
			moveHorizontally(HORIZONTAL_VELOCITY);
			double newPositionY = getLayoutY() + getTranslateY();
			double newPositionX = getLayoutX() + getTranslateX();
			if (newPositionY < Y_UPPER_BOUND || newPositionY > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
			if (newPositionX > X_UPPER_BOUND || newPositionX < X_LOWER_BOUND) {
				this.setTranslateX(initialTranslateX);
			}
		}
	}
	
	@Override
	public void updateActor() {
		updatePosition();
	}
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	private boolean isMoving() {
		return VERTICAL_VELOCITY != 0 || HORIZONTAL_VELOCITY != 0;
	}

	public void moveUp() { VERTICAL_VELOCITY = -8; }

	public void moveDown() { VERTICAL_VELOCITY = 8; }

	public void moveLeft() { HORIZONTAL_VELOCITY = -8; }

	public void moveRight() { HORIZONTAL_VELOCITY = 8; }

	public void stopY() { VERTICAL_VELOCITY = 0;}

	public void stopX() { HORIZONTAL_VELOCITY = 0; }

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

}
