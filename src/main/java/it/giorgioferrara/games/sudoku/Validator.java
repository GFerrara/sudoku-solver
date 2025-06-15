package it.giorgioferrara.games.sudoku;

import static it.giorgioferrara.games.sudoku.Sudoku.DIMENSION;
import static it.giorgioferrara.games.sudoku.Sudoku.ONE_THIRD_DIMENSION;
import static it.giorgioferrara.games.sudoku.Sudoku.NULL_VALUE;

/**
 * <p>This class performs validations on a sudoku grid.</p>
 * 
 * @author Giorgio Ferrara
 */
public class Validator {
    private final short[][] cells;

    Validator(short[][] cells) {
        this.cells = cells;
    }
    
    /**
     * <p>Checks whether the value assigned to a given sudoku cell is valid.
     * </p>
     * 
     * @param row the cell's row
     * @param column the cell's column
     * @return true if the cell's value is valid; false otherwise.
     */
    boolean isCellValueAdmissible(short row, short column) {
        // Check row
        if (!isValidCombination(row, (short)(row+1), (short)0, DIMENSION))
            return false;
        
        // Check column
        if (!isValidCombination((short)0, DIMENSION, column, (short)(column+1)))
            return false;
        
        // Check quadrant
        short x = (short) ((column/ONE_THIRD_DIMENSION)*ONE_THIRD_DIMENSION);
        short y = (short) ((row/ONE_THIRD_DIMENSION)*ONE_THIRD_DIMENSION);
        if (!isValidCombination(y, (short)(y+ONE_THIRD_DIMENSION), x, (short)(x+ONE_THIRD_DIMENSION)))
            return false;
        
        return true;
    }
    
    private boolean isValidCombination(short rowMin, short rowMax, short colMin, short colMax) {
        boolean[] isUsed = new boolean[DIMENSION];
        short value;
        
        for (short i=rowMin; i<rowMax; i++) {
            for (short j=colMin; j<colMax; j++) {
                if ((value = cells[i][j]) != NULL_VALUE) {
                    value--;
                    if (isUsed[value]) 
                        return false;
                    else 
                        isUsed[value] = true;
                }
            }
        }
        return true;
    }
}
