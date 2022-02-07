package csp.sudoku;

import csp.base_classes.CSP;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class SudokuCSP extends CSP<SudokuVariable, Integer> {
    public SudokuCSP(String filePath) throws FileNotFoundException {
        super();

        // Generate Sudoku Variables from file
        /**
         * File input format:
         * 9.7......
         * ...83....
         * .....6...
         * 89....64.
         * ..3.9.7..
         * ..254...8
         * ....7....
         * 42.9.3.5.
         * 3..4...9.
         */
        Scanner in = new Scanner(new FileReader(filePath));
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()){
            sb.append(in.next());
        }
        in.close();
        int index = 0;
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++, index++){
                char currChar = sb.charAt(9 * i + j);
                String varName = "";
                varName += (char)((int)('A') + i);
                varName += (char)((int)'1' + j);
                SudokuVariable newVar = new SudokuVariable(varName, i, j);
                variables.add(newVar);
                if(currChar != '.'){
                    // Reduce the domain of the variable to only one element: the one that has been preset
                    int value = ((int)(currChar)) - ((int)('0'));
                    newVar.setDomain(new HashSet<>(Arrays.asList(value)));
                }
            }
        }

        // Generate constraints
        // Row constraints
        for(int row = 0; row < 9; row++){
            for(int col1 = 0; col1 < 9; col1++){
                for(int col2 = col1 + 1; col2 < 9; col2++){
                    int finalCol1 = col1;
                    int finalRow = row;
                    int finalCol2 = col2;
                    constraints.add(new SudokuConstraint(
                            variables.stream()
                                    .filter(var -> var.getRow() == finalRow && var.getCol() == finalCol1)
                                    .collect(Collectors.toList())
                                    .get(0),
                            variables.stream()
                                    .filter(var -> var.getRow() == finalRow && var.getCol() == finalCol2)
                                    .collect(Collectors.toList())
                                    .get(0)
                    ));
                }
            }
        }

        // Column constraints
        for(int col = 0; col < 9; col++){
            for(int row1 = 0; row1 < 9; row1++){
                for(int row2 = row1 + 1; row2 < 9; row2++){
                    int finalCol = col;
                    int finalRow1 = row1;
                    int finalRow2 = row2;
                    constraints.add(new SudokuConstraint(
                            variables.stream()
                                    .filter(var -> var.getRow() == finalRow1 && var.getCol() == finalCol)
                                    .collect(Collectors.toList())
                                    .get(0),
                            variables.stream()
                                    .filter(var -> var.getRow() == finalRow2 && var.getCol() == finalCol)
                                    .collect(Collectors.toList())
                                    .get(0)
                    ));
                }
            }
        }

        // Quadrant constraints (don't duplicate constraints for the row and the columns
        for(int q_row = 0; q_row < 3; q_row++){
            for(int q_col = 0; q_col < 3; q_col++){
                List<SudokuVariable> quadrantVariables = new ArrayList<>(8);
                for(int row = 3*q_row; row < 3*(q_row+1); row++){
                    for(int col = 3*q_col; col < 3*(q_col+1); col++){
                        int finalRow = row;
                        int finalCol = col;
                        quadrantVariables.add(
                                variables.stream()
                                        .filter(var -> var.getRow() == finalRow && var.getCol() == finalCol)
                                        .collect(Collectors.toList())
                                        .get(0)
                        );
                    }
                }
                // Create the constraints
                for(int i = 0; i < quadrantVariables.size(); i++){
                    for(int j = i + 1; j < quadrantVariables.size(); j++){
                        if( // Different row and different column (if row or col is the same the constraint has already been created)
                                quadrantVariables.get(i).getRow() != quadrantVariables.get(j).getRow() &&
                                quadrantVariables.get(i).getCol() != quadrantVariables.get(j).getCol()
                        )
                        constraints.add(new SudokuConstraint(quadrantVariables.get(i), quadrantVariables.get(j)));
                    }
                }
            }
        }
    }


}
