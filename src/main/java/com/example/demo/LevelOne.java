package com.example.demo;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelOneTwo";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 3;
	private static final double ENEMY_SPAWN_PROBABILITY = .01;
	private boolean tryOnce = true;

	public LevelOne(double screenHeight, double screenWidth, int health) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
	}

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

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				double spawnPositionY;
				if (Math.random() > 0.5) {spawnPositionY = -120;}
				else {spawnPositionY = getScreenHeight() + 120;}
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, spawnPositionY);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), getUser().getHealth());
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
