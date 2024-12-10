package com.example.demo;

/**
 * abstract class extending activeactor.java and implementing destructible.java interface. acts as a base for all actors in a scene (level).
 */

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * passes values to superclass activeactor.java
	 * @param imageName name of the image for this image view to set its image to. passed from various actors.
	 * @param imageHeight height of the image for image view to set its image to.
	 * @param initialXPos initial x position for image view to place its image relative to the scene.
	 * @param initialYPos initial y position for image view to place its image relative to the scene.
	 */

	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * abstract function which determines the actor's next action. called continuously while a level is running.
	 */

	@Override
	public abstract void updatePosition();

	/**
	 * abstract function which affects the actors change in state
	 */

	public abstract void updateActor();

	/**
	 * function to change an actor's health
	 */

	@Override
	public abstract void takeDamage();

	/**
	 * sets a boolean value to true
	 */

	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * function which affects boolean isdestroyed
	 * @param isDestroyed boolean determining the status of an actor; destroyed or not.
	 */

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * getter which returns boolean value
	 * @return value of boolean isDestroyed.
	 */

	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
