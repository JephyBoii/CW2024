package com.example.demo;

/**
 * class extending fighterplane. enemy which spawns in level one two. fires enemyprojectiletwo.java.
 * moves, accelerates and decelerates slower than enemyplane.java
 */

public class EnemyPlaneTwo extends FighterPlane {

    private static final String IMAGE_NAME = "enemyplane2.png";
    private static final int IMAGE_HEIGHT = 60;
    private static final int HORIZONTAL_VELOCITY = -1;
    private static final int VERTICAL_VELOCITY = 5;
    private static final double PROJECTILE_X_POSITION_OFFSET = -30.0;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
    private static final int INITIAL_HEALTH = 6;
    private static final double FIRE_RATE = .01;
    private static final int ZERO = 0;
    private static final int MAX_VELOCITY = 15;

    private double aliveTime = -(Math.random()*200);
    private final double entryTime = Math.random() * 600;
    private final double exitTime =  200 + entryTime;
    private final double ENTRY_Y_POSITION;
    private final boolean isTopSpawn;
    private double b = 0;

    /**
     * initializes values by passing to superclass and has a random spawn position in the scene as well as an initial spawn on top or below the visible screen
     * @param initialXPos
     * @param initialYPos
     * @param spawnPositionY
     */

    public EnemyPlaneTwo(double initialXPos, double initialYPos, double spawnPositionY) {
        super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos - 100*Math.random(), spawnPositionY, INITIAL_HEALTH);
        ENTRY_Y_POSITION = initialYPos;
        isTopSpawn = spawnPositionY < ZERO;
    }

    /**
     * function which updates the current position of the plane.
     * the pattern that the plane goes is as follows:
     * 0. plane spawns either at the top or bottom of the viewable stage.
     * 1. the plane will continue to move horizontally for a random time between 0 and 200 frames.
     * 2. once the time the plane has been alive surpasses its entry time, the plane calls enterstage() function from its respective inital position.
     * 3. after reaching its supposed position it will continue to move horizontally until its time alive surpasses its exit time (100 frames after entry time).
     * 4. the exit stage function is called and the plane exits the viewable scene
     */

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

    /**
     * function used to fire an enemyprojectiletwo.java when its probability allows it to.
     * @return
     */

    @Override
    public ActiveActorDestructible fireProjectile() {
        if (Math.random() < FIRE_RATE) {
            double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
            double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
            return new EnemyProjectileTwo(projectileXPosition, projectileYPosition);
        }
        return null;
    }

    /**
     * function called by LevelParent.java to update position of actor
     */

    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * boolean which returns whether the plane initially spawned on top or below the screen. used for enterstage() and exitstage() function to correctly move the plane.
     * @param isTop
     * @return
     */

    private boolean notAtPosition(boolean isTop) {
        if (isTop && getPositionY()<ENTRY_Y_POSITION) {
            return true;
        } else return !isTop && getPositionY() > ENTRY_Y_POSITION;
    }

    /**
     * entry movement following initial spawn and wait. changes depending on whether it spawned on top or the bottom of the screen.
     * enters rapidly while decelerating constantly until constantly moving into position.
     * @param isTop
     */

    private void enterStage(boolean isTop) {
        if (isTop) {
            double a = ENTRY_Y_POSITION - getPositionY();
            moveVertically(VERTICAL_VELOCITY + a/50);
        } else {
            double a = ENTRY_Y_POSITION - getPositionY();
            moveVertically(-VERTICAL_VELOCITY + a/50);
        }
    }

    /**
     * exit movement following its exit time pass requirement. changes depending on whether it spawned on top or the bottom of the screen.
     * slowly moves vertically while constantly accelerating to its maximum velocity and exiting the visible screen.
     * @param isTop
     */

    private void exitStage(boolean isTop) {
        if (isTop) {
            if (VERTICAL_VELOCITY + b < MAX_VELOCITY) {
                moveVertically(VERTICAL_VELOCITY + b);
                b += 0.5;
            } else {
                moveVertically(VERTICAL_VELOCITY + b);
            }
        } else {
            if (-(VERTICAL_VELOCITY + b) > -MAX_VELOCITY) {
                moveVertically(-VERTICAL_VELOCITY - b);
                b += 0.5;
            } else {
                moveVertically(-VERTICAL_VELOCITY - b);
            }
        }
    }

    /**
     * returns the current y position of the plane. used in various calculations for movement, entry and exit phase.
     * @return
     */

    private double getPositionY() {
        return getLayoutY()+ getTranslateY();
    }
}
