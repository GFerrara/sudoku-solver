package it.giorgioferrara.games.sudoku;

import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import java.util.Arrays;

public class SudokuTester {
    
    @Test
    void blankCellsCount() {
        // Given
        var sudokuRows = new String[] {
            "53  7    ",
            "6  195   ",
            " 98    6 ",
            "8   6   3",
            "4  8 3  1",
            "7   2   6",
            " 6    28 ",
            "   419  5",
            "    8  79"
        };
        var sudokuTrimmedRows = Arrays.stream(sudokuRows).map(String::trim).toArray(String[]::new);
        // When
        for (var rows : new String[][] {sudokuRows, sudokuTrimmedRows}) {
            Sudoku sudoku = new Sudoku(rows);
            // Then
            Assertions.assertThat(sudoku.getBlankCells())
                .isNotNull()
                .hasSize(Arrays.stream(sudokuRows).mapToInt(s -> s.replaceAll("[^\\s]", "").length()).sum())
            ;
        }
    }
    
    @Test
    void sudokuWithSolution() {
        // Given
        var sudokuRows = new String[] {
            "     4  3",
            " 71 9 4",
            "3  7  9 6",
            "  517  6",
            "1 64 3 9",
            "  96 2 35",
            "        7",
            "",
            "6      4"
        };
        // When
        Sudoku sudoku = new Sudoku(sudokuRows);
        int nGuesses = 0;
        for (int i=0; i<10; i++) {
            boolean isSolved = sudoku.solve();
            // Then
            Assertions.assertThat(isSolved)
                .isTrue()
            ;
            switch (i) {
            case 0 -> Assertions.assertThat(nGuesses = sudoku.getnGuesses())
                    .isGreaterThan(0)
                ;
            default -> Assertions.assertThat(sudoku.getnGuesses())
                    .isEqualTo(nGuesses)
                ;
            }
            Assertions.assertThat(sudoku.getCells())
                .isEqualTo(new short[][] {
                    {9,6,2,8,1,4,5,7,3}
                  , {5,7,1,3,9,6,4,8,2}
                  , {3,8,4,7,2,5,9,1,6}
                  , {8,3,5,1,7,9,2,6,4}
                  , {1,2,6,4,5,3,7,9,8}
                  , {7,4,9,6,8,2,1,3,5}
                  , {2,1,3,9,4,8,6,5,7}
                  , {4,9,8,5,6,7,3,2,1}
                  , {6,5,7,2,3,1,8,4,9}
                })
            ;
        }
    }
    
    @Test
    void sudokuWithoutSolution() {
        // Given
        var sudokuRows = new String[] {
            "  6 71 3",
            "    4  7",
            "    567",
            " 1   4 5",
            " 8  3",
            "7  5  6",
            " 68 2",
            " 429    7",
            " 97  35"
        };
        // When
        Sudoku sudoku = new Sudoku(sudokuRows);
        // Then
        Assertions.assertThat(sudoku.solve()).isFalse();
        Assertions.assertThat(sudoku.getnGuesses()).isGreaterThan(0);
    }
}
