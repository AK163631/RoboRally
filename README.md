# RoboRally
RoboRally Source

## Usage 
This is a basic idea of how the ```Game``` class should be used in the GUI.
```java
// Create game object with either paths to the brd file and prg file
// or use the other constructor for custom internals
Game g = new Game("brdFile.txt", "prgFile.txt");

ArrayList <ArrayList<BoardEntity>> board = g.getBoard(); //get the board

ArrayList <Players>players = g.getPlayers(); //get players

/*
display board in gui
display players and starts in gui
*/

while(g.hasNext()) { // while game has further steps
    
    g.step(); // step the game once
    
    /*
    update board and players stats in gui using this appropriate getters
    */
}

// game has finished as there are no more steps (g.hasNext() = false)

Player winner = g.getWinner(); // retrieve winner

if (winner == null) {
    /*
    its a draw as there is no winner and the game has ended
    */
} else {
    /*
    display winner
    */
}
```
  

		
