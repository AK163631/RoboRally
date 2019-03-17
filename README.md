# RoboRally
RoboRally Source
## TODO's For Asad
### Docs
 - Start on the design
 - Javadoc - partially complete
### Code
 - Tighten access modifiers - mostly done  
 - Error handling for invalid brd or prg files - half done 
 
 ## TODOS's For Rob
 - The God Damn GUI that seems to be taking forever for him to start on. Wonder why?

## TODO's For The Rest Of Team
 - Complete unit tests using junit jupiter - Ayub 
 - Make a UML diagram from the code - Jimmy

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
  

		
