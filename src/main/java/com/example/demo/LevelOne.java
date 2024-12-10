package com.example.demo;

/**
 * class extending levelparent.java. first stage level. spawns enemyplane.java. level pass requirement is 50 kills.
 */

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelOneTwo";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 50;
	private static final double ENEMY_SPAWN_PROBABILITY = .01;
	private boolean tryOnce = true;

	/**
	 * called by controller.java, passing background image name, screen dimensions and player health.
	 * @param screenHeight height of the scene/background image.
	 * @param screenWidth width of the scene/background image.
	 * @param health initial health value of user plane.
	 */

	public LevelOne(double screenHeight, double screenWidth, int health) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
	}

	/**
	 * checks whether the game is over (pass requirement achieved or player health drops to 0) every update.
	 * attached is a variable to ensure losegame() or gotonextlevel() function can only be called once.
	 * once the level pass requirement is met, calls a function to go to next level, passing the levelname of the next level.
	 * <br><img src="doc-files/LevelOneA.png" alt="image" height="200">
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
	 * spawns enemyplane.java.
	 * can spawn up to 10 enemies at a  time.
	 * <br><img src="doc-files/LevelOneB.png" alt="image" height="200">
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
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition, spawnPositionY);
				addEnemyUnit(newEnemy);
			}
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

	/**
	 * returns boolean which determines whether the level pass requirement is met.
	 * @return boolean determining whether a user has passed level pass requirement.
	 */

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
