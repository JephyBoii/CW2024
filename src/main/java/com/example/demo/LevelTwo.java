package com.example.demo;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background2.jpg";
	private final Boss boss;

	public LevelTwo(double screenHeight, double screenWidth, int health) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, health);
		boss = new Boss();
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
	}

	@Override
	protected LevelView instantiateLevelView() {
        return new LevelView(getRoot(), getUser().getHealth());
	}

	@Override
	protected void updateLevelView() {
		getLevelView().removeHearts(getUser().getHealth());
		if (boss.checkShield()) {getLevelView().showShield();}
		else {getLevelView().hideShield();}
	}

}
