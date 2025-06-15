package it.giorgioferrara.games.sudoku.cli;

import it.giorgioferrara.games.sudoku.Sudoku;

/**
 * <p>This class provides a way to run the sudoku solver from the command 
 * line.</p>
 * 
 * @author Giorgio Ferrara
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || args[0].length() != Sudoku.DIMENSION * Sudoku.DIMENSION) {
            showInfo();
            return;
        }
        Sudoku sudoku = new Sudoku(toStringArray(args[0]));
        boolean solved = sudoku.solve();
        if (solved) {
            out("Puzzle solved");
            out(sudoku.toString());
        } else {
            out("Invalid puzzle");
        }
    }
    
    private static String[] toStringArray(String string) {
        var out = new String[Sudoku.DIMENSION];
        for (int i=0; i<out.length; i++) {
            out[i] = string.substring(i*9, (i+1)*9);
        }
        return out;
    }
    
    private static void showInfo() {
        out("sudoku-solver by Giorgio Ferrara");
        out("- usage is: java -jar <sudoku-solver jar> \"<one-line string of 81 chars representing the sudoku puzzle>\"" );
        out("  i.e. java -jar sudoku-solver-1.0.jar \"53  7    6  195    98    6 8   6   34  8 3  17   2   6 6    28    419  5    8  79\"");
    }
    
    private static void out(String string) {
        System.out.println(string);
    }
    
    private Main() {}
}
