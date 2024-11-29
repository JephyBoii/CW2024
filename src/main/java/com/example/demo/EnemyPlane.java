package com.example.demo;

public class EnemyPlane extends FighterPlane {

	private static final String IMAGE_NAME = "enemyplane.png";
	private static final int IMAGE_HEIGHT = 150;
	private static final int HORIZONTAL_VELOCITY = -2;
	private static final int VERTICAL_VELOCITY = 5;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;
	private static final int ZERO = 0;

	private double aliveTime = 0;
	private final double entryTime = Math.random() * 300;
	private final double exitTime =  100 + entryTime;
	private final double ENTRY_Y_POSITION;
	private final boolean isTopSpawn;
	private double VELOCITY_MULTIPLIER = 3;
	private double PREVIOUS_POSITION;

	public EnemyPlane(double initialXPos, double initialYPos, double spawnPositionY) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, spawnPositionY, INITIAL_HEALTH);
		ENTRY_Y_POSITION = initialYPos;
		PREVIOUS_POSITION = spawnPositionY;
		isTopSpawn = spawnPositionY < ZERO;
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
		if (aliveTime > entryTime) {
			if (isTopSpawn &&
			(getProjectileYPosition(ZERO)<ENTRY_Y_POSITION+10 ||
			(getProjectileYPosition(ZERO)<ENTRY_Y_POSITION-10))) {
				if (getProjectileYPosition(ZERO) > (PREVIOUS_POSITION + ENTRY_Y_POSITION)/1.1 - 10||
				getProjectileYPosition(ZERO) > (PREVIOUS_POSITION + ENTRY_Y_POSITION)/1.1 + 10) {
					VELOCITY_MULTIPLIER *= 0.5;
					PREVIOUS_POSITION = getProjectileYPosition(ZERO);
				}
				moveVertically(VERTICAL_VELOCITY*VELOCITY_MULTIPLIER); //positive value to move down
			} else if (!isTopSpawn &&
			(getProjectileYPosition(ZERO)>ENTRY_Y_POSITION+10 ||
			(getProjectileYPosition(ZERO)<ENTRY_Y_POSITION-10))) {
				if (getProjectileYPosition(ZERO) < (PREVIOUS_POSITION + ENTRY_Y_POSITION)/2.6 - 10||
				getProjectileYPosition(ZERO) < (PREVIOUS_POSITION + ENTRY_Y_POSITION)/2.6 + 10) {
					VELOCITY_MULTIPLIER *= 0.5;
					PREVIOUS_POSITION = getProjectileYPosition(ZERO);
				}
				moveVertically((-VERTICAL_VELOCITY)*VELOCITY_MULTIPLIER);
			} else {
				aliveTime += 1;
				if (aliveTime > exitTime && isTopSpawn) {
					moveVertically(VERTICAL_VELOCITY);
				} else if (aliveTime > exitTime && !isTopSpawn) {
					moveVertically(-VERTICAL_VELOCITY);
				}
			}
		} else {
			aliveTime += 1;
		}
	}

	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPostion = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPostion);
		}
		return null;
	}

	@Override
	public void updateActor() {
		updatePosition();
	}

}
