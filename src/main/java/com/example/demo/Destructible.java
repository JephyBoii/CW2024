package com.example.demo;

/**
 * basic interface which is utilized by activeactordestructible and further actors
 */

public interface Destructible {

	/**
	 * framework for taking damage
	 */

	void takeDamage();

	/**
	 * framework for removing actor from scene
	 */

	void destroy();
	
}
