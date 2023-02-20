//package shipSink;

public class shipTable {

	// shipTable values:
	// 0 = empty
	// 1 = ship
	// 2 = missed shot
	// 3 = ship hit

	private int[][] shipTable = new int[10][10];

	public void setShip(int x, int y, int z) {
		if (x >= 0 && x < 10 && y >= 0 && y < 10) {
			this.shipTable[x][y] = z;
		}
	}

	public int getShip(int x, int y) {
		return this.shipTable[x][y];
	}

	public boolean checkIfWin() {
		boolean win = true;
		for (int i = 0; i < 10 && win == true; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.shipTable[i][j] == 1) {
					win = false;
					break;
				}
			}
		}
		return win;
	}

	public int[] findNextShip() {
		int[] ship = { -1, -1 };
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.shipTable[i][j] == 1) {
					ship[0] = i;
					ship[1] = j;
					return ship;
				}
			}
		}
		return ship;
	}

	// For minesweeper mode counting how many ships are next to where player shoots
	public int scanShipNumber(int x, int y) {
		int sum = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (x + i >= 0 && x + i < 10 && y + j >= 0 && y + j < 10) {
					if (this.shipTable[x + i][y + j] == 3 || this.shipTable[x + i][y + j] == 1) {
						sum++;
					}
				}
			}
		}
		if (sum == 0) {
			// If nothing was found we return missed shot
			return 2;
		} else {
			// We multiply result with 10 to distinquish other possible values (0-3)
			sum = sum * 10;
			return sum;
		}
	}

	// Scanning
	public boolean scanShips(int x, int y) {
		boolean result = true;
		if (x > 0) {
			if (this.shipTable[x - 1][y] == 1) {
				result = false;
			}
		}
		if (x < 9) {
			if (this.shipTable[x + 1][y] == 1) {
				result = false;
			}
		}
		if (y > 0) {
			if (this.shipTable[x][y - 1] == 1)
				result = false;
		}
		if (y < 9) {
			if (this.shipTable[x][y + 1] == 1)
				result = false;
		}
		return result;
	}
}