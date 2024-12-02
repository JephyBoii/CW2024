package com.example.demo;

public class LevelTwoTwo extends LevelParent{

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
    private final BossTwo boss;

    private final int TOTAL_ENEMIES = 4;

    public LevelTwoTwo(double screenHeight, double screenWidth, int health) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
        boss = new BossTwo();
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        }
        else if (boss.isDestroyed()) {
            winGame();
        }
    }

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

    @Override
    protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), getUser().getHealth());
    }
}
