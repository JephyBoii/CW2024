package com.example.demo;

/**
 * abstract class which extends activeactordestructible and is used by all enemyplanes, bosses and userplane.
 * basic structure to move and fire projectiles.
 */

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	/**
	 * takes in various values and passes  them to the superclass.
	 * initializes a health value.
	 * @param imageName
	 * @param imageHeight
	 * @param initialXPos
	 * @param initialYPos
	 * @param health
	 */

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * abstract function used to fire appropriate projectile by the respective planes.
	 * @return
	 */

	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * basic function to take damage and adjust health as  well as calling the destroy function on the plane upon reaching 0 health.
	 */
	
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * returns the x position a projectile should spawn.
	 * @param xPositionOffset
	 * @return
	 */

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * returns the y position a projectile should spawn.
	 * @param yPositionOffset
	 * @return
	 */

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * returns a boolean to decide if the health has reached 0.
	 * @return
	 */

	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * returns the value that the health is at for any given plane.
	 * @return
	 */

	public int getHealth() {
		return health;
	}
		
}
