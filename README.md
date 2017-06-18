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