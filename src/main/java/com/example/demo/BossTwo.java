package com.example.demo;

import java.util.*;

/**
 * boss actor extending fighterplane.java. has a movement pattern, health value and ability to shoot bullets. spawns in level two two.
 * similar to the original boss without its original shield mechanic and shoots out different type of bullets.
 */

public class BossTwo extends FighterPlane {

    private static final String IMAGE_NAME = "bossplane2.png";
    private static final double INITIAL_X_POSITION = 1000.0;
    private static final double INITIAL_Y_POSITION = 200;
    private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
    private static final double BOSS_FIRE_RATE = .025;
    private static final int IMAGE_HEIGHT = 80;
    private static final int VERTICAL_VELOCITY = 5;
    private static final int HEALTH = 80;
    private static final int MOVE_FREQUENCY_PER_CYCLE = 4;
    private static final int ZERO = 0;
    private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
    private static final int Y_POSITION_UPPER_BOUND = -100;
    private static final int Y_POSITION_LOWER_BOUND = 475;
    private final List<Integer> movePattern;
    private int consecutiveMovesInSameDirection;
    private int indexOfCurrentMove;

    /**
     * passes instantiation values to further superclass. initializes certain values relating to level flow and calls a function to initialize its move pattern.
     */

    public BossTwo() {
        super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
        movePattern = new ArrayList<>();
        consecutiveMovesInSameDirection = 0;
        indexOfCurrentMove = 0;
        initializeMovePattern();
    }

    /**
     * moves the boss in the appropriate manner according to the move pattern.
     */

    @Override
    public void updatePosition() {
        double initialTranslateY = getTranslateY();
        moveVertically(getNextMove());
        double currentPosition = getLayoutY() + getTranslateY();
        if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
            setTranslateY(initialTranslateY);
        }
    }

    /**
     * updates the state/position of the boss
     */

    @Override
    public void updateActor() {
        updatePosition();
    }

    /**
     * function to fire a bossprojectiletwo.java
     * @return a projectile of type bossprojectiletwo.java, spawns at an offset to the plane.
     */

    @Override
    public ActiveActorDestructible fireProjectile() {
        return bossFiresInCurrentFrame() ? new BossProjectileTwo(getProjectileInitialPosition()) : null;
    }

    /**
     * calls a function in a superclass to adjust health to reflect damage being taken
     */

    @Override
    public void takeDamage() {
        super.takeDamage();
    }

    /**
     * function which initializes the boss' movement pattern by adding various movements to a list and shuffling them
     */

    private void initializeMovePattern() {
        for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
            movePattern.add(VERTICAL_VELOCITY);
            movePattern.add(-VERTICAL_VELOCITY);
            movePattern.add(ZERO);
            movePattern.add(ZERO);
        }
        Collections.shuffle(movePattern);
    }

    /**
     * returns the next move for the boss to move by looking through the shuffled list of movement pattern and determining how often certain moves should be repeated before switching to the next move
     * @return list of shuffled movement patterns.
     */

    private int getNextMove() {
        int currentMove = movePattern.get(indexOfCurrentMove);
        consecutiveMovesInSameDirection++;
        if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
            Collections.shuffle(movePattern);
            consecutiveMovesInSameDirection = 0;
            indexOfCurrentMove++;
        }
        if (indexOfCurrentMove == movePattern.size()) {
            indexOfCurrentMove = 0;
        }
        return currentMove;
    }

    /**
     * determines whether the boss will fire at any given frame
     * @return boolean determining a boss' fire rate.
     */

    private boolean bossFiresInCurrentFrame() {
        return Math.random() < BOSS_FIRE_RATE;
    }

    /**
     * returns the position relative to the boss that its projectile should fire
     * @return projectile y offset position relative to the plane.
     */

    private double getProjectileInitialPosition() {
        return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
    }
}
