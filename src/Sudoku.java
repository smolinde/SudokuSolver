import java.util.ArrayList;

public class Sudoku {

	private ArrayList <Integer> [][] sudoku;
	private int [][] sudokuAsArray;
	@SuppressWarnings("unchecked")
	public Sudoku (int [][] raw) {
		sudoku = new ArrayList [9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				sudoku [x][y] = new ArrayList <Integer> ();
			}
		}
		sudokuAsArray = raw.clone();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (raw [x][y] == 0) {
					fillField(x, y);
				} else {
					sudoku [x][y].add(raw[x][y]);
				}
			}
		}
	}
	private Sudoku (ArrayList <Integer> [][] s) {
		sudokuAsArray = new int [9][9];
		sudoku = copySudoku (s);
	}
	private void fillField (int x, int y) {
		for (int i = 1; i < 10; i++) {
			sudoku [x][y].add(i); 
		}
	}
	public boolean enoughNumbers () {
		int count = 0;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (sudokuAsArray [x][y] != 0) {
					count++;
				}
				if (count == 17) {break;}
			}
			if (count == 17) {break;}
		}
		return count == 17 ? true : false;
	}

	public boolean correctSyntax () {
		boolean correct = true;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (sudokuAsArray [x][y] != 0) {
					if (checkRow(x, y) || checkColumn (x, y) || checkSquare (x, y)) {
						correct = false;
						break;
					}
				}
			}
			if (!correct) {break;}
		}
		return correct;
	}
	public boolean checkRow (int x, int y) {
		boolean doubleNumber = false;
		for (int i = 0; i < 9; i++) {
			if (sudokuAsArray [x][i] == sudokuAsArray [x][y] && i != y) {
				doubleNumber = true;
				break;
			}
		}
		return doubleNumber;
	}
	public boolean checkColumn (int x, int y) {
		boolean doubleNumber = false;
		for (int i = 0; i < 9; i++) {
			if (sudokuAsArray [i][y] == sudokuAsArray [x][y] && i != x) {
				doubleNumber = true;
				break;
			}
		}
		return doubleNumber;
	}
	public boolean checkSquare (int x, int y) {
		boolean doubleNumber = false;
		int startX = x < 3 ? 0 : x < 6 ? 3 : 6;
		int startY = y < 3 ? 0 : y < 6 ? 3 : 6;
		for (int i = 0; i < 3; i++) {
			for (int t = 0; t < 3; t++) {
				if (sudokuAsArray [startX + i][startY + t] == sudokuAsArray [x][y] && !((startX + i ) == x && (startY + t) == y)) {
					doubleNumber = true;
					break;
				}
			}
			if (doubleNumber) {break;}
		}
		return doubleNumber;
	}
	public ArrayList <Integer> [][] getSudoku () {
		return sudoku;
	}
	public void setOneNumber (int x, int y, int num) {
		sudoku [x][y].clear();
		sudoku [x][y].add(num);
	}
	@SuppressWarnings("unchecked")
	private ArrayList <Integer> [][] copySudoku (ArrayList <Integer> [][] s) {
		ArrayList <Integer> [][] zwsp = new ArrayList [9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				zwsp [x][y] = (ArrayList <Integer>)(s [x][y].clone());
			}
		}
		return zwsp;
	}
	public ArrayList <Integer> [][] solveSudoku (int e) {
		ArrayList <Integer> [][] zwsp = copySudoku(sudoku);
		solvingAlgorithm();
		if (isSolved(sudoku)) {
			return sudoku;
		} else if (isSolvedCorrect(sudoku) && e > 1) {
			for (int l = 2; l < 10; l++) {
				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 9; y++) {
						if (zwsp [x][y].size() == l) {
							for (int i = 0; i < zwsp [x][y].size(); i++) {
								Sudoku sdk = new Sudoku (zwsp);
								sdk.setOneNumber(x, y, zwsp [x][y].get(i));
								sudoku = sdk.solveSudoku((e - 1));
								if (isSolved(sudoku)) {
									return sudoku;
								}
							}
						}
					}
				}
			}
		}
		sudoku = copySudoku(zwsp);
		return zwsp;
	}
	private boolean isSolvedCorrect (ArrayList <Integer> [][] s) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (s[x][y].size() == 1) {
					sudokuAsArray [x][y] = s [x][y].get(0);
				} else {
					sudokuAsArray [x][y] = 0;
				}
			}
		}
		return correctSyntax();
	}
	public void solvingAlgorithm() {
		boolean changedGlobal = false;
		boolean b = false;
		do {
			changedGlobal = false;
			do {} while (solveExplicitNumbers());
			do {
				b = solveClonedNumbers();
				if (!changedGlobal) {
					changedGlobal = b;
				}
			} while (b);
		} while (changedGlobal);
	}
	private boolean isSolved (ArrayList <Integer> [][] s) {
		boolean solved = true;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (s [x][y].size() > 1) { 
					solved = false;
				}
			}
		}
		if (solved) {
			return isSolvedCorrect(s);
		} else {
			return false;
		}
	}
	private boolean solveExplicitNumbers () {
		boolean changed = false;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (sudoku [x][y].size() == 1) {
					if (changed) {
						removeNumber (x, y, sudoku [x][y].get(0));
					} else {
						changed = removeNumber (x, y, sudoku [x][y].get(0));
					}
				}
			}
		}
		return changed;
	}

	private boolean solveClonedNumbers () {
		boolean changed = false;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (sudoku [x][y].size() > 1 && hasClonesInRow(x, y)) {
					for (int i = 0; i < sudoku [x][y].size(); i++) {
						if (changed) {
							removeFromRow (x, y, sudoku [x][y].get(i));
						} else {
							changed = removeFromRow (x, y, sudoku [x][y].get(i));
						}
					}	
				}
				if (sudoku [x][y].size() > 1 && hasClonesInColumn(x, y)) {
					for (int i = 0; i < sudoku [x][y].size(); i++) {
						if (changed) {
							removeFromColumn (x, y, sudoku [x][y].get(i));
						} else {
							changed = removeFromColumn (x, y, sudoku [x][y].get(i));
						}
					}
				}
				if (sudoku [x][y].size() > 1 && hasClonesInSquare(x, y)) {
					for (int i = 0; i < sudoku [x][y].size(); i++) {
						if (changed) {
							removeFromSquare (x, y, sudoku [x][y].get(i));
						} else {
							changed = removeFromSquare (x, y, sudoku [x][y].get(i));
						}
					}
				}
			}
		}
		return changed;
	}
	private boolean hasClonesInRow (int x, int y) {
		int clones = 0;
		for (int i = 0; i < 9; i++) {
			if (sudoku [x][i].equals(sudoku [x][y])) {
				clones++;
			}
		}
		return sudoku [x][y].size() == clones;
	}
	private boolean hasClonesInColumn (int x, int y) {
		int clones = 0;
		for (int i = 0; i < 9; i++) {
			if (sudoku [i][y].equals(sudoku [x][y])) {
				clones++;
			}
		}
		return sudoku [x][y].size() == clones;
	}
	private boolean hasClonesInSquare (int x, int y) {
		int clones = 0;
		int startX = x < 3 ? 0 : x < 6 ? 3 : 6;
		int startY = y < 3 ? 0 : y < 6 ? 3 : 6;
		for (int i = 0; i < 3; i++) {
			for (int t = 0; t < 3; t++) {
				if (sudoku [startX + i][startY + t].equals(sudoku [x][y]) ) {
					clones++;
				}
			}
		}
		return sudoku [x][y].size() == clones;
	}
	private boolean removeNumber (int x, int y, int num) {
		return removeFromRow(x, y, num) || removeFromColumn(x, y, num) || removeFromSquare(x, y, num);
	}
	private boolean removeFromRow (int x, int y, int num) {
		boolean changed = false;
		for (int i = 0; i < 9; i++) {
			if (i != y && !sudoku [x][i].equals(sudoku [x][y]) && sudoku [x][i].size() > 1) {
				if (changed) {
					sudoku [x][i].remove((Object)(num));
				} else {
					changed = sudoku [x][i].remove((Object)(num));
				}
			}
		}
		return changed;
	}
	private boolean removeFromColumn (int x, int y, int num) {
		boolean changed = false;
		for (int i = 0; i < 9; i++) {
			if (i != x && !sudoku [i][y].equals(sudoku [x][y]) && sudoku [i][y].size() > 1) {
				if (changed) {
					sudoku [i][y].remove((Object)(num));
				} else {
					changed = sudoku [i][y].remove((Object)(num));
				}
			}
		}
		return changed;
	}
	private boolean removeFromSquare (int x, int y, int num) {
		boolean changed = false;
		int startX = x < 3 ? 0 : x < 6 ? 3 : 6;
		int startY = y < 3 ? 0 : y < 6 ? 3 : 6;
		for (int i = 0; i < 3; i++) {
			for (int t = 0; t < 3; t++) {
				if (!((startX + i) == x && (startY + t) == y) && !sudoku [startX + i][startY + t].equals(sudoku [x][y]) && sudoku [startX + i][startY + t].size() > 1) {
					if (changed) {
						sudoku [startX + i][startY + t].remove((Object)(num));
					} else {
						changed = sudoku [startX + i][startY + t].remove((Object)(num));
					}
				}
			}
		}
		return changed;
	}
	public void printSudoku () {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (sudoku [x][y].size() == 1) {
					System.out.print(sudoku[x][y].get(0) + " ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
	public void printSudoku (ArrayList <Integer> [][] zwsp) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (zwsp [x][y].size() == 1) {
					System.out.print(zwsp[x][y].get(0) + " ");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}
}