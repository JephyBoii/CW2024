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
	 * @param imageName name of the image for this image view to set its image to. passed from various actors.
	 * @param imageHeight height of the image for image view to set its image to.
	 * @param initialXPos initial x position for image view to place its image relative to the scene.
	 * @param initialYPos initial y position for image view to place its image relative to the scene.
	 * @param health value for the health of any fighterplane.
	 */

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * abstract function used to fire appropriate projectile by the respective planes.
	 * @return a projectile fired by their respective fighterplane.
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
	 * @param xPositionOffset x offset of the projectile relative to the plane.
	 * @return the x position and offset of the spawn of the projectile relative to the plane.
	 */

	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * returns the y position a projectile should spawn.
	 * @param yPositionOffset y offset of the projectile relative to the plane
	 * @return the y position and offset of the spawn of the projectile relative to the plane.
	 */

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * returns a boolean to decide if the health has reached 0.
	 * @return boolean determining whether a fighterplane's health is at 0 or not.
	 */

	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * returns the value that the health is at for any given plane.
	 * @return value of fighterplane's health value.
	 */

	public int getHealth() {
		return health;
	}
		
}
