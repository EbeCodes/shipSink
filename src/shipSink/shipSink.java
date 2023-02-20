//package shipSink;

public class shipSink {
	static shipTable playersShips = new shipTable(); //Shiptable for player's ships
	static shipTable aiShips = new shipTable(); //Shiptable for ai's ships
	static int shipsToPlace[] = { 5, 4, 3, 3, 2, 2, 1 }; // Ships and their lengths
	static int difficulty = 0; // Difficulty setting 0,1,2,3
	static boolean shipSweeper = false; // Minesweeper setting
	static shipUi s = new shipUi(); // Creating GUI
	static highScores scores = new highScores();

	// Variables for game status
	static int playerScore;
	static int aiScore;
	static int aiMiss;
	static int turnNumber;
	static int hitsInRow;
	static boolean lastWasHit;
	static boolean playerTurn;

	public static void main(String[] args) {

	}

	public static void setupBeginnig() {
		// Resetting game variables
		aiMiss = 0;
		playerScore = 0;
		aiScore = 0;
		turnNumber = 1;
		hitsInRow = 0;
		lastWasHit = false;
		playerTurn = true;
		// Setting up ai ships using the method
		aiClearShips();
		playerClearShips();
		aiSetShips();
		s.resetShipButtons();
		playerSetShips();
		s.disableAiButtons();
		s.disablePlayerButtons();
		s.updateTurn(turnNumber);
		s.setPlayerScore(playerScore);
		s.setAIScore(aiScore);
	}

	// Method for setting playerShips
	public static void playerSetShips() {
		playersShips = setShips(playersShips);
		updatePlayersShips();
	}

	// Method for setting aiShips
	public static void aiSetShips() {
		aiShips = setShips(aiShips);
	}

