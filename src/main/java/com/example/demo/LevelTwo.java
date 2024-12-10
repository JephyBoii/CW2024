package com.example.demo;

/**
 * class extending levelparent.java. first boss level stage. spawns boss.java. level pass requirement is killing the boss.
 */

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelTwoTwo";
	private final Boss boss;
	private boolean tryOnce = true;

	/**
	 * called by controller.java, passing background image name, screen dimensions and player health.
	 * @param screenHeight height of the scene/background image.
	 * @param screenWidth width of the scene/background image.
	 * @param health initial health value of user plane.
	 */

	public LevelTwo(double screenHeight, double screenWidth, int health) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
		boss = new Boss();
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
		else if (boss.isDestroyed()) {
			while (tryOnce) {
				goToNextLevel(NEXT_LEVEL);
				tryOnce = false;
			}
		}
	}

	/**
	 * initializes the enemy spawn of the level.
	 * spawns boss.java just once.
	 */

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
		}
	}

	/**
	 * instantiates level view, specifically the player hearts to display.
	 * @return level view of the current level
	 */

	@Override
	protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), getUser().getHealth());
	}

	/**
	 * updates level view to reflect whether boss shield should be displayed and updates user heart display.
	 */

	@Override
	protected void updateLevelView() {
		getLevelView().removeHearts(getUser().getHealth());
		if (boss.checkShield()) {getLevelView().showShield();}
		else {getLevelView().hideShield();}
	}

}
