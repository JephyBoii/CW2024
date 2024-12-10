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

/**
 * ultimate abstract class for all levels.
 * contains all actors, ui elements, timeline progression, actor handling, user control, level view control.
 * instantiates every level through controller.
 */

public abstract class LevelParent{

	/**
	 * listener interface for passing level name data and current user health value to controller to construct next level.
	 */

	public interface Listener {

		/**
		 * used by listened class to send a string data and health integer value to listener class.
		 * @param Data name of level.
		 * @param health user health value.
		 */

		void fetch(String Data, int health);

		/**
		 * used by listened class to send a signal to instantiate a menu screen display with a loss or win screen.
		 * @param n determines win(true) or loss(false) screeen to be displayed.
		 */

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

	/**
	 * initializes all values and creates new group, scene, userplane and list of all actors categorized appropriately.
	 * initializes the background, player health and level view (scene).
	 * instantiates a new level view (scene) every level.
	 * @param backgroundImageName image name to be  used as scene background image.
	 * @param screenHeight height of the scene/background image.
	 * @param screenWidth width of the scene/background image.
	 * @param playerInitialHealth initial health of the user.
	 */

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

	/**
	 * abstract function to initialize friendly units (user). called by all levels.
	 */

	protected abstract void initializeFriendlyUnits();

	/**
	 * abstract function which checks whether the game state has changed to a loss (user health decreased to 0) or win (level pass requirements achieved).
	 */

	protected abstract void checkIfGameOver();

	/**
	 * abstract function to spawn appropriate enemy units in their respective levels. called by all levels.
	 */

	protected abstract void spawnEnemyUnits();

	/**#
	 * abstract function to instantiate and return a level view appropriate to the level.
	 * @return an instantiated level view for each level.
	 */

	protected abstract LevelView instantiateLevelView();

	/**
	 * listener interface function to set a class which implements the interface as a listener.
	 * @param listener assigns as a listener to the implementing class.
	 */

	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * initiallizes the background image, userplane image and heart display in the levelview of a level.
	 * @return a scene with level view initialized (background set, user spawned and hearts displayed).
	 */

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		levelView.showHeartDisplay();
		return scene;
	}

	/**
	 * starts the game, displays the entire scene on top fo the stage and starts the timeline.
	 */

	public void startGame() {
		background.requestFocus();
		timeline.play();
	}

	/**
	 * indicates to the listener class to go to a level, specified by the levelname data and passes user health value.
	 * @param levelName string of the name of the level to be called.
	 */

	public void goToNextLevel(String levelName) {
		listener.fetch(levelName, user.getHealth());
	}

	/**
	 * all changes which take place during a timeline update.
	 * numerous actor handling takes place and level view is updates
	 */

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

