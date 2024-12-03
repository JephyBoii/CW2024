package com.example.demo;

public class EnemyProjectileTwo extends Projectile {

    private static final String IMAGE_NAME = "enemyfire2.png";
    private static final int IMAGE_HEIGHT = 20;
    private int HORIZONTAL_VELOCITY = -2;
    private static final int MAX_VELOCITY = 100;
    private static final int VELOCITY_MULTIPLIER = -4;
    private int timeAlive = 0;

    public EnemyProjectileTwo(double initialXPos, double initialYPos) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
    }

    @Override
    public void updatePosition() {
        moveHorizontally(HORIZONTAL_VELOCITY);
        timeAlive++;
        if (HORIZONTAL_VELOCITY < MAX_VELOCITY && timeAlive > 60) {
            HORIZONTAL_VELOCITY += VELOCITY_MULTIPLIER;
        }
    }

    @Override
    public void updateActor() {
        updatePosition();
    }


}
