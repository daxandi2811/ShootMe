# ShootMe

## Project Setup
1. Open IntelliJ
2. Open Project ShootMe (press OK when gradle dialog opens)
3. wait for IntelliJ to load project (progress on bottom, can take a while because gradle is being downloaded)
4. press OK on "Gradle Project Data To Import" dialog
5. on IntelliJ Menu Bar "View" --> "Tool Windows" --> "Gradle"
6. on opened Window unfold "ShootMe (root)" --> "Tasks" --> "build" and double click "build"
7. click the "refresh all gradle projects" button (2 blue circled arrows) on the top left of the gradle window

## Running the Game
1. Run the DesktopServerLauncher located in desktop/src/at/shootme/desktop to start the server
2. Run the DesktopClientLauncher located in desktop/src/at/shootme/desktop to start a client
    * enter "localhost" as host and some player name to join the game at the server
3. Repeat the 2nd step as often as you like to add additional players
4. enter a playername and type in "localhost" for the server host ip to join the server and get into the level-selection screen
5. when all players are connected, one client can select a level and start the game

## controls
mouse click - shoot in direction, the farther your mouse is from your player, the harder you shoot
A, S - move left and right
space - jump, you can double jump - the double jump resets when touching the ground


## gameplay
shoot at other players to gain 10 score per hit
collect coins by running into them to gain 100 score
the player with the highest score after 1min wins the game
your score and the time you have left are displayed in the top right corner

Have fun!