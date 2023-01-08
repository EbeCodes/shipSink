//package shipSink;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class shipUi extends Frame {

	private static final long serialVersionUID = 1L;
	private Button[][] aiShipButtons = new Button[10][10];
	private Button[][] playerShipButtons = new Button[10][10];
	private Button buttonStart = new Button("Start");
	private Button buttonDifficulty = new Button("Difficulty");
	private Button buttonScores = new Button("Scores");
	private Button buttonQuit = new Button("Quit");
	private int buttonWidth = 20;
	private int buttonHeigth = 20;
	private int topBorder = 60;
	private Panel menuUI = new Panel();
	private Panel gameUI = new Panel();
	private Panel panelShipPlacement = new Panel();
	private Panel panelScore = new Panel();
	private Button buttonPlaceShips = new Button("Replace");
	private Button buttonStopPlacing = new Button("Start");
	private Button buttonCancel = new Button("Cancel");
	private Button buttonExitGame = new Button("Quit");
	private Label labelTitle = new Label("Ship Sink");
	private Label labelPlayersShips = new Label("Own ships");
	private Label labelOpponent = new Label("Opponent ships");
	private TextArea textArea = new TextArea();
	private Label labelTurn = new Label("Turn");
	private Label labelTurnNumber = new Label("");
	private Label labelScores = new Label("Scores");
	private Label labelPlayer = new Label("Player:");
	private Label labelPlayerScore = new Label("");
	private Label labelAI = new Label("AI:");
	private Label labelAIScore = new Label("");

	// Settings
	private Panel settingsUI = new Panel();
	private Label labelDifficutly = new Label("Difficulty");
	private Label labelShipAmount = new Label("Amount of ships");
	private JRadioButton difficultyRbEasy = new JRadioButton("Easy", true);
	private JRadioButton difficultyRbMedium = new JRadioButton("Medium", false);
	private JRadioButton difficultyRbHard = new JRadioButton("Hard", false);
	private JRadioButton difficultyRbImpossible = new JRadioButton("Impossible", false);
	private Button difficultyBtnOk = new Button("OK");
	private JComboBox<String> difficultyShipAmount = new JComboBox<String>();
	ButtonGroup grpDifficulty = new ButtonGroup();
	private Label labelCheats = new Label("Cheats");
	JCheckBox chkShipSweeper = new JCheckBox("Minesweeper mode");

	shipUi() {

		// Basic settings for the window view
		int UiHeight = 410;
		int UiWidth = 600;
		setSize(UiWidth, UiHeight);
		// setLocation(100,100);
		setLocationRelativeTo(null); //sets location to center of screen
		setLayout(null);
		setTitle("Ship Sink");

		// Makes window close button terminate the program
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		// MenuUI
		menuUI.setBounds(0, 0, UiWidth, UiHeight);
		menuUI.setLayout(null);
		menuUI.setVisible(true);
		add(menuUI);
		labelTitle.setBounds(180, 50, 400, 60);
		labelTitle.setFont(new Font("Times Roman", Font.BOLD, 50));
		buttonStart.setBounds(100, 140, 400, 40);
		buttonDifficulty.setBounds(100, 190, 400, 40);
		buttonScores.setBounds(100, 240, 400, 40);
		buttonQuit.setBounds(100, 290, 400, 40);
		menuUI.add(labelTitle);
		menuUI.add(buttonStart);
		menuUI.add(buttonDifficulty);
		menuUI.add(buttonScores);
		menuUI.add(buttonQuit);
		buttonStart.addActionListener(new ButtonListener());
		buttonQuit.addActionListener(new ButtonListener());

		// SettingsUI
		settingsUI.setBounds(0, 0, UiWidth, UiHeight);
		settingsUI.setLayout(null);
		settingsUI.setVisible(false);
		labelDifficutly.setBounds(100, 70, 400, 20);
		difficultyRbEasy.setBounds(100, 90, 400, 20);
		difficultyRbMedium.setBounds(100, 110, 400, 20);
		difficultyRbHard.setBounds(100, 130, 400, 20);
		difficultyRbImpossible.setBounds(100, 150, 400, 20);
		labelShipAmount.setBounds(100, 180, 400, 20);
		difficultyShipAmount.setBounds(100, 200, 400, 20);
		labelCheats.setBounds(100, 230, 400, 20);
		chkShipSweeper.setBounds(100, 250, 400, 20);
		chkShipSweeper.setBackground(null);
		difficultyBtnOk.setBounds(200, 300, 200, 40);
		grpDifficulty.add(difficultyRbEasy);
		grpDifficulty.add(difficultyRbMedium);
		grpDifficulty.add(difficultyRbHard);
		grpDifficulty.add(difficultyRbImpossible);
		difficultyRbEasy.setBackground(null);
		difficultyRbMedium.setBackground(null);
		difficultyRbHard.setBackground(null);
		difficultyRbImpossible.setBackground(null);
		difficultyShipAmount.addItem("Less");
		difficultyShipAmount.addItem("Default");
		difficultyShipAmount.addItem("More");
		difficultyShipAmount.setSelectedIndex(1);
		settingsUI.add(labelDifficutly);
		settingsUI.add(difficultyRbEasy);
		settingsUI.add(difficultyRbMedium);
		settingsUI.add(difficultyRbHard);
		settingsUI.add(difficultyRbImpossible);
		settingsUI.add(difficultyBtnOk);
		settingsUI.add(difficultyShipAmount);
		settingsUI.add(labelShipAmount);
		settingsUI.add(labelCheats);
		settingsUI.add(chkShipSweeper);
		difficultyBtnOk.addActionListener(new ButtonListener());
		add(settingsUI);

		// GameUi
		labelOpponent.setBounds(buttonWidth, 50, buttonWidth * 10, 20);
		labelPlayersShips.setBounds(buttonWidth * 19, 50, buttonWidth * 10, 20);
		panelShipPlacement.setBounds(buttonWidth * 11, topBorder + buttonHeigth, buttonWidth * 7, buttonHeigth * 10);
		panelShipPlacement.setLayout(null);
		panelScore.setBounds(buttonWidth * 11, topBorder + buttonHeigth, buttonWidth * 7, buttonHeigth * 10);
		panelScore.setLayout(null);
		labelTurn.setBounds(buttonWidth, 0, buttonWidth * 3, 20);
		labelTurnNumber.setBounds(buttonWidth * 4, 0, buttonWidth * 3, 20);
		labelScores.setBounds(buttonWidth, 40, buttonWidth * 6, 20);
		labelPlayer.setBounds(buttonWidth, 60, buttonWidth * 3, 20);
		labelPlayerScore.setBounds(buttonWidth * 4, 60, buttonWidth * 3, 20);
		labelAI.setBounds(buttonWidth, 80, buttonWidth * 3, 20);
		labelAIScore.setBounds(buttonWidth * 4, 80, buttonWidth * 3, 20);
		buttonPlaceShips.setBounds(buttonWidth, 0, buttonWidth * 6, 50);
		buttonStopPlacing.setBounds(buttonWidth, 75, buttonWidth * 6, 50);
		buttonCancel.setBounds(buttonWidth, 150, buttonWidth * 6, 50);
		buttonExitGame.setBounds(buttonWidth, 150, buttonWidth * 6, 50);
		textArea.setBounds(buttonWidth, buttonHeigth * 12 + topBorder, buttonWidth * 28, 90);
		textArea.setEditable(false);
		textArea.setFocusable(false);
		panelShipPlacement.add(buttonPlaceShips);
		panelShipPlacement.add(buttonCancel);
		panelShipPlacement.add(buttonStopPlacing);
		panelScore.add(labelTurn);
		panelScore.add(labelTurnNumber);
		panelScore.add(labelPlayer);
		panelScore.add(labelPlayerScore);
		panelScore.add(labelAI);
		panelScore.add(labelAIScore);
		panelScore.add(buttonExitGame);
		panelScore.add(labelScores);
		gameUI.add(labelPlayersShips);
		gameUI.add(labelOpponent);
		gameUI.add(textArea);
		gameUI.setLayout(null);
		gameUI.setBounds(0, 0, UiWidth, UiHeight);
		gameUI.setVisible(false);
		gameUI.add(panelShipPlacement);
		gameUI.add(panelScore);
		add(gameUI);
		buttonStopPlacing.addActionListener(new ButtonListener());
		buttonCancel.addActionListener(new ButtonListener());
		buttonPlaceShips.addActionListener(new ButtonListener());
		buttonExitGame.addActionListener(new ButtonListener());
		buttonDifficulty.addActionListener(new ButtonListener());

		// Creating buttons for aiShips
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				aiShipButtons[i][j] = new Button("");
				aiShipButtons[i][j].setBounds((j + 1) * buttonWidth, (i + 1) * buttonHeigth + topBorder, buttonWidth,
						buttonHeigth);
				gameUI.add(aiShipButtons[i][j]);
				aiShipButtons[i][j].setFocusable(false);
				aiShipButtons[i][j].addActionListener(new ButtonListener());
				aiShipButtons[i][j].addMouseListener(new ML());
			}
		}
		// Creating buttons for playerShips
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerShipButtons[i][j] = new Button("");
				playerShipButtons[i][j].setBounds((j + 1) * buttonWidth + (buttonWidth * 18),
						(i + 1) * buttonHeigth + topBorder, buttonWidth, buttonHeigth);
				gameUI.add(playerShipButtons[i][j]);
				playerShipButtons[i][j].setEnabled(false);
			}
		}
		setVisible(true); // Sets the frame visible	
	}

	public void setAiButton(int x, int y, int z) {
		// shipTable values:
		// 0 = empty
		// 1 = ship
		// 2 = missed shot
		// 3 = ship hit
		// >9 = minesweeper mode is on and passed value is divided by 10 and shown as button label
		if (z == 3) {
			aiShipButtons[x][y].setBackground(Color.GREEN);
			aiShipButtons[x][y].setFont(new Font("Arial", Font.BOLD, 14));
			aiShipButtons[x][y].setLabel("X");
		} else if (z == 2)
			aiShipButtons[x][y].setLabel("O");
		else if (z > 9) {
			aiShipButtons[x][y].setLabel(Integer.toString(z / 10));
		} else
			aiShipButtons[x][y].setLabel("");
	}

	public void setPlayerButton(int x, int y, int z) {
		if (z == 0)
			playerShipButtons[x][y].setLabel("");
		else if (z == 1) {
			playerShipButtons[x][y].setLabel("S");
			playerShipButtons[x][y].setBackground(Color.YELLOW);
		} else if (z == 2) {
			playerShipButtons[x][y].setFont(new Font("Arial", Font.BOLD, 14));
			playerShipButtons[x][y].setLabel("O");
		} else if (z == 3) {
			playerShipButtons[x][y].setLabel("X");
			playerShipButtons[x][y].setBackground(Color.RED);
		}
	}

	public void enablePlayerButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerShipButtons[i][j].setEnabled(true);

			}
		}
	}

	public void disablePlayerButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerShipButtons[i][j].setEnabled(false);
			}
		}
	}

	public void enableAiButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				aiShipButtons[i][j].setEnabled(true);
				aiShipButtons[i][j].setBackground(null);
			}
		}
	}

	public void disableAiButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				aiShipButtons[i][j].setEnabled(false);
			}
		}
	}

	public void resetShipButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playerShipButtons[i][j].setBackground(Color.LIGHT_GRAY);
				playerShipButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
				aiShipButtons[i][j].setBackground(Color.LIGHT_GRAY);
				aiShipButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 14));
			}
		}
	}

	public void gameOver(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void updateText(String text) {
		textArea.append(text);
	}

	public void updateTurn(int turn) {
		labelTurnNumber.setText(Integer.toString(turn));
	}

	public void setPlayerScore(int score) {
		labelPlayerScore.setText(Integer.toString(score));
	}

	public void setAIScore(int score) {
		labelAIScore.setText(Integer.toString(score));
	}

	private void saveSettings() {
		int d = 0;
		ArrayList<Integer> arrList = new ArrayList<Integer>();
		if (difficultyRbEasy.isSelected()) {
			d = 0;
		} else if (difficultyRbMedium.isSelected()) {
			d = 1;
		} else if (difficultyRbHard.isSelected()) {
			d = 2;
		} else if (difficultyRbImpossible.isSelected()) {
			d = 3;
		}
		if (difficultyShipAmount.getSelectedIndex() == 0) {
			Collections.addAll(arrList, 4, 3, 2, 2, 1);
		} else if (difficultyShipAmount.getSelectedIndex() == 1) {
			Collections.addAll(arrList, 5, 4, 3, 3, 2, 2, 1);
		} else if (difficultyShipAmount.getSelectedIndex() == 2) {
			Collections.addAll(arrList, 5, 4, 4, 3, 3, 3, 2, 2, 1);
		}
		int[] s = new int[arrList.size()];
		for (int i = 0; i < arrList.size(); i++) {
			s[i] = arrList.get(i);
		}
		shipSink.saveSettings(d, s, chkShipSweeper.isSelected());
	}

	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			// Gets the source of which button is pressed
			Object Source = e.getSource();

			// Loops through button grid to figure out which was pressed
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (Source.equals(aiShipButtons[i][j])) {
						// After found where player wants to shoot
						// button is disabled and result is being
						// fetched from shipTable object
						aiShipButtons[i][j].setEnabled(false);
						shipSink.playerShoots(i, j);
						// aiShipButtons[i][j].setLabel(Integer.toString(shipSink.aiShips.getShip(i,
						// j)));
						break;
					}
				}
			}
			if (Source.equals(buttonStart)) {
				shipSink.setupBeginnig();
				textArea.setText("New game! Place your ships.");
				menuUI.setVisible(false);
				panelScore.setVisible(false);
				panelShipPlacement.setVisible(true);
				gameUI.setVisible(true);
			}
			if (Source.equals(buttonQuit)) {
				System.exit(0);
			}
			if (Source.equals(buttonPlaceShips)) {
				shipSink.playerClearShips();
				resetShipButtons();
				shipSink.playerSetShips();
				textArea.append("\nReplacing player ships.");
			}
			if (Source.equals(buttonCancel)) {
				gameUI.setVisible(false);
				menuUI.setVisible(true);
			}
			if (Source.equals(buttonExitGame)) {
				int reply = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit?",
						JOptionPane.YES_NO_OPTION);
				if (reply == JOptionPane.YES_OPTION) {
					gameUI.setVisible(false);
					menuUI.setVisible(true);
				}
			}
			// Game starting when ships are placed
			if (Source.equals(buttonStopPlacing)) {
				enableAiButtons();
				panelShipPlacement.setVisible(false);
				panelScore.setVisible(true);
				textArea.setText("Game begins. Players turn.");
				updateText("\n-----Turn 1-----");
			}
			if (Source.equals(buttonDifficulty)) {
				menuUI.setVisible(false);
				settingsUI.setVisible(true);
			}
			if (Source.equals(difficultyBtnOk)) {
				saveSettings();
				settingsUI.setVisible(false);
				menuUI.setVisible(true);
			}
		}
	}

	// Mouselistener to add some colors in button grid
	private class ML implements MouseListener {
		//hovering the button grid paints buttons black
		public void mouseEntered(MouseEvent me) {
			Button button = (Button) me.getSource();
			button.setBackground(Color.BLACK);
		}

		@Override
		public void mouseClicked(MouseEvent me) {
			// TODO Auto-generated method stub

		}

		
		public void mousePressed(MouseEvent me) {
			Button button = (Button) me.getSource();
			button.setBackground(Color.RED);
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			Button button = (Button) me.getSource();
			button.setBackground(Color.WHITE);
		}

		@Override
		public void mouseExited(MouseEvent me) {
			Button button = (Button) me.getSource();
			Color testi = button.getBackground();
			if (testi != Color.GREEN)
				button.setBackground(null);
		}
	}
}