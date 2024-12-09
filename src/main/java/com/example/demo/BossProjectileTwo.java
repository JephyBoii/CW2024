package com.example.demo;

/**
 * extends projectile, the bullets that boss 2 shoots out and damages the user plane upon contact.
 */

public class BossProjectileTwo extends Projectile {

    private static final String IMAGE_NAME = "bossfire2.png";
    private static final int IMAGE_HEIGHT = 60;
    private int HORIZONTAL_VELOCITY = -1;
    private static final int INITIAL_X_POSITION = 950;
    private static final int MAX_VELOCITY = 80;
    private static final int VELOCITY_MULTIPLIER = -5;
    private int timeAlive = 0;

    /**
     * initializes values which are passed to the superclass
     * @param initialYPos
     */

    public BossProjectileTwo(double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    /**
     * updates the projectile's next move, which is to move slowly for some time before rapidly accelerating until its max velocity.
     */

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        timeAlive++;
        if (HORIZONTAL_VELOCITY < MAX_VELOCITY && timeAlive >= 80) {
            HORIZONTAL_VELOCITY += VELOCITY_MULTIPLIER;
        }
    }

    /**
     * calls function which updates the actor's current position
     */

    @Override
    public void updateActor() {
        updatePosition();
    }

}
