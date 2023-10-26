import java.awt.EventQueue;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
public class SudokuSolver {

	/**
	 * 
	 */
	private JFrame frmSudokusolver;
	private int xpos = -1;
	private int ypos = -1;
	private Sudoku s;
	private boolean isSolved = false;
	private Random rnd = new Random();
	private ArrayList <Integer> [][] solvedSudoku;
	private String [] messages = {"Stop wasting my time.", "Wow, that was useless.", "What are you trying to summon?", "You can't erase the void.", "That's the wrong approach to use me.", "404", "Using me the first time?", "Are you a Quality Engineer?", "Try it again. Nothing will ever change.", "Stop it. Give me your Sudoku.", "This field is now as empty as your head."};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SudokuSolver window = new SudokuSolver();
					window.frmSudokusolver.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	public SudokuSolver() throws FontFormatException, IOException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 * @throws FontFormatException 
	 */
	private void initialize() throws FontFormatException, IOException {
		frmSudokusolver = new JFrame();
		frmSudokusolver.getContentPane().setBackground(Color.WHITE);
		frmSudokusolver.setResizable(false);
		frmSudokusolver.setTitle("SudokuSolver");
		frmSudokusolver.setBounds(100, 100, 1000, 608);
		frmSudokusolver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSudokusolver.getContentPane().setLayout(null);

		JTextPane infobox = new JTextPane();
		infobox.setText("None.");
		infobox.setFont(new Font("Arial", Font.PLAIN, 16));
		infobox.setEditable(false);
		infobox.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 2), "Info:", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(255, 0, 0)));
		infobox.setBackground(Color.WHITE);
		infobox.setBounds(582, 372, 400, 195);
		frmSudokusolver.getContentPane().add(infobox);

		JPanel [] lines = getLines();
		for (int x = 0; x < 24; x++) {
			frmSudokusolver.getContentPane().add(lines[x]);
		}
		JPanel [] lines2 = getLines2();
		for (int x = 0; x < 9; x++) {
			frmSudokusolver.getContentPane().add(lines2[x]);
		}
		JButton [][] buttons = getDefaultButtons ();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				frmSudokusolver.getContentPane().add(buttons[x][y]);
				buttons[x][y].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						if (xpos > -1 && ypos > -1) {
							buttons [xpos][ypos].setBackground(Color.WHITE);
						}
						xpos = Integer.parseInt("" + arg0.getComponent().getName().charAt(1));
						ypos = Integer.parseInt("" + arg0.getComponent().getName().charAt(2));
						if (isSolved) {
							if (solvedSudoku [xpos][ypos].size() > 1) {
								infobox.setText("Possible Numbers: " + solvedSudoku [xpos][ypos].toString());
							} else {
								infobox.setText("None.");
							}
						} else {
							infobox.setText("Field selected.");
						}
						buttons [xpos][ypos].setBackground(Color.YELLOW);
					}
				});
			}
		}
		JButton [][] buttons2 = getDefaultButtons2 ();
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				frmSudokusolver.getContentPane().add(buttons2[x][y]);
				buttons2[x][y].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent arg0) {
						if (xpos >= 0 && ypos >= 0 && !isSolved) {
							infobox.setText("Number set.");
							buttons [xpos][ypos].setText("" + arg0.getComponent().getName().charAt(11));
						} else {
							infobox.setText("None.");
						}
					}
				});
			}
		}
		JButton delete = new JButton("<");
		delete.setForeground(Color.RED);
		delete.setFont(new Font("Arial", Font.BOLD, 26));
		delete.setFocusable(false);
		delete.setFocusTraversalKeysEnabled(false);
		delete.setFocusPainted(false);
		delete.setBorder(null);
		delete.setBackground(Color.WHITE);
		delete.setBounds(691, 300, 58, 58);
		frmSudokusolver.getContentPane().add(delete);

		JButton solve = new JButton("SOLVE");
		solve.setForeground(new Color(0, 128, 0));
		solve.setFont(new Font("Arial", Font.BOLD, 24));
		solve.setFocusable(false);
		solve.setFocusTraversalKeysEnabled(false);
		solve.setFocusPainted(false);
		solve.setBorder(null);
		solve.setBackground(Color.WHITE);
		solve.setBounds(753, 300, 120, 58);
		frmSudokusolver.getContentPane().add(solve);
		
		JLabel lblNewLabel = new JLabel("'Your Sudoku. My Answer.'");
		lblNewLabel.setForeground(Color.MAGENTA);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(582, 13, 400, 44);
		frmSudokusolver.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("- Created by Denis Smolin -");
		lblNewLabel_1.setFont(new Font("Arial", Font.ITALIC, 16));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(582, 56, 400, 29);
		frmSudokusolver.getContentPane().add(lblNewLabel_1);

		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (isSolved) {
					infobox.setText("None.");
				} else if (xpos >= 0 && ypos >= 0) {
					if (!buttons [xpos][ypos].getText().equals("")) {
						infobox.setText("Number deleted.");
						buttons [xpos][ypos].setText("");
					} else {
						infobox.setText(messages[rnd.nextInt(messages.length)]);
					}
				} else {
					infobox.setText("None.");
				}
			}
		});
		solve.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (isSolved) {
					infobox.setText("Resetting...");
					buttons [xpos][ypos].setBackground(Color.WHITE);
				} else {
					infobox.setText("Solving...");
					if (xpos > -1 && ypos > -1) {
						buttons [xpos][ypos].setBackground(Color.WHITE);
					}	
				}
			}
			public void mouseReleased (MouseEvent e) {
				if (isSolved) {
					isSolved = false;
					xpos = -1;
					ypos = -1;
					infobox.setText("None.");
					resetButtons(buttons);
					solve.setText("SOLVE");
					solve.setForeground(new Color(0, 128, 0));
				} else {
					setSudoku(buttons);
					if (s.enoughNumbers()) {
						if (s.correctSyntax()) {
							solvedSudoku = s.solveSudoku(3);
							if (isSolved()) {
								fillWithSolvedNumbers (buttons, solvedSudoku);
								infobox.setText("Sudoku solved!");
							} else {
								s.solvingAlgorithm();
								solvedSudoku = s.getSudoku();
								fillWithSolvedNumbers (buttons, solvedSudoku);
								infobox.setText("Sudoku is not solvable in 40 seconds!");
							}
							solve.setText("RESET");
							solve.setForeground(Color.YELLOW);
						} else {
							infobox.setText("Incorrect Syntax!");
						}
					} else {
						infobox.setText("Mininum 17 numbers are required!");
					}
				}
			}
		});
	}
	private void fillWithSolvedNumbers (JButton [][] b, ArrayList <Integer> [][] num) {
		isSolved = true;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (num [x][y].size() == 1 && b [x][y].getText().equals("")) {
					b [x][y].setForeground(new Color(0, 128, 0));
					b [x][y].setText("" + num [x][y].get(0));
				} else if (b [x][y].getText().equals("")) {
					b [x][y].setForeground(Color.RED);
					b [x][y].setText("?");
				}
			}
		}
	}
	private boolean isSolved () {
		boolean solved = true;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (solvedSudoku [x][y].size() > 1) {
					solved = false;
					break;
				}
			}
			if (!solved) {break;}
		}
		return solved;
	}
	private JPanel [] getLines () {
		JPanel [] p = new JPanel [24];
		for (int x = 0; x < 4; x++) {
			p[x] = new JPanel();
			p[x].setBounds(4 + (x * 186), 4, 4, 562);
			p[x].setBackground(Color.BLACK);
		}
		for (int x = 4; x < 14; x++) {
			p[x] = new JPanel();
			p[x].setBounds(4, 4 + ((x - 4) * 62), 562, 4);
			if ((x - 4) % 3 == 0) {
				p[x].setBackground(Color.BLACK);
			} else {
				p[x].setBackground(Color.LIGHT_GRAY);
			}
		}
		for (int x = 14; x < 24; x++) {
			p[x] = new JPanel();
			p[x].setBounds(4 + ((x - 14) * 62), 4, 4, 562);
			if ((x-14) % 3 == 0) {
				p[x].setBackground(Color.BLACK);
			} else {
				p[x].setBackground(Color.LIGHT_GRAY);
			}
		}
		return p;
	}
	private JPanel [] getLines2 () {
		JPanel [] p = new JPanel [9];
		for (int x = 0; x < 2; x++) {
			p[x] = new JPanel();
			p[x].setBounds(687 + (x * 186), 110, 4, 252);
			p[x].setBackground(Color.BLACK);
		}
		for (int x = 2; x < 4; x++) {
			p[x] = new JPanel();
			p[x].setBounds(687, 110 + ((x - 2) * 248), 190, 4);
			p[x].setBackground(Color.BLACK);
		}
		for (int x = 4; x < 7; x++) {
			p[x] = new JPanel();
			p[x].setBounds(687, 172 + ((x - 4) * 62), 190, 4);
			p[x].setBackground(Color.BLACK);
		}
		p[7] = new JPanel();
		p[7].setBounds(749, 110, 4, 252);
		p[7].setBackground(Color.BLACK);

		p[8] = new JPanel();
		p[8].setBounds(811, 110, 4, 190);
		p[8].setBackground(Color.BLACK);
		return p;
	}
	private JButton [][] getDefaultButtons () {
		JButton [][] b = new JButton [9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				b [x][y] = new JButton("");
				b [x][y].setName("b" + x + y);
				b [x][y].setForeground(Color.BLACK);
				b [x][y].setFont(new Font("Arial", Font.BOLD, 26));
				b [x][y].setFocusable(false);
				b [x][y].setFocusTraversalKeysEnabled(false);
				b [x][y].setFocusPainted(false);
				b [x][y].setBorder(null);
				b [x][y].setBackground(Color.WHITE);
				b [x][y].setBounds(8 + (y * 62), 8 + (x * 62), 58, 58);
			}
		}
		return b;
	}
	private JButton [][] getDefaultButtons2 () {
		JButton [][] b = new JButton [3][3];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				b [x][y] = new JButton("" + ((3 * x) + y + 1));
				b [x][y].setName("panelButton" + ((3 * x) + y + 1));
				b [x][y].setForeground(Color.BLACK);
				b [x][y].setFont(new Font("Arial", Font.BOLD, 26));
				b [x][y].setFocusable(false);
				b [x][y].setFocusTraversalKeysEnabled(false);
				b [x][y].setFocusPainted(false);
				b [x][y].setBorder(null);
				b [x][y].setBackground(Color.WHITE);
				b [x][y].setBounds(691 + (y * 62), 114 + (x * 62), 58, 58);
			}
		}
		return b;
	}
	private void resetButtons (JButton [][] b) {
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				b [x][y].setText("");
				b [x][y].setForeground(Color.BLACK);
			}
		}
	}
	private void setSudoku (JButton [][] b) {
		int [][] raw = new int [9][9];
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				if (!b[x][y].getText().equals("")) {
					raw [x][y] = Integer.parseInt(b[x][y].getText());
				} else {
					raw [x][y] = 0;
				}
			}
		}
		s = new Sudoku(raw);
	}
}