package gui;

import board.InvalidBoardException;
import game.Game;
import game.InvalidPlayerConfigurationException;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static javax.swing.JOptionPane.showMessageDialog;


public class EntryFrame extends JFrame {


	private JButton fromCustomButton = new JButton("Custom Game");
	private JButton fromGameFileButton = new JButton("Game File");
	private JPanel buttonPanel = new JPanel();
	private Container contentPanel = this.getContentPane();
	public ArrayList<String> globalBoard = new ArrayList<>();
	public boolean panelSet = false;

	public EntryFrame() {
		super("Choose Your Game Type");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BoxLayout(this.contentPanel, BoxLayout.PAGE_AXIS));

		this.buttonPanel.setLayout(new GridLayout(1, 2));
		buttonPanel.add(this.fromCustomButton);
		buttonPanel.add(this.fromGameFileButton);

		this.contentPanel.add(this.buttonPanel);

		this.fromCustomButton.addActionListener(new FromCustomActionListener(this));
		this.fromGameFileButton.addActionListener(new FromGameFileActionListener(this));

	}


	private class FromCustomActionListener implements ActionListener {
		private EntryFrame parentFrame;

		FromCustomActionListener(EntryFrame parentFrame) {
			this.parentFrame = parentFrame;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.parentFrame.panelSet) {
				this.parentFrame.contentPanel.remove(1);
			}
			this.parentFrame.contentPanel.add(new InputCustomGamePanel(this.parentFrame));
			this.parentFrame.pack();
			this.parentFrame.panelSet = true;
		}
	}

	private class FromGameFileActionListener implements ActionListener {
		private EntryFrame parentFrame;

		FromGameFileActionListener(EntryFrame parentFrame) {
			this.parentFrame = parentFrame;

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.parentFrame.panelSet) {
				this.parentFrame.contentPanel.remove(1);
			}
			this.parentFrame.contentPanel.add(new InputGameFilePanel(this.parentFrame));
			this.parentFrame.pack();
			this.parentFrame.panelSet = true;

		}
	}


	private class InputCustomGamePanel extends JPanel {

		private final int ROWS = 5;
		private final int COLS = 4;
		private JPanel fieldPanel = new JPanel();
		private JPanel boardInputPanel = new JPanel();
		private JButton submit = new JButton("Submit");
		private JButton createBoard = new JButton("Create Board");
		private HashMap<JTextField, JTextField> name_instructions = new HashMap<>();
		private JTextField boardRows = new JTextField();
		private JTextField boardCols = new JTextField();

		InputCustomGamePanel(EntryFrame topFrame) {
			super();
			fieldPanel.setLayout(new GridLayout(ROWS, COLS));
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			this.add(fieldPanel);

			for (int i = 1; i < ROWS; i++) {
				fieldPanel.add(new JLabel("Player " + i + " Name:"));
				JTextField name = new JTextField();
				JTextField instruction = new JTextField();
				this.name_instructions.put(name, instruction);
				fieldPanel.add(name);
				fieldPanel.add(new JLabel("Instructions (blocks of 5):"));
				fieldPanel.add(instruction);
			}

			this.boardInputPanel.setLayout(new GridLayout(3, 2));
			this.boardInputPanel.add(new JLabel("Board Rows:"));
			this.boardInputPanel.add(this.boardRows);
			this.boardInputPanel.add(new JLabel("Board Columns:"));

			this.boardInputPanel.add(this.boardCols);
			this.boardInputPanel.add(this.createBoard);

			this.add(this.boardInputPanel);
			this.boardInputPanel.add(this.submit);

			this.submit.addActionListener(new SubmitActionListener(topFrame, this.name_instructions));
			this.createBoard.addActionListener(new CreateBoardActionListener(this.boardRows, this.boardCols, topFrame));

		}

		private class InputBoard extends AbstractTableModel {
			private Object[][] contents;
			private int columnCount;
			private int rowCount;

			public InputBoard(int cols, int rows) {
				super();
				this.columnCount = cols;
				this.rowCount = rows;
				this.contents = new Object[rows][cols];

				for (int i = 0; i < rows; i++) {
					this.contents[i] = new Object[rows];
					for (int x = 0; x < cols; x++) {
						this.contents[i][x] = ".";
					}
				}

			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return true;
			}

			@Override
			public int getRowCount() {
				return this.columnCount;
			}

			@Override
			public int getColumnCount() {
				return this.rowCount;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return this.contents[rowIndex][columnIndex];
			}

			@Override
			public void setValueAt(Object value, int row, int col) {
				contents[row][col] = value;
				fireTableCellUpdated(row, col);
			}
		}

		private class CreateBoardFinishActionListener implements ActionListener {

			private EntryFrame topFrame;
			private JFrame rootFrame;

			public CreateBoardFinishActionListener(EntryFrame topFrame, JFrame rootFrame) {
				this.topFrame = topFrame;
				this.rootFrame = rootFrame;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				JTable jt = (JTable) this.rootFrame.getContentPane().getComponent(0);
				this.topFrame.globalBoard.clear();
				this.topFrame.globalBoard.add("format 1");

				for (int i = 0; i < jt.getRowCount(); i++) {
					StringBuilder sb = new StringBuilder();
					for (int x = 0; x < jt.getColumnCount(); x++) {
						sb.append(jt.getModel().getValueAt(i, x));
					}
					this.topFrame.globalBoard.add(sb.toString());

				}
				this.rootFrame.setVisible(false);
				this.rootFrame.dispose();

				System.out.println(this.topFrame.globalBoard); // TODO remove print


			}
		}

		private class CreateBoardActionListener implements ActionListener {

			private JTextField rows;
			private JTextField cols;
			private EntryFrame topFrame;
			private JButton finished = new JButton("Finished");

			public CreateBoardActionListener(JTextField rows, JTextField cols, EntryFrame topFrame) {
				this.rows = rows;
				this.cols = cols;
				this.topFrame = topFrame;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				String rows = this.rows.getText();
				String cols = this.cols.getText();

				if (!rows.isEmpty() && !cols.isEmpty()) {
					JFrame createBoardFrame = new JFrame("Create Board");
					this.finished.addActionListener(new CreateBoardFinishActionListener(topFrame, createBoardFrame));
					TableModel dataModel = new InputBoard(Integer.parseInt(rows), Integer.parseInt(cols));
					JTable table = new JTable(dataModel);
					table.setFillsViewportHeight(true);
					Container contents = createBoardFrame.getContentPane();
					createBoardFrame.setLayout(new BoxLayout(contents, BoxLayout.Y_AXIS));
					contents.add(table);
					contents.add(this.finished);
					createBoardFrame.pack();
					createBoardFrame.setVisible(true);
				} else {
					showMessageDialog(this.topFrame, "Board size is not specified");

				}
			}

		}

		private class SubmitActionListener implements ActionListener {

			private EntryFrame topFrame;
			private HashMap<JTextField, JTextField> name_instructions;

			public SubmitActionListener(EntryFrame topFrame, HashMap<JTextField, JTextField> name_instructions) {
				this.topFrame = topFrame;
				this.name_instructions = name_instructions;

			}

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap<String, ArrayList<String>> hm = new HashMap<>();
				for (JTextField name : this.name_instructions.keySet()) {
					String pName = name.getText();
					String instructions = this.name_instructions.get(name).getText();
					if (!pName.isEmpty() && !instructions.isEmpty()) {
						hm.put(pName, new ArrayList<>(Arrays.asList(instructions.split(","))));
					}
				}
				if (hm.isEmpty()) {
					showMessageDialog(this.topFrame, "Complete at least one player field");
				} else if (this.topFrame.globalBoard.isEmpty()) {
					showMessageDialog(this.topFrame, "Board as not been created");
				} else {
					try {
						Game g = new Game(hm, this.topFrame.globalBoard);
						startGUI(g);
					} catch (InvalidBoardException e1) {
						showMessageDialog(this.topFrame, "Invalid Board Configuration");
					} catch (InvalidPlayerConfigurationException e1) {
						showMessageDialog(this.topFrame, "Invalid Player Configuration");
					}
				}


			}
		}
	}

	private class InputGameFilePanel extends JPanel {

		private final int ROWS = 3;
		private final int COLS = 2;
		private JButton submit = new JButton("Submit");
		private JTextField gameFilePath = new JTextField();
		private JTextField boardFilePath = new JTextField();

		InputGameFilePanel(EntryFrame topFrame) {
			super();
			this.setLayout(new GridLayout(ROWS, COLS));
			this.add(new JLabel("Game File Path: "));
			this.add(this.gameFilePath);
			this.add(new JLabel("Board File Path: "));
			this.add(this.boardFilePath);
			this.add(this.submit);

			this.submit.addActionListener(new SubmitActionListener(topFrame, this.gameFilePath, this.boardFilePath));
		}

		private class SubmitActionListener implements ActionListener {

			private EntryFrame topFrame;
			private JTextField gameFilePath;
			private JTextField boardFilePath;

			public SubmitActionListener(EntryFrame topFrame, JTextField gameFilePath, JTextField boardFilePath) {
				this.topFrame = topFrame;
				this.gameFilePath = gameFilePath;
				this.boardFilePath = boardFilePath;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!this.gameFilePath.getText().isEmpty() && !this.boardFilePath.getText().isEmpty()) {
					try {
						this.topFrame.startGUI(new Game(this.boardFilePath.getText(), this.gameFilePath.getText()));
					} catch (InvalidBoardException e1) {
						showMessageDialog(this.topFrame, "Invalid Board Configuration");
					} catch (InvalidPlayerConfigurationException e1) {
						showMessageDialog(this.topFrame, "Invalid Player Configuration");
					} catch (IOException e1) {
						showMessageDialog(this.topFrame, "Unable to read board or/and game files");
					}
				} else {
					showMessageDialog(this.topFrame, "Path(s) empty");
				}
			}
		}

	}

	private void startGUI(Game game) {
		Gui gui = new Gui(game);
		this.setVisible(false);
		dispose(); //Destroy the JFrame object
	}
}
