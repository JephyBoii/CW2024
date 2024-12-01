package com.example.demo;

public class EnemyPlaneTwo extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane.png";
    private static final int IMAGE_HEIGHT = 150;
    private static final int HORIZONTAL_VELOCITY = -1;
    private static final int VERTICAL_VELOCITY = 5;
    private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 6;
    private static final double FIRE_RATE = .01;
    private static final int ZERO = 0;

    private double aliveTime = -(Math.random()*200);
    private final double entryTime = Math.random() * 600;
    private final double exitTime =  200 + entryTime;
    private final double ENTRY_Y_POSITION;
    private final boolean isTopSpawn;
    private double b = 0;

    public EnemyPlaneTwo(double initialXPos, double initialYPos, double spawnPositionY) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos - 100*Math.random(), spawnPositionY, INITIAL_HEALTH);
        ENTRY_Y_POSITION = initialYPos;
        isTopSpawn = spawnPositionY < ZERO;
    }

    @Override
    public void updatePosition() {
        if (aliveTime >= 0) {
            moveHorizontally(HORIZONTAL_VELOCITY);
        }
        if (aliveTime > entryTime) {
            if (notAtPosition(isTopSpawn)) {
                enterStage(isTopSpawn);
            } else {
                aliveTime += 1;
                if (aliveTime > exitTime) {
                    exitStage(isTopSpawn);
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
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectileTwo(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    @Override
    public void updateActor() {
        updatePosition();
    }

    private boolean notAtPosition(boolean isTop) {
        if (isTop && getProjectileYPosition(ZERO)<ENTRY_Y_POSITION) {
            return true;
        } else return !isTop && getProjectileYPosition(ZERO) > ENTRY_Y_POSITION;
    }

    private void enterStage(boolean isTop) {
        if (isTop) {
            double a = ENTRY_Y_POSITION - getProjectileYPosition(ZERO);
            moveVertically(VERTICAL_VELOCITY + a/15);
        } else {
            double a = ENTRY_Y_POSITION - getProjectileYPosition(ZERO);
            moveVertically(-VERTICAL_VELOCITY + a/15);
        }
    }

    private void exitStage(boolean isTop) {
        if (isTop) {
            if (VERTICAL_VELOCITY + b < 15) {
                moveVertically(VERTICAL_VELOCITY + b);
                b += 1;
            } else {
                moveVertically(VERTICAL_VELOCITY + b);
            }
        } else {
            if (-(VERTICAL_VELOCITY + b) > -15) {
                moveVertically(-VERTICAL_VELOCITY - b);
                b += 1;
            } else {
                moveVertically(-VERTICAL_VELOCITY - b);
            }
        }
    }

}