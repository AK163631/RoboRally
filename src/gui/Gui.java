package gui;

import game.Game;
import game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.JOptionPane.showMessageDialog;

// need to be able to create a game in the gui

@SuppressWarnings("serial")
public class Gui extends JPanel {

	protected Game game;
	private JPanel playerStats = new JPanel();
	private JFrame frame;
	private JButton step = new JButton("step");

	public Gui(Game game) {

		this.game = game;

		EventQueue.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| UnsupportedLookAndFeelException ex) {
				ex.printStackTrace();
			}

			this.frame = new JFrame("Robot Rally");
			this.frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
			this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.frame.add(this.playerStats);

			this.step.addActionListener(new StepActionListener(this));
			this.frame.add(this.step);

			try {
				this.frame.add(new BoardPanel(game));
			} catch (IOException e) {
				showMessageDialog(this.frame, "Unable to read data file");
				this.frame.setVisible(false);
				this.frame.dispose();
			}

			this.UpdatePlayerStatsFrame();

			frame.pack();
			frame.setVisible(true);
		});
	}

	private void UpdatePlayerStatsFrame() {
		this.playerStats.removeAll();
		ArrayList<ArrayList<JLabel>> stats = new ArrayList<>();
		ArrayList<Player> players = this.game.getPlayers();
		this.playerStats.setLayout(new GridLayout(players.size(), 6));
		for (Player p : this.game.getPlayers()) {
			ArrayList<JLabel> labels = new ArrayList<>();
			labels.add(new JLabel("Name: " + p.getName()));
			labels.add(new JLabel("Health: " + p.getHealth()));
			labels.add(new JLabel("Current Location: " + p.getX() + "," + p.getY()));
			labels.add(new JLabel("Instructions: " + Arrays.toString(p.getInstructions().toArray())));
			labels.add(new JLabel("Current Instruction: " + p.getCurrentInstruction()));
			labels.add(new JLabel("Flags collected: " + Arrays.toString(p.getFlags().toArray())));
			stats.add(labels);
		}

		for (ArrayList<JLabel> al : stats) {
			for (JLabel l : al) {
				this.playerStats.add(l);
			}
		}
	}

	private void updateGui() {
		frame.getContentPane().remove(2); // remove middle panel which is the board
		try {
			frame.add(new BoardPanel(this.game));
		} catch (IOException e) {
			showMessageDialog(this.frame, "Unable to read data file");
			this.frame.setVisible(false);
			this.frame.dispose();
		}
		frame.revalidate();
		frame.repaint();
	}

	private class StepActionListener implements ActionListener {

		public Gui topFrame;
		public Game game;

		public StepActionListener(Gui topFrame) {
			this.topFrame = topFrame;
			this.game = this.topFrame.game;

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.game.hasNext()) {
				this.game.step();
				topFrame.UpdatePlayerStatsFrame();
				topFrame.updateGui();
			} else {
				if (this.game.getWinner() != null) {
					showMessageDialog(this.topFrame, this.game.getWinner().getName() + " has won!");
				} else {
					showMessageDialog(this.topFrame, "Draw!");
				}
			}

		}
	}

}
