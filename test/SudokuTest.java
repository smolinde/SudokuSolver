import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuTest {

    @Test
    void enoughNumbers() {
        // Create an empty Sudoku with all zeroes
        int [][] nums = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nums [i][j] = 0;
            }
        }
        // Instantiate Sudoku class
        Sudoku s = new Sudoku(nums);
        // At least 17 numbers are expected. Shall return false to pass test.
        assertFalse(s.enoughNumbers());
    }

    @Test
    void correctSyntax() {
        // Create an empty Sudoku with all zeroes
        int [][] nums = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nums [i][j] = 0;
            }
        }
        // Add 2x a 9 in same column
        nums[0][0] = 9;
        nums[8][0] = 9;
        // Instantiate Sudoku class
        Sudoku s = new Sudoku(nums);
        // Shall return false to pass the test as there cannot be two of the same numbers in a Sudoku.
        assertFalse(s.correctSyntax());
    }

    @Test
    void checkRow() {
        // This is a method used by correctSyntax, we can check if this one works as expected too.
        // Create an empty Sudoku with all zeroes
        int [][] nums = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                nums [i][j] = 0;
            }
        }
        // Add 2x a 9 in same row
        nums[0][0] = 9;
        nums[0][8] = 9;
        // Instantiate Sudoku class
        Sudoku s = new Sudoku(nums);
        // Shall return true if duplicate number in a row is detected
        assertTrue(s.checkRow(0,0));
    }
}