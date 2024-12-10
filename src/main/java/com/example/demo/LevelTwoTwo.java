package com.example.demo;

/**
 * class extending levelparent.java. second boss level stage. spawns bosstwo.java and enemyplane.java. level pass requirement is killing the boss.
 */

public class LevelTwoTwo extends LevelParent{

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background4.jpg";
    private final BossTwo boss;
    private boolean tryOnce = true;

    private final int TOTAL_ENEMIES = 4;

    /**
     * called by controller.java, passing background image name, screen dimensions and player health.
     * @param screenHeight height of the scene/background image.
     * @param screenWidth width of the scene/background image.
     * @param health initial health value of user plane.
     */

    public LevelTwoTwo(double screenHeight, double screenWidth, int health) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
        boss = new BossTwo();
    }

    /**
     * initializes the userplane.
     */

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    /**
     * checks whether the game is over (pass requirement achieved or player health drops to 0) every update.
     * attached is a variable to ensure losegame() or wingame() function can only be called once.
     * once the level pass requirement is met, calls a function end timeline and display win menu screen.
     */

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            while (tryOnce) {
                loseGame();
                tryOnce = false;
            }
        }
        else if (boss.isDestroyed()) {
            while (tryOnce) {
                winGame();
                tryOnce = false;
            }
        }
    }

    /**
     * initializes enemy spawns of a level.
     * spawns enemyplane.java.
     * can spawn up to 4 enemies at a  time.
     */

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
        }
        int currentNumberOfEnemies = getCurrentNumberOfEnemies();
        for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
            double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
            double spawnPositionY;
            if (Math.random() > 0.5) {spawnPositionY = -120;}
            else {spawnPositionY = getScreenHeight() + 120;}
            ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, spawnPositionY);
            addEnemyUnit(newEnemy);
        }
    }

    /**
     * instantiates level view, specifically the player hearts to display.
     * @return level view of the level.
     */

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), getUser().getHealth());
    }
}
