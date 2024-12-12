# 1942 game: refactored
github repository link: https://github.com/JephyBoii/CW2024

## introduction and project description

the main task is to refactor a game's source code, which initially was not runnable and had a lot of bugs and errors on launch and during runtime.
the game involves moving up and down on the left side of the screen with enemies spawning on the right side of the screen.
in my refafactored version, many, if not all, bugs were properly addressed and taken care of, numerous additions were implemented and various aspects were modified or completely changed.

## compilation inctructions

1. open the source code on your preferred IDE
2. have javafx installed and set up on the IDE
3. find and select the Main.java class
4. run it

notes:
javaFX and FXML are required to run the program.
in order to run the test files, add the tryJunit folder as a driectory for dependencies and set as test resource folder.

## implemented and working properly

### improved movement and shooting + health carry over
addition of left and right movement and improved input reading, although not perfect [LevelParent.java: 122-136, UserPlane.java: 25-38, 55-65].
change of shooting system, now fires bursts of 2 bullets at a time and the ability to hold down shoot button [LevelParent.java: 142-161].
health correctly carries over to the next level [Controller.java: 34, 38].
these features were implemented because movement felt very limited and unreponsive; unneccessarily tedious to properly traverse the screen and a lmited y axis movement gave the player less options to dodge enemy fire.
hold-to-shoot is always favoured in games where single actions need to be repeatedly done in rapid succession. spamming spacebar to shoot was simply painful especially in boss levels where a boss can have up to 80hp.
the burst pattern of the user fire was done just because it looked and also provided some level of challenge as it put the player's aim to the test.
health carrying over was supposed to be a prerequisite to future implementation of healing and a score system, but was kept for added difficulty.

### new listener interfaces
implemented new listener interfaces between controller, levelparent and menuscreen classes [LevelParent.java: 16-18, MenuScreen.java: 11-13, Controller.java: 29, 30, 39, 47, 63].
correctly and appropriately sends needed data for game to progress [LevelOne.java, LevelOneTwo.java, LevelTwo.java, LevelTwoTwo.java].
the original observer interface was depreceated since java 9 so it seemed obvious to try to find or make a replacement.
the observer interface lacked specification of which listening and listened classes should interact and communicate for any observer and observable class.
my listener interface makes use of in-class interfaces and set listener classes as such within those listened classes.

### main menu screen and game over screen
implemented a main menu screen upon launch and an ending screen depending on user win or loss [MenuScreen.java, Controller.java: 28-30].
correctly takes the player to the first level or exits the program [MenuScreen.java: 38-45].
traditionally a game should take the player to a main menu before the game starts to give the player some time to prepare.
the game over screen, which could display a win or lose message, gives the player the oppurtunity to restart the game or exit the game, classic options for a game.

### new stage level + new enemy type
new level following first level with a new enemy which shoots different types of bullets and have different health [LevelOneTwo.java, EnemyPlaneTwo.java, EnemyProjectileTwo.java].
also displays a new background [LevelOneTwo.java: 5].
a game wouldnt be very fun if it ended too quickly or had only one type of enemy.
a new stage gives the player new enemies with a different look, feel and challenge.

### new boss level + new boss type
new level following the initial boss level with a new boss which shoots different type of bullet and has different health and mechnanic [LevelTwoTwo.java, BossTwo.java, BossProjectileTwo.java].
spawns minions in the fight [LevelTwoTwo.java: 38-50].
to test the capability of a player, i employed the toughest challnge yet: facing a boss with a new pattern *and* facing previous enemies simultaneously.
being able to surpass this challenge leaves the player satisfied with the game upon completion.

## implemented but not working properly

nothing really...

## features not implemented

### music + SFX
addition of a new class which controls all SFX.
not implemented due to time constraints.

### further refactoring of levelparent class
separation of all activeactor handlers, collisions etc. into its own class.
not implemented due to time constraints.

### pause menu
ability to pause the game during gameplay without disrupting anything.
not implemented due to time constraints.

### resizable/resized window
ability to resize the window to the user's content or fullscreen for better view.
not implemented due to time constraints.

### score system
a score to be displayed during the game and at the win or lose screen.
not implemented due to time constraints.

