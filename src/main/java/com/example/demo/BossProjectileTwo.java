package com.example.demo;

public class BossProjectileTwo extends Projectile {

    private static final String IMAGE_NAME = "bossfire2.png";
    private static final int IMAGE_HEIGHT = 60;
    private int HORIZONTAL_VELOCITY = -1;
    private static final int INITIAL_X_POSITION = 950;
    private static final int MAX_VELOCITY = 80;
    private static final int VELOCITY_MULTIPLIER = -5;
    private int timeAlive = 0;

    public BossProjectileTwo(double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        timeAlive++;
        if (HORIZONTAL_VELOCITY < MAX_VELOCITY && timeAlive >= 80) {
            HORIZONTAL_VELOCITY += VELOCITY_MULTIPLIER;
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

}
