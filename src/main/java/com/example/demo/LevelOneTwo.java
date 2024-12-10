package com.example.demo;

/**
 * class extending levelparent.java. second stage level. spawns enemyplanetwo.java. level pass requirement is 30 kills.
 */

public class LevelOneTwo extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
    private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
    private static final int TOTAL_ENEMIES = 12;
    private static final int KILLS_TO_ADVANCE = 30;
    private static final double ENEMY_SPAWN_PROBABILITY = .20;
    private boolean tryOnce = true;

    /**
     * called by controller.java, passing background image name, screen dimensions and player health.
     * @param screenHeight height of the scene/background image.
     * @param screenWidth width of the scene/background image.
     * @param health initial health value of user plane.
     */

    public LevelOneTwo(double screenHeight, double screenWidth, int health) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
    }

    /**
     * checks whether the game is over (pass requirement achieved or player health drops to 0) every update.
     * attached is a variable to ensure losegame() or gotonextlevel() function can only be called once.
     * once the level pass requirement is met, calls a function to go to next level, passing the levelname of the next level.
     */

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            while (tryOnce) {
                loseGame();
                tryOnce = false;
            }
        }
        else if (userHasReachedKillTarget())
            while (tryOnce) {
                goToNextLevel(NEXT_LEVEL);
                tryOnce = false;
            }
    }

    /**
     * initializes the userplane.
     */

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * initializes enemy spawns of a level.
     * spawns enemyplanetwo.java.
     * can spawn up to 12 enemies at a  time.
     */

    @Override
    protected void spawnEnemyUnits() {
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
                double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
                double spawnPositionY;
                if (Math.random() > 0.5) {spawnPositionY = -120;}
                else {spawnPositionY = getScreenHeight() + 120;}
                ActiveActorDestructible newEnemy = new EnemyPlaneTwo(getScreenWidth(), newEnemyInitialYPosition, spawnPositionY);
                addEnemyUnit(newEnemy);
            }
        }
    }

    /**
     * instantiates level view, specifically the player hearts to display.
     * @return level view of the level
     */

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), getUser().getHealth());
    }

    /**
     * returns boolean which determines whether the level pass requirement is met.
     * @return boolean determining whether a user has passed level pass requirement.
     */

    private boolean userHasReachedKillTarget() {
        return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
    }
}