### new user projectile
another projectile for the user to fire, taking time to charge up and firing multiple bullets across the screen.
not implemented due to time constraints.

## new java classes

### 1. MenuScreen.java
new 'parent' class for all menus displayed within the game.
displays start screen, lose screen and win screen.
includes start game/restart game button which takes the user to the first level and an exit game button which closes the program.
includes a listener interface for Controller.java to appropriately call which menu to be displayed.

### 2. MainMenuImage.java
imageview for the main menu image to be displayed on the start screen.

### 3. LevelOneTwo.java
a new stage level which displays a new background image and spawns new enemies.
has a different spawn pattern and pass requirement.

### 4. LevelTwoTwo.java
a new boss level which displays a new basckground image and spawns a new boss.
also spawns enemies.

### 5. EnemyPlaneTwo.java
new type of enemy with different health, speed and spawn values.
also shoots different bullets.

### 6. EnemyProjectileTwo.java
new type of enemy projectile used by the new enemy.
has different velocity value and movement pattern, starting slow before accelerating rapidly.

### 7. BossTwo.java
new type of boss with different health, speed and mechanic.
shoots different bullets.

### 8. BossProjectileTwo.java
new type of boss projectile used by new boss.
has different movement pattern similar to enemy projectile two.

## modified java classes

### Controller.java
- implements 2 new listener interafaces for LevelParent.java and MenuScreen.java for data to be appropriately communicated between each class for the correct functions to take place.
- the interface for LevelParent.java allows the current level to call for a new level through Controller.
- the interface for MenuScreen.java allows the level of LevelParent.java to call the appropriate end screen following the end of a level.
- this was done because the observer interface is depreciated and did not allow for specific listener calls to be made such as a menu call and level call.#

### LevelParent.java
- added additional key presses and releases for horizontal user movement.
- additionally reworked key press/release-to-move system to allow smoother transition between movement key presses. previously, if for example the up key was pressed, the plane would move up; when the down key was pressed the plane would then move down but when the up key was released the override of user.stop() on key release forced the plane to stop.
- updateLevelView() function was turned into a protected funcction to allow LevelTwo.java to override it (needed for shield display to work properly).
- additionally a new function getLevelView() was added.
- no longer extends observable; new listener interface implemented.
- when goToNextLevel() si called, updates listener (Controller.java) to fecth level name.
- adjusted MILLISECOND_DELAY to 40 (from 50) to slightly speed up the game.
- removed updateKillCount()
- updated collision handle functions to also account for kill count (will only increment if user projectile affects enemy plane)
- incrementKillCount() is now called when an enemy plane is killed by a user projectile
- added enemyIsOutOfBounds() and handleEnemyOutOfBounds() to deal with enemies moving off screen vertically with added enemy spawn patterns.
- added getScreenHeight() to be called by levelOne.java and levelOneTwo.java when deciding spawn of enemy plane
- completely depreciated currentNumberOfEnemies, its function updateNumberOfEnemies() and its instantiation in the constructor as it can be observed that getCurrentNumberOfEnemies returns something else
- reworked firing mechanics:
- fireProjectile affects firing boolean
- generateUserFire is included when updateScene() is called
- fires a burst (of 2 projectiles with a 2-update difference) every 10 updates whilst spacebar is being held
- changed getUser().getHealth() to user.getHealth()
- removed handleEnemyPenetration() and enemyHasPenetratedDefenses() because enemy can no longer do that
- renamed boolean killCount to countToKill because it's more intuitive
- added gameEnded function in listener interface
- changed winGame() and loseGame() to send data to listener rather than show win/lose image. this data determines whether the menuscreen will display a lose or win

### LevelOne.java
- added a boolean variable tryOnce to ensure goToNextLevel(), loseGame() and WinGame() are called once and not repeatedly.
- NEXT_LEVEL string replaced to point to levelOneTwo (next level)
- added new variable spawnPositionY which determines whether a plane will spawn on top or bottom off screen
- removed player initial health variable
- added new variable into constructor call: health
- updated instantiateLevelView() to accommodate health carry over

