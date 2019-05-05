package gui;

import board.Board;
import board.BoardEntity;
import game.Game;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BoardPanel extends JPanel {

	private Game game;
	private Board board;

	public BoardPanel(Game game) throws IOException {
		this.game = game;
		board = game.getBoard();

		setLayout(new GridLayout(board.getYLen(), board.getXLen()));

		for (int col = 0; col < board.getYLen(); col++) {
			ArrayList<BoardEntity> yBoardEntities = this.board.getBoard().get(col);
			for (int row = 0; row < board.getXLen(); row++) {

				BoardEntity boardEntity = yBoardEntities.get(row);
				ImagePanel cell = new ImagePanel();
				Image image = null;

				switch (boardEntity.getRepr()) {
					case "A":
					case "B":
					case "C":
					case "D":
						setPlayerIcon(boardEntity.getRepr(), boardEntity, cell);
						break;
					case "+":
						image = ImageIO.read(new File("resources/gears/Gear_Clockwise.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "-":
						image = ImageIO.read(new File("resources/gears/Gear_Anticlockwise.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "^":
					case "N":
					case "n":
						image = ImageIO.read(new File("resources/northConveyor/Straight_Conveyor_North.png"));
						cell.setImage(image);
						add(cell);
						break;
					case ">":
					case "E":
					case "e":
						image = ImageIO.read(new File("resources/eastConveyor/Straight_Conveyor_East.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "v":
					case "S":
					case "s":
						image = ImageIO.read(new File("resources/southConveyor/Straight_Conveyor_South.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "<":
					case "W":
					case "w":
						image = ImageIO.read(new File("resources/westConveyor/Straight_Conveyor_West.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "1":
						image = ImageIO.read(new File("resources/flags/flag1/Flag1.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "2":
						image = ImageIO.read(new File("resources/flags/flag2/Flag2.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "3":
						image = ImageIO.read(new File("resources/flags/flag3/Flag3.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "4":
						image = ImageIO.read(new File("resources/flags/flag4/Flag4.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "x":
						image = ImageIO.read(new File("resources/pit.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "[":
						image = ImageIO.read(new File("resources/laser/laserEast/Laser_East.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "]":
						image = ImageIO.read(new File("resources/laser/laserWest/Laser_West.png"));
						cell.setImage(image);
						add(cell);
						break;
					case "(":
						image = ImageIO.read(new File("resources/laser/laserSouth/Laser_South.png"));
						cell.setImage(image);
						add(cell);
						break;
					case ")":
						image = ImageIO.read(new File("resources/laser/laserNorth/Laser_North.png"));
						cell.setImage(image);
						add(cell);
						break;
					default:
						image = ImageIO.read(new File("resources/floor.png"));
						cell.setImage(image);
						add(cell);
				}
			}
		}
	}

	public void setPlayerIcon(String player, BoardEntity boardEntity, ImagePanel cell) throws IOException {

		String playerLetter = player;
		BoardEntity entity = boardEntity;
		Player activePlayer = null;
		ImagePanel boardCell = cell;
		Image image = null;

		for (int x = 0; game.getPlayers().size() > x; x++) {
			if (game.getPlayers().get(x).getRepr().equals(playerLetter)) {
				activePlayer = game.getPlayers().get(x);
			}
		}

		String playerDirection = activePlayer.getDirection();

		if (playerDirection.equals("N")) {
			playerDirection = "North";
		} else if (playerDirection.equals("E")) {
			playerDirection = "East";
		} else if (playerDirection.equals("S")) {
			playerDirection = "South";
		} else if (playerDirection.equals("W")) {
			playerDirection = "West";
		}
		switch (entity.getFinalRepr()) {
			case "+":
			case "-":
				image = ImageIO.read(new File(
						"resources/gears/" + playerLetter + "_" + playerDirection + "_Gear.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "^":
			case "N":
			case "n":
				image = ImageIO.read(new File(
						"resources/northConveyor/" + playerLetter + "_" + playerDirection + "_Conveyor_North.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case ">":
			case "E":
			case "e":
				image = ImageIO.read(new File(
						"resources/eastConveyor/" + playerLetter + "_" + playerDirection + "_Conveyor_East.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "v":
			case "S":
			case "s":
				image = ImageIO.read(new File(
						"resources/southConveyor/" + playerLetter + "_" + playerDirection + "_Conveyor_South.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "<":
			case "W":
			case "w":
				image = ImageIO.read(new File(
						"resources/westConveyor/" + playerLetter + "_" + playerDirection + "_Conveyor_West.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "1":
				image = ImageIO
						.read(new File("resources/flags/flag1/" + playerLetter + "_Flag1_" + playerDirection + ".png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "2":
				image = ImageIO
						.read(new File("resources/flags/flag2/" + playerLetter + "_Flag2_" + playerDirection + ".png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "3":
				image = ImageIO
						.read(new File("resources/flags/flag3/" + playerLetter + "_Flag3_" + playerDirection + ".png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "4":
				image = ImageIO
						.read(new File("resources/flags/flag4/" + playerLetter + "_Flag4_" + playerDirection + ".png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "[":
				image = ImageIO.read(new File(
						"resources/laser/laserEast/" + playerLetter + "_" + playerDirection + "_Laser_East.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "]":
				image = ImageIO.read(new File(
						"resources/laser/laserWest/" + playerLetter + "_" + playerDirection + "_Laser_West.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case "(":
				image = ImageIO.read(new File(
						"resources/laser/laserSouth/" + playerLetter + "_" + playerDirection + "_Laser_South.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			case ")":
				image = ImageIO.read(new File(
						"resources/laser/laserNorth/" + playerLetter + "_" + playerDirection + "_Laser_North.png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
			default:
				image = ImageIO.read(
						new File("resources/playerDefault/" + playerLetter + "_Default_" + playerDirection + ".png"));
				boardCell.setImage(image);
				add(boardCell);
				break;
		}
	}

	public void refreshBoard() {
		this.revalidate();
		this.repaint();
	}
}