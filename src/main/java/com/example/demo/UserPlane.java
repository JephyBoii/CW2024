package com.example.demo;

/**
 * class extending fighterplane.java. controlled by the player through levelparent.java.
 */

public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = 10;
	private static final double Y_LOWER_BOUND = 655.0;
	private static final double X_UPPER_BOUND = 1150.0;
	private static final double X_LOWER_BOUND = 0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 45;
	private int VERTICAL_VELOCITY = 0;
	private int HORIZONTAL_VELOCITY = 0;
	private static final int PROJECTILE_X_POSITION_OFFSET = 120;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int numberOfKills;

	/**
	 * passes values to initialize the class to the superclass.
	 * @param initialHealth health value of the user plane passed to the figherplane superclass.
	 */

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
	}

	/**
	 * changes various x and y values of the plane's current position and horizontal and vertical velocity.
	 * ensures with the x/y upper/lower bounds that the plane will never exit the viewable scene.
	 */
	
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

	/**
	 * called by levelParent.java so the plane's position is updated every frame.
	 */
	
	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * fires a userprojectile.java at its offest position relative to the user position.
	 * @return a projectile of type userprojectile.java, spawns at its offset values to the plane.
	 */
	
	@Override
	public ActiveActorDestructible fireProjectile() {
		return new UserProjectile(getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET), getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * returns boolean determining whether the plane currently has a horizontal or vertical velocity.
	 * @return true or false value.
	 */

	private boolean isMoving() {
		return VERTICAL_VELOCITY != 0 || HORIZONTAL_VELOCITY != 0;
	}

	/**
	 * setter which changes vertical velocity to an upward value.
	 */

	public void moveUp() { VERTICAL_VELOCITY = -8; }

	/**
	 * setter which changes the vertical velocity to a downward value.
	 */

	public void moveDown() { VERTICAL_VELOCITY = 8; }

	/**
	 * setter which changes the horizontal velocity to a leftward value.
	 */

	public void moveLeft() { HORIZONTAL_VELOCITY = -8; }

	/**
	 * setter which changes the horizontal velocity to a rightward value.
	 */

	public void moveRight() { HORIZONTAL_VELOCITY = 8; }

	/**
	 * setter which changes the vertical velocity to 0.
	 */

	public void stopY() { VERTICAL_VELOCITY = 0;}

	/**
	 * setter which changes  the horizontal velocity to 0.
	 */

	public void stopX() { HORIZONTAL_VELOCITY = 0; }

	/**
	 * returns the number of kills the plane has in order to compare to the pass requirement of levelone.java and levelonetwo.java
	 * @return number of kills value.
	 */

	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * incremenets the number of kill variable. occurs everytime a userprojectile collides an enemy plane and destroys it.
	 */

	public void incrementKillCount() {
		numberOfKills++;
	}

}