	/**
	 * initializes the timeline to indefinitely update every 40 milliseconds delay.
	 */

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
	}

	/**
	 * initializes background image in scene and handles user input reading.
	 * when certain keys are pressed, userplane functions are called and a boolean keeps track of whether the plane is moving in a direction or not.
	 * on key press, moving in their respective direction updates their boolean value.
	 * on key release, as long as its opposing direction boolean is not true, the plane will stop moving.
	 * otherwise, the user plane will continue to move in the direction of the last key press.
	 * additionally, also handles projectile firing boolean, determining whether a new projectile should be fired at any given frame.
	 */

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

	/**
	 * setter of boolean firing which determines whether user plane should be firing a projectile at a given frame.
	 * @param fire sets variable firing to this value.
	 */

	private void fireProjectile(boolean fire) {
		firing = fire;
	}

	/**
	 * generates user projectile while boolean firing is true.
	 * fires a projectile upon firing becoming true and firerate at 0.
	 * fires in a burst of 2 projectiles with a 2 frame gap between them and a 10 frame gap between bursts.
	 * will always fire bursts and cannot fire a single bullet.
	 */

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

	/**
	 * generates enemy fire by calling fireprojectile() in each enemy of enemyunit list.
	 * affected by an individual enemy's firerate (chance to fire at any given frame).
	 */

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * passes the projectile image to the scene and adds this projectile to enemyprojectiles list.
	 * @param projectile projectile from enemy to be added to scene.
	 */

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
		}
	}

	/**
	 * call to update all actors contained in all actor lists.
	 * calls updateactor() of every actor.
	 */

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * removes all actors which are destroyed (upon collision or health reaching 0) from the scene.
	 */

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * removes a given actor which has its state set to destroyed from the scene.
	 * @param actors list of actors for function to affect.
	 */

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * handles collision between a user plane and enemy plane.
	 * does not affect kill count.
	 */

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits, false);
	}

	/**
	 * handles collision between user projectile and enemy plane.
	 * affects kill count which determines level progression.
	 */

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits, true);
	}

	/**
	 * handles collision between enemy projectile and user plane.
	 * does not affect kill count.
	 */

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits, false);
	}

	/**
	 * handles pairs of actors and their collisions.
	 * both actors will take damage upon collision.
	 * if count to kill boolean is true, the kill count of the user is incremented if a collision results in the enemyplane being destroyed.
	 * @param actors1 first list of actors to compare collision between.
	 * @param actors2 second list of actors to compare collision between.
	 * @param countToKill determines if the collision will affect kill count.
	 */

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

	/**
	 * updates the level view, specifically the heart display of the player being updates to reflect the current user health.
	 */

	protected void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * returns level view.
	 * used for level two's boss shield mechanic.
	 * @return the level view of the current level.
	 */

	protected LevelView getLevelView() {
		return levelView;
	}

	/**
	 * handles an enemy being out of bounds vertically.
	 * removes enemy if true.
	 * @param enemy any enemy actor at a given time.
	 * @return boolean determining whether the actor is out of bounds or not.
	 */

	private boolean enemyIsOutOfBounds(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateY()) > screenHeight + 300 || Math.abs(enemy.getTranslateY()) < -300;
	}

	/**
	 * game win state, stops the timeline and sends data to the listener class to indicate to show the correct win screen.
	 */

	protected void winGame() {
		timeline.stop();
		listener.gameEnded(true);
	}

	/**
	 * game lose state, stops the timeline and sends data to the listener class to indicate to show the correct lose screen.
	 */

	protected void loseGame() {
		timeline.stop();
		listener.gameEnded(false);
	}

	/**
	 * returns the user when called.
	 * used in various classes to check for kill count of the user and instantiate a new level view.
	 * @return the user plane actor.
	 */

	protected UserPlane getUser() {
		return user;
	}

	/**
	 * returns the root of the level parent.
	 * used in various classes to add elements to the level view such as user heart display.
	 * @return group of the scene.
	 */

	protected Group getRoot() {
		return root;
	}

	/**
	 * returns the current number of enemies in enemyunits list.
	 * used to determine whether a new enemy should spawn or not.
	 * @return  size of list enemyunits.
	 */

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * adds a new enemy to enemyunits list and scene.
	 * @param enemy enemy to be added to the scene.
	 */

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * returns enemy maximum position value.
	 * used in calculations for bounds of the enemy to spawn.
	 * @return a maximum value for a y position for enemy to spawn.
	 */

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * returns width of the screen.
	 * used in various levels when spawning enemy planes to determine its initial x position.
	 * @return value of the width of the stage.
	 */

	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * returns height of the screen.
	 * used in various levels when spawning enemy planes to determine its initial y position.
	 * @return value of the height if the stage.
	 */

	protected double getScreenHeight() {
		return screenHeight;
	}

	/**
	 * returns user state.
	 * used when determining if a user loses the game.
	 * @return boolean value of user isdestroyed variable.
	 */

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * handles enemies moving out of bounds vertically.
	 * if true, destroys that enemy.
	 */

	private void handleEnemyOutOfBounds() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyIsOutOfBounds(enemy)) {
				enemy.destroy();
			}
		}
	}

}
