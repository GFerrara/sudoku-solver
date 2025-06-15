package it.giorgioferrara.games.sudoku;

/**
 * <p>This is the main class used for Sudoku solving. Creating a new instance
 * defines the initial state of the game. The <code>solve</code> method 
 * searches for the solution.</p>
 * 
 * @author Giorgio Ferrara
 */
public class Sudoku {
    public static final short DIMENSION = 9;
    public static final short ONE_THIRD_DIMENSION = DIMENSION / 3;
    
    public static final short NULL_VALUE = 0;
    
    private final short[][] cells;
    private final Validator validator;
    private final BlankCell[] blankCells;
    
    private boolean hasBlankCells;
    private int nGuesses;
    

    /**
     * <p>Defines a new schema by passing its rows definition as
     * an array of string. Each string is structured so that the n-th
     * cell of current row will have a value equal to the n-th character
     * of the string.
     * 
     * For example, given a string = "1 45   6", the first element would be
     * 1, the second would be left empty, then 4, then 5 and so on. </p>
     * 
     * <p>The passed array must be non-null. Its length does not need to be
     * equal to <code>DIMENSION</code>: if smaller, only the specified
     * rows will be considered and the remaining ones will be left
     * empty. Conversely, if larger, only the first <code>DIMENSION</code>
     * elements will be used.</p>
     * 
     * <p>Each string in the array must be non-null and its length does not
     * need to match <code>DIMENSION</code>. In all cases, only the first
     * <code>DIMENSION</code> characters of each string, if present, 
     * will be considered.
     * 
     * For string shorter than <code>DIMENSION</code>, only
     * the available characters will be used and the remaining cells 
     * will be  left empty.</p>
     * 
     * @param rowsAsString the sudoku schema as array of strings
     */
    public Sudoku(String[] rowsAsString) {
        this.cells = new short[DIMENSION][DIMENSION];
        
        // cells init
        char c;
        short nBlankCells = 0;
        for (short i=0; i<DIMENSION; i++) {
            for (short j=0; j<DIMENSION; j++) {
                if (rowsAsString.length > i && rowsAsString[i].length() > j && (c = rowsAsString[i].charAt(j)) != ' ') {
                   cells[i][j] = (short) (c - '0');
                } else {
                    cells[i][j] = NULL_VALUE;
                    nBlankCells++;
                }
            }
        }
        
        this.validator = new Validator(cells);
        
        this.blankCells = new BlankCell[nBlankCells];
        this.hasBlankCells = nBlankCells > 0;
        this.nGuesses = 0;
        
        if (!hasBlankCells) {
            // Already solved
            return;
        }
        
        short iBlankCell = 0;
        for (short i=0; i<DIMENSION; i++) {
            for (short j=0; j<DIMENSION; j++) {
                if (cells[i][j] == NULL_VALUE) {
                    blankCells[iBlankCell] = new BlankCell(i, j);
                    for (short k=1; k<=DIMENSION; k++) {
                        cells[i][j] = k;
                        blankCells[iBlankCell].getIsApplicable()[k-1] = validator.isCellValueAdmissible(i,j);
                    }
                    cells[i][j] = NULL_VALUE;
                    iBlankCell++;
                }
            }
        }
    }
    
    /**
     * <p>A one-shot method to be called after class instantiation 
     * to solve the currently defined sudoku.</p>
     * 
     * @return true if a solution has been found; false otherwise.
     */
    public boolean solve() {
        if (!hasBlankCells) {
            // Already solved
            return true;
        }
        
        // To be solved
        for (short i=0; i<blankCells.length;) {
            short row = blankCells[i].getRow();
            short column = blankCells[i].getColumn();
            short currentValue = cells[row][column];
            short nextValue = NULL_VALUE;
            for (short j=currentValue; j<blankCells[i].getIsApplicable().length; j++) {
                if (blankCells[i].getIsApplicable()[j]) {
                    nextValue = (short)(j+1);
                    break;
                }
            }
            cells[row][column] = nextValue;
            nGuesses++;
            
            if (nextValue == NULL_VALUE) {
                if (i == 0) {
                    return false;
                } 

                i--;
                continue;
            } 
                
            if (validator.isCellValueAdmissible(row, column)) {
                i++;
            }
        }
        hasBlankCells = false;
        return true;
    }
    
    /**
     * <p>Returns a DIMENSION-by-DIMENSION matrix representing the current
     * sudoku state: if sudoku has been solved, it represents the solution;
     * if it is unsolvable, it shows the last checked combination of cells;
     * before solving, it reflects initial sudoku state.</p>
     *      
     * @return a DIMENSION-by-DIMENSION matrix representing the current
     * sudoku state.
     */
    public short[][] getCells() {
        return cells.clone();
    }
    
    /**
     * <p>Given that a guess here corresponds to a value assigned to a 
     * blank cell, this method returns the number of guesses made.
     * The higher the number, the more difficult the Sudoku is.</p>
     * 
     * @return the number of guesses made.
     */
    public int getnGuesses() {
        return nGuesses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sudoku{");
        sb.append("cells=");
        for (short i=0; i<DIMENSION; i++) {
            sb.append("\n").append(" ".repeat(6));
            for (short j=0; j<DIMENSION; j++) {
                sb.append((cells[i][j] == NULL_VALUE) ? " " : cells[i][j]);
            }
        }
        sb.append("\n");
        sb.append(", nGuesses=").append(nGuesses);
        sb.append('}');
        return sb.toString();
    }


    BlankCell[] getBlankCells() {
        return blankCells;
    }
}