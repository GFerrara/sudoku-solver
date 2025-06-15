package it.giorgioferrara.games.sudoku;

import static it.giorgioferrara.games.sudoku.Sudoku.DIMENSION;
import java.io.Serializable;

/**
 * <p>A bean representing a blank cell: it stores its position in the grid
 * (row and column) along with the list of assignable values.</p>
 * 
 * @author Giorgio Ferrara
 */
public class BlankCell implements Serializable {
    private static final long serialVersionUID = 8187146437156217575L;

    private final short row;
    private final short column;
    private final boolean[] isApplicable;  // A boolean array of size DIMENSION,
                                           // where true at index i means that 
                                           // i+1 can be assigned to the cell.

    BlankCell(short row, short column) {
        this.row = row;
        this.column = column;
        this.isApplicable = new boolean[DIMENSION];
    }

    short getRow() {
        return row;
    }

    short getColumn() {
        return column;
    }

    boolean[] getIsApplicable() {
        return isApplicable;
    }
}