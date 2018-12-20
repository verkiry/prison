package com.company;

public class Main {

    public static void main(String[] args) {
        int[][] prisoners = new int[][]{{7, 5, 6}, {8, 2, 1}, {4, 3, 0}}; //условие задачи, пустое поле обозначить нулем
        Field initial = new Field(prisoners); //создаем доску
        Solver solver = new Solver(initial);
        System.out.println("количество шагов =  " + solver.moves());
        for (Field board : solver.solution()) {

            System.out.println(board);


        }
    }
}
