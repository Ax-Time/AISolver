package csp.sudoku;

import csp.base_classes.Assignment;

import java.util.stream.Collectors;

public class SudokuAssignment extends Assignment<SudokuVariable, Integer> {

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                int finalI = i;
                int finalJ = j;
                if(
                        variableValueMap.keySet().stream()
                                .filter(var -> var.getRow() == finalI && var.getCol() == finalJ)
                                .collect(Collectors.toList())
                                .size() == 0
                ) s += "_  ";
                else{
                    s += variableValueMap.get(
                            variableValueMap.keySet().stream()
                            .filter(var -> var.getRow() == finalI && var.getCol() == finalJ)
                            .collect(Collectors.toList())
                            .get(0)
                    ).toString() + "  ";
                }
            }
            s += "\n";
        }
        return s;
    }
}
