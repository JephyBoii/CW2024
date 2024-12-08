package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.util.Duration;

public abstract class LevelParent{

	public interface Listener {
		void fetch(String Data, int health);
		void gameEnded(boolean n);
	}

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 40;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;
	private double fireRate = 0;
	private boolean firing = false;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;

	private final LevelView levelView;
	private Listener listener;

	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(String.valueOf(getClass().getResource(backgroundImageName))));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		initializeTimeline();
		friendlyUnits.add(user);
	}

	protected abstract void initializeFriendlyUnits();

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	public void goToNextLevel(String levelName) {
		listener.fetch(levelName, user.getHealth());
	}

	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateLevelView();
		checkIfGameOver();
		handleEnemyOutOfBounds();
		generateUserFire();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	private void initializeBackground() {
		final boolean[] movingUp = {false};
		final boolean[] movingDown = {false};
		final boolean[] movingLeft = {false};
		final boolean[] movingRight = {false};

		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) {user.moveUp(); movingUp[0] = true;}
				if (kc == KeyCode.DOWN) {user.moveDown(); movingDown[0] = true;}
				if (kc == KeyCode.LEFT) {user.moveLeft(); movingLeft[0] = true;}
				if (kc == KeyCode.RIGHT) {user.moveRight(); movingRight[0] = true;}
				if (kc == KeyCode.SPACE) fireProjectile(true);
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) {movingUp[0] = false; if (!movingDown[0]) user.stopY();}
				if (kc == KeyCode.DOWN) {movingDown[0] = false; if (!movingUp[0]) user.stopY();}
				if (kc == KeyCode.LEFT) {movingLeft[0] = false; if (!movingRight[0]) user.stopX();}
				if (kc == KeyCode.RIGHT) {movingRight[0] = false; if (!movingLeft[0]) user.stopX();}
				if (kc == KeyCode.SPACE) fireProjectile(false);
			}
		});
		root.getChildren().add(background);
	}

	private void fireProjectile(boolean fire) {
		firing = fire;
	}

	private void generateUserFire() {
		if (fireRate == 0 && firing) {
			ActiveActorDestructible projectile = user.fireProjectile();
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
			fireRate = 12;
		} else if (fireRate == 10) {
			ActiveActorDestructible projectile = user.fireProjectile();
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
			fireRate = 9;
		}
		if (fireRate > 0) {
			fireRate--;
		}
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits, false);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits, true);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits, false);
	}

	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2, boolean countToKill) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					if (actor.isDestroyed() && countToKill) {
						user.incrementKillCount();
						//System.out.println(user.getNumberOfKills()); will be used for testing later
					}
				}
			}
		}
	}

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	protected LevelView getLevelView() {
		return levelView;
	}

	private boolean enemyIsOutOfBounds(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateY()) > screenHeight + 300 || Math.abs(enemy.getTranslateY()) < -300;
	}

	protected void winGame() {
		timeline.stop();
		listener.gameEnded(true);
	}

	protected void loseGame() {
		timeline.stop();
		listener.gameEnded(false);
	}

	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected double getScreenHeight() {
		return screenHeight;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void handleEnemyOutOfBounds() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyIsOutOfBounds(enemy)) {
				enemy.destroy();
			}
		}
	}

}