	// method for clearing ships
	public static void playerClearShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				playersShips.setShip(i, j, 0);
				s.setPlayerButton(i, j, 0);
			}
		}
	}

	// method for clearing aiShips
	public static void aiClearShips() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				aiShips.setShip(i, j, 0);
				s.setAiButton(i, j, 0);
			}
		}
	}

	// Method for setting ships into random locations of the grid
	public static shipTable setShips(shipTable s) {
		boolean shipFits = false;
		for (int i = 0; i < shipsToPlace.length; i++) {
			int startPlacingFromX = (int) (Math.random() * 10);
			int startPlacingFromY = (int) (Math.random() * 10);

			if (s.getShip(startPlacingFromX, startPlacingFromY) == 0) {
				int direction = (int) (Math.random() * 2);
				if (direction == 0) {
					if (startPlacingFromX + shipsToPlace[i] < 10) {
						shipFits = true;
						for (int j = 0; j < shipsToPlace[i] && shipFits == true; j++) {
							shipFits = s.scanShips(startPlacingFromX + j, startPlacingFromY);
							if (shipFits == true && s.getShip(startPlacingFromX + j, startPlacingFromY) == 0)
								shipFits = true;
							else
								shipFits = false;
						}
						if (shipFits == true) {
							for (int j = 0; j < shipsToPlace[i]; j++) {
								s.setShip(startPlacingFromX + j, startPlacingFromY, 1);
							}
						}
					}
				} else if (direction == 1) {
					if (startPlacingFromY + shipsToPlace[i] < 10) {
						shipFits = true;
						for (int j = 0; j < shipsToPlace[i] && shipFits == true; j++) {
							shipFits = s.scanShips(startPlacingFromX, startPlacingFromY + j);
							if (shipFits == true && s.getShip(startPlacingFromX, startPlacingFromY + j) == 0)
								shipFits = true;
							else
								shipFits = false;
						}
						if (shipFits == true) {
							for (int j = 0; j < shipsToPlace[i]; j++) {
								s.setShip(startPlacingFromX, startPlacingFromY + j, 1);
							}
						}
					}
				}
			}
			if (!shipFits)
				i--;
			else
				shipFits = false;
		}
		return s; //return shiptable 
	}

	// Refreshing buttons in ShipUi
	public static void updatePlayersShips() {
		for (int i=0; i<10; i++) {
			for (int j=0; j<10; j++) {
				int z = playersShips.getShip(i, j);
				if (z != 0) {
					s.setPlayerButton(i, j, z);
				}
			}
		}
	}
	
	// For saving changed settings
	public static void saveSettings(int d, int[] s, boolean m) {
		difficulty = d;
		shipsToPlace = s;
		shipSweeper = m;
	}

	public static void playerShoots(int x, int y) {
		if (playerTurn) {
			playerTurn = false;
			lastWasHit = false;
			int z;
			z = aiShips.getShip(x, y);
			if (z == 0) {
				aiShips.setShip(x, y, 2);
				z = 2;
				if (shipSweeper)
					z = aiShips.scanShipNumber(x, y);
				s.updateText("\nPlayer misses.");
				hitsInRow = 0;
				if (playerScore > 0)
					playerScore -= 10;
			} else if (z == 1) {
				aiShips.setShip(x, y, 3);
				s.updateText("\nPlayer hits AI ship!");
				lastWasHit = true;
				z = 3;
				playerScore += 100 + hitsInRow * 10;
				hitsInRow++;
				// TODO
				// Add check if ship was sunk.
				// Add a visual feedback into shipUi
			}
			s.setAiButton(x, y, z);
			if (!lastWasHit) {
				turnNumber++;
				s.updateTurn(turnNumber);
			}
			s.setPlayerScore(playerScore);
			if (aiShips.checkIfWin()) {
				if(highScores.getLowestScore() < playerScore) {
					s.updateText("\nNew high score!");
					s.gameOver("Player wins the game!\n New high score!", "Outstanding");
					s.newHighScore(playerScore);
				}
				else {
					s.gameOver("Player wins the game!", "Congratulations");
				}
			}
			if (lastWasHit) {
				playerTurn = true;
			} else {
				aiShoots();
			}
		}
	}

	public static void aiShoots() {
		lastWasHit = false;
		hitsInRow = 0;
		do {
			int z;
			int x = (int) (Math.random() * 10);
			int y = (int) (Math.random() * 10);
			// Checking difficulty and if it's time for AI to cheat
			if (difficulty == 3 && aiMiss == 1) {
				int cheat[] = playersShips.findNextShip();
				x = cheat[0];
				y = cheat[1];
			} else if (difficulty == 2 && aiMiss == 3) {
				int cheat[] = playersShips.findNextShip();
				x = cheat[0];
				y = cheat[1];
			} else if (difficulty == 1 && aiMiss == 6) {
				int cheat[] = playersShips.findNextShip();
				x = cheat[0];
				y = cheat[1];
			}
			z = playersShips.getShip(x, y);
			// AI hits ship
			if (z == 1) {
				playersShips.setShip(x, y, 3);
				lastWasHit = true;
				s.setPlayerButton(x, y, 3);
				s.updateText("\nAI hits players ship!");
				hitsInRow++;
				aiScore += 100 + hitsInRow * 10;
				aiMiss = 0;
			}
			// Missing shot
			else if (z == 0) {
				playersShips.setShip(x, y, 2);
				s.setPlayerButton(x, y, 2);
				lastWasHit = false;
				s.updateText("\nAI misses.");
				s.updateText("\n-----Turn " + Integer.toString(turnNumber) + " -----");
				hitsInRow = 0;
				aiMiss++;
				if (aiScore > 0)
					aiScore -= 10;
			}
			// Coordinates have already been shot
			else {
				lastWasHit = true;
			}
		} while (lastWasHit);
		s.setAIScore(aiScore);
		if (playersShips.checkIfWin()) {
			s.updateText("\nGame over. Player lost all ships.");
			s.gameOver("You lose!", "Totaled");
		}
		playerTurn = true;
	}
}