package com.example.demo;

/**
 * abstract class extending activeactor.java and implementing destructible.java interface. acts as a base for all actors in a scene (level).
 */

public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	private boolean isDestroyed;

	/**
	 * passes values to superclass activeactor.java
	 * @param imageName
	 * @param imageHeight
	 * @param initialXPos
	 * @param initialYPos
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
	 * @param isDestroyed
	 */

	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * getter which returns boolean value
	 * @return
	 */

	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
