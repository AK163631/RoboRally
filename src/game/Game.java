package game;

import board.Board;
import board.BoardEntity;
import board.InvalidBoardException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * This class represents the whole game. It was specifically designed to abstract away all logic
 * related to the management of the game state and processes related to execution of player instructions.
 * The class provides intimate access to its state to allow for the GUI or TUI to update as necessary
 * either gracefully or all at once.
 * <p>
 * Influencing the game state is done via {@link Game#step()} which will run the game.<br>
 * {@link Game#hasNext()} will indicate weather the game still has steps remaining.<br>
 * The associated getters provide an insight into the game state.
 *
 * @author Asad Khan
 */
public class Game {

	/**
	 * Holds game board
	 */
	private Board board;

	/**
	 * List of players that are currently active in game
	 */
	private ArrayList<Player> players;

	/**
	 * Winning player null if no player has one yet
	 */
	private Player winner;

	/**
	 * {@code false} = No draw there is a winner<br>
	 * {@code true} = All instructions complete and there is no winner
	 */
	private boolean draw = false;

	/**
	 * Index of the first player
	 */
	private int startIndex = 0;

	/**
	 * Index of current player to step
	 */
	private int indexOfCurrentPlayer = 0;

	/**
	 * number of players that have been executed on each text
	 * rests after all players have been stepped
	 */
	private int iterations = 0;

	/**
	 * Creates an instance from program file path and board file path.
	 * If either file is not found an {@link java.io.IOException} is raised otherwise
	 * the files are parsed and the appropriate board and player objects are created.
	 *
	 * @param brdPath is a file that contains the representation of the board
	 * @param prgPath is a file that contains the players names and their instructions
	 * @throws InvalidBoardException               In case the board file could not be parsed or parsed incorrectly
	 * @throws InvalidPlayerConfigurationException In case the player file could not be parsed or parsed incorrectly
	 * @throws IOException                         Exception reading game files i.e. no found, unable to read, etc
	 */
	public Game(String brdPath, String prgPath) throws InvalidBoardException, InvalidPlayerConfigurationException, IOException {
		// load game from file
		try {
			this.board = new Board(new ArrayList<>(Files.readAllLines(new File(brdPath).toPath())));
			this.players = this.board.getPlayers();
			List<String> lines = Files.readAllLines(new File(prgPath).toPath());
			this.setInitialPlayerNamesRepr(new ArrayList<>(Arrays.asList(lines.get(1).split(" "))));

			// set initial player instructions
			for (int i = 2; i < lines.size(); i++) {
				String[] tokens = lines.get(i).split(" ");
				for (int x = 0; x < tokens.length; x++) {
					this.players.get(x).addInstruction(tokens[x]);
				}
			}

			this.validatePlayers();

		} catch (IndexOutOfBoundsException e) {
			// alignment inconsistent
			throw new InvalidPlayerConfigurationException("Name and Instruction alignment inconsistent unable to parse");
		}

	}

	/**
	 * Creates instance from {@link java.util.HashMap} (player data) and {@link java.util.ArrayList} (board data)
	 * objects game purpose is to allow for custom game construction from the UI.
	 * <p>
	 * {@code HashMap} structure = {PlayerName: [InstructionBlock1, InstructionBlock2, InstructionBlock3, …]}<br>
	 * {@code ArrayList} structure = [BoardLine0, BoardLine1, BoardLine2, …]
	 *
	 * @param playersHM Data structure contains player data
	 * @param board     Data structure contains board data
	 * @throws InvalidBoardException               In case the board file could not be parsed or parsed incorrectly
	 * @throws InvalidPlayerConfigurationException In case the player file could not be parsed or parsed incorrectly
	 */
	public Game(HashMap<String, ArrayList<String>> playersHM, ArrayList<String> board) throws InvalidBoardException,
			InvalidPlayerConfigurationException {
		// for GUI support
		// HashMAP -> {"Alice": ("FLFWF","RFWFL")} # player stats
		// ArrayList -> [...., ....., ....., .....] # board
		this.board = new Board(board);
		this.players = this.board.getPlayers();
		this.setInitialPlayerNamesRepr(new ArrayList<>(playersHM.keySet()));

		// initialize player instructions
		try {
			int count = 0;
			for (String key : playersHM.keySet()) {
				for (String block : playersHM.get(key)) {
					this.players.get(count).addInstruction(block);
				}
				count++;
			}

			this.validatePlayers();

		} catch (IndexOutOfBoundsException e) {
			// alignment inconsistent
			throw new InvalidPlayerConfigurationException("Name and Instruction alignment inconsistent unable to parse");
		}

	}

	/**
	 * Validates all players.
	 * Calls validation function for each player.
	 *
	 * @throws InvalidPlayerConfigurationException In case the player file could not be parsed or parsed incorrectly
	 * @see Player#validatePlayer()
	 */
	private void validatePlayers() throws InvalidPlayerConfigurationException {
		for (Player p : this.players) {
			p.validatePlayer();
		}

	}

	/**
	 * Reads through unallocated player names and assigns names to players found on the board.
	 * Initial player representation (its letter). All unallocated players are removed from game and board
	 * <p><strong>
	 * Note: first letter of the player name may not necessarily be the same as the players representation.
	 * Assignments are done in order indiscriminate of name or representation
	 * </strong></p>
	 *
	 * @param names player names that need to be assigned
	 * @see Player#setName(String)
	 * @see Player#getName()
	 * @see Player#getRepr()
	 */
	private void setInitialPlayerNamesRepr(ArrayList<String> names) throws InvalidPlayerConfigurationException {
		// set initial player names and repr
		if (this.players.size() < 1 || names.size() < 1) {
			throw new InvalidPlayerConfigurationException("No Players Found");
		}

		for (int i = 0; i < this.players.size() && i < names.size(); i++) {
			Player p = this.players.get(i);
			p.setName(names.get(i)); // sets name
			BoardEntity bE = this.board.getEntity(p.getX(), p.getY());
			bE.setRepr(p.getRepr()); // sets initial repr
		}
		// removes unused players
		this.players.removeIf(p -> p.getName() == null);

	}

	/**
	 * Method indicates weather game still has steps left to execute.
	 * <p>
	 * {@code true} = Game still has steps remaining there is no winner yet or more instructions are available<br>
	 * {@code false} = Game has ended and there is a winner or there are no more instructions to execute therefore a draw
	 *
	 * @return {@code this.winner == null && !this.draw}
	 */
	public boolean hasNext() {
		//true = game still has steps remaining there is no winner yet or more instructions are available<br>
		//false = game has ended and there is a winner or there no more instructions therefore a draw

		return this.winner == null && !this.draw;
		//return !this.draw;
	}

	/**
	 * Runs game for one step<br>
	 * Executes {@link Player#step()} for each player in correct order
	 *
	 * @see #activateAllActions()
	 * @see #startIndex
	 * @see #winner
	 */
	public void step() {

		if (!this.hasNext()) {
			return;
		}

		// if at end of player list
		if (this.iterations == this.players.size()) {

			// activate player and board actions
			this.activateAllActions();

			for (Player p : this.players) {
				if (p.checkWin()) {
					this.winner = p;
					return;
				}
			}

			this.iterations = 0; // rest iterations
			this.startIndex++; // add 1 to start index
			this.indexOfCurrentPlayer = this.startIndex; // set current index to start point
		}

		Player p = this.players.get(this.indexOfCurrentPlayer % this.players.size());
		try {
			p.step();
		} catch (NoMoreInstructionsException e) {
			this.draw = true;
			return;
		}

		this.indexOfCurrentPlayer++;
		this.iterations++;
	}

	/**
	 * activates all players and the board entities under the player
	 */
	private void activateAllActions() {

		// activates board entities under player
		for (Player p : this.players) {

			p.activateEntity();
		}

		this.board.activateLasers(); // board activates laser

		// fires laser
		for (Player p : this.players) {
			p.fireLaser();

		}
	}

	/**
	 * {@code Player} if winner else {@code null}
	 *
	 * @return Gets winner
	 */
	public Player getWinner() {
		return this.winner;
	}

	/**
	 * @return Gets internal board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * @return Gets internal player list
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		// game state as string
		StringBuilder sb = new StringBuilder();
		for (Player p : this.players) {
			sb.append(p);
			sb.append("\n");
		}
		sb.append(this.board.toString());
		return sb.toString();
	}

}
