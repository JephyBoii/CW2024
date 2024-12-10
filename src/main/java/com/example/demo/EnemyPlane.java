package com.example.demo;

/**
 * class extending fighterplane. enemy which spawns in level one and level two two. fires enemyprojectile.java.
 */

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 60;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final int VERTICAL_VELOCITY = 5;
	private static final double PROJECTILE_X_POSITION_OFFSET = -30.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 2;
	private static final double FIRE_RATE = .01;
	private static final int ZERO = 0;
	private static final int MAX_VELOCITY = 20;

	private double aliveTime = -(Math.random()*200);//0;
	private final double entryTime = Math.random() * 200;
	private final double exitTime =  100 + entryTime;
	private final double ENTRY_Y_POSITION;
	private final boolean isTopSpawn;
	private double b = 0;

	/**
	 * initializes values by passing to superclass and has a random spawn position in the scene as well as an initial spawn on top or below the visible screen
	 * <br><img src="doc-files/EnemyPlaneA.png" alt="image" height="70">
	 * @param initialXPos initial x position for image view to place its image relative to the scene.
	 * @param initialYPos initial y position for image view to place its image relative to the scene.
	 * @param spawnPositionY actual y position for spawn of plane. passed as initial y position to superclass.
	 */

	public EnemyPlane(double initialXPos, double initialYPos, double spawnPositionY) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos - 100*Math.random(), spawnPositionY, INITIAL_HEALTH);
		ENTRY_Y_POSITION = initialYPos;
		isTopSpawn = spawnPositionY < ZERO;
	}

	/**
	 * function which updates the current position of the plane.
	 * the pattern that the plane goes is as follows:
	 * 0. plane spawns either at the top or bottom of the viewable stage.
	 * 1. the plane will continue to move horizontally for a random time between 0 and 200 frames.
	 * 2. once the time the plane has been alive surpasses its entry time, the plane calls enterstage() function from its respective inital position.
	 * 3. after reaching its supposed position it will continue to move horizontally until its time alive surpasses its exit time (100 frames after entry time).
	 * 4. the exit stage function is called and the plane exits the viewable scene
	 * <br><img src="doc-files/EnemyPlaneB.png" alt="image" height="200">
	 */

	@Override
	public void updatePosition() {
		if (aliveTime >= 0) {
			moveHorizontally(HORIZONTAL_VELOCITY);
		}
		if (aliveTime > entryTime) {
			if (notAtPosition(isTopSpawn)) {
				enterStage(isTopSpawn);
			} else {
				aliveTime += 1;
				if (aliveTime > exitTime) {
					exitStage(isTopSpawn);
				}
			}
		} else {
			aliveTime += 1;
		}
	}

	/**
	 * function used to fire an enemyprojectile.java when its probability allows it to.
	 * @return a projectile of type enemyprojectile.java, spawns at its offset values to the plane.
	 */

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * function called by LevelParent.java to update position of actor.
	 */

	@Override
	public void updateActor() {
		updatePosition();
	}

	/**
	 * boolean which returns whether the plane initially spawned on top or below the screen. used for enterstage() and exitstage() function to correctly move the plane.
	 * @param isTop determines whether it spawns on top or bottom of the screen.
	 * @return boolean determining if a plane is at its entry position yet or not.
	 * <br><img src="doc-files/EnemyPlaneC.png" alt="image" height="100">
	 */

	private boolean notAtPosition(boolean isTop) {
		if (isTop && getPositionY()<ENTRY_Y_POSITION) {
			return true;
		} else return !isTop && getPositionY() > ENTRY_Y_POSITION;
	}

	/**
	 * entry movement following initial spawn and wait. changes depending on whether it spawned on top or the bottom of the screen.
	 * enters rapidly while decelerating constantly until constantly moving into position.
	 * <br><img src="doc-files/EnemyPlaneD.png" alt="image" height="200">
	 * @param isTop determines whether it spawns on top or bottom of the screen.
	 */

	private void enterStage(boolean isTop) {
		if (isTop) {
			double a = ENTRY_Y_POSITION - getPositionY();
			moveVertically(VERTICAL_VELOCITY + a/30);
		} else {
			double a = ENTRY_Y_POSITION - getPositionY();
			moveVertically(-VERTICAL_VELOCITY + a/30);
		}
	}

	/**
	 * exit movement following its exit time pass requirement. changes depending on whether it spawned on top or the bottom of the screen.
	 * slowly moves vertically while constantly accelerating to its maximum velocity and exiting the visible screen.
	 * <br><img src="doc-files/EnemyPlaneE.png" alt="image" height="300">
	 * @param isTop determines whether it spawns on top or bottom of the screen.
	 */

	private void exitStage(boolean isTop) {
		if (isTop) {
			if (VERTICAL_VELOCITY + b < MAX_VELOCITY) {
				moveVertically(VERTICAL_VELOCITY + b);
				b += 1;
			} else {
				moveVertically(VERTICAL_VELOCITY + b);
			}
		} else {
			if (-(VERTICAL_VELOCITY + b) > -MAX_VELOCITY) {
				moveVertically(-VERTICAL_VELOCITY - b);
				b += 1;
			} else {
				moveVertically(-VERTICAL_VELOCITY - b);
			}
		}
	}

	/**
	 * returns the current y position of the plane. used in various calculations for movement, entry and exit phase.
	 * @return current y position of plane relative  to the scene.
	 */

	private double getPositionY() {
		return getLayoutY()+ getTranslateY();
	}

}
