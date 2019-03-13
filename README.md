# RoboRally
RoboRally Source
## TODO's  
 - Tighten access modifiers - mostly done  
 - Leave function optimisation for other team members otherwise do my self - partly done  
 - Team members to complete javadoc using in-line comments as a guide for how functions work  
 - Error handling for invalid brd or prg files - in progress  

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
display players and stats in gui
*/

// main game loop
while (g.hasNext()) { // while game has further steps
    
    g.step(); // step the game once
    
    /*
    update board and players stats in gui using this appropriate getters
    */
}

// game has finished as there are no more steps (g.hasNext() = false)

Player winner = g.getWinner(); // retrive winner

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
  

		
