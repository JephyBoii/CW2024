package com.example.demo;

/**
 * abstract class for all projectiles fired by enemyplane, bosses and userplane.
 * extends activeactordestructible.
 */

public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * initializes all values by passing it to a superclass
	 * @param imageName
	 * @param imageHeight
	 * @param initialXPos
	 * @param initialYPos
	 */

	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * function to change the state of a given projectile to be destroyed by calling function destroy()
	 */

	@Override
	public void takeDamage() {
		this.destroy();
	}

	/**
	 * abstract function which determines the movement pattern of a projectile.
	 */

	@Override
	public abstract void updatePosition();

}