### LevelTwo.java
- adjusted instantiateLevelView() to be protected
- added updateLevelView() override for shield display
- removed player initial health variable
- added new variable into constructor call, health
- updated instantiateLevelView to accommodate health carry over
- changed NEXT_LEVEL variable to point to LevelTwoTwo (next level)
- added a boolean variable tryOnce to ensure goToNextLevel(), loseGame() and WinGame() are called once and not repeatedly.

### LevelView.java
- main function is to display any and every UI element that isnt a plane or projectile
- added shield as an image
- added showShield() and hideShield() for shield display function
- removed win image and lose image (transfered to MenuScreen.java)

### LevelViewLevelTwo.java
- fully depreciated
- all previous functionality transferred to LevelView.java

### UserPlane.java
- reworked velocity and multiplier variables
- added moveRight() and moveLeft() functions
- separated stop() into stopY() and stopX() in order to allow their movement methods to be independent
- added Projectile x offset variable(set to 0)
- adjust fireProjectile() to send position of player rather than a static x value
- updated x/y upper/lower bounds

### EnemyPlane.java
- overhauled updatePosition() to have an enemy spawn either above or below the screen and accelerate downwards/upwards into its given Y position to a halt. each enemy enter and exit at different times, determined by the aliveTime variable value.
- added new intake spawnPosition which is the plane's position to travel to after spawning from off screen
- added a lot of new variables to accomodate the new movement overhaul
- overhauled updatePosition() to be more readable
- created boolean function notAtPosition() to confirm whether an enemy plane is where they are supposed to be from their spawn for entry
- created enterStage() which takes a boolean to determine its spawn position (above or below the screen) and properly enter the stage
- created exitStage() to properly determine its next movement to make to exit a  stage
- added private double b to help with calculations for exitStage()
- initial health increased (from 1 to 2)
- alive time adjusted for more variety enemy initial positions (was 0)
- entry time adjusted to prevent enemies spawning too close (from 300 to 200)
-  instantiation value initialXPos adjusted to allow more variety of spawn positions
- added new if statement in updatePosition() to allow more variety of spawn positions
- renamed a variable (misspelling) in fireProjectile()
- added new function, getPositionY() to replace previoiusly "getProjectilePosition(ZERO)"
- adjusted some values
- added new variable, max velocity

### Boss.java
- adjusted activateShield and deactivateShield to be protected to allow them to be called in a higher class
- adjusted shieldExhausted
- adjusted MAX_FRAMES_WITH_SHIELD
- added checkShield() so a higher class can check if shield is activated or not (to display)
- adjusted initial Y position to center

### GameOverImage.java
- added a line to set its visibility to false on instantiation
- added function to set its visibility to true
- removed intake variables
- restructured entire body and functions

### WinImage.java
- removed height and width variables
- reworked entire body and function

### ShieldImage.java
- added setVisible(false)
- removed shield size variable
- manually set shield size

### UserProjectile.java
- adjusted velocity (from 15 to 10)
- added velocity multiplier
- updated updatePosition() to adjust for new bullet velocity calculation
- adjusted velocity multiplier value (from 1.1 to 2)
- added new maximum velocity value
- reworked projectile movement to work more reasonably and controllably (no longer multiplies velocity with multiplier for a very irrational final velocity)

## unexpected problems

Some functions would be repeatedly called when they were only supposed to be called once. this would greatly consume a lot of RAM and slow the game down significantly, occasionally crashing the program altogether. to fix this issue i added a variable in each class where this problem could occur to make sure that the culprit function would only be able to be  called once.

In level two, the boss shield would not be properly displayed as there was nothing relating displaying the shield in level view to the boss needing the shield to be activated. to fix this, I overrode the updatelevelview() function in level two to check when the boss needed the shield to be active and called the showShield() function in its levelview.

Apparently all the hitboxes/sizes of the actual images were much larger than what we see in game because of transparent elemnts of those imaages. to fix this, i simply replaced every image, taking into account the transparent parts and scaling everything properly.

error when deploying project through maven. no fix.

JUnit testing was unbelieveably tiring and tedious, I encountered many errors such as failing to initialize graphics and much much more relating to testing on a javaFX project. eventually, through the implementation of testFX, i was able to successfully implement test cases... to some extent. the tests do work; they pass and fail when they should.
