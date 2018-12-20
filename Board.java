package com.company;

import java.util.Set;
import java.util.HashSet;


class Field {
    private int[][] prisoners; //   Само поле. Пустая клетка обозначается нулем
    private int N; //  эвристика. Количество цифр, находящихся не на своем месте
    private int XZeroCoord;    // Координаты нуля
    private int YZeroCoord;


    public Field(int[][] prisoners) {
        int[][] prisoners2 = deepCopy(prisoners);   //   делаем копию доски
        this.prisoners = prisoners2;

        for (int i = 0; i < 3; i++) {  //  тут ищем координаты нуля и определяем N
            for (int j = 0; j < 3; j++) {
                if (prisoners[i][j] == 0) {
                XZeroCoord =i;
                YZeroCoord =j;
            }
                if (prisoners[i][j] != (i*3 + j + 1) && prisoners[i][j] != 0) {  // если 0 не на своем месте - не считается.
                    N++;
                }

            }
        }
    }


    public boolean isGoal() {  //   если все на своем месте, значит это искомая позиция
        return N == 0;
    }
    public int N() {
        return N;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field board = (Field) o;


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (prisoners[i][j] != board.prisoners[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Field> neighbors() {  //ищем все соседние позиции. двигаем ноль влево, вправо, вниз и вверх. Hashset нужен для того, чтобы не записывались null в случае, когда одного из соседей нет.
        Set<Field> boardList = new HashSet<Field>();
        boardList.add(Shuffler(getNewBlock(), XZeroCoord, YZeroCoord, XZeroCoord, YZeroCoord + 1));
        boardList.add(Shuffler(getNewBlock(), XZeroCoord, YZeroCoord, XZeroCoord, YZeroCoord - 1));
        boardList.add(Shuffler(getNewBlock(), XZeroCoord, YZeroCoord, XZeroCoord - 1, YZeroCoord));
        boardList.add(Shuffler(getNewBlock(), XZeroCoord, YZeroCoord, XZeroCoord + 1, YZeroCoord));

        return boardList;
    }

    private int[][] getNewBlock() { //  опять же, для неизменяемости
        return deepCopy(prisoners);
    }

    private Field Shuffler(int[][] blocks2, int x1, int y1, int x2, int y2) {  //  тут меняем местами ноль и соседнюю цифру

        if (x2 > -1 && x2 < 3 && y2 > -1 && y2 < 3) {
            int t = blocks2[x2][y2];
            blocks2[x2][y2] = blocks2[x1][y1];
            blocks2[x1][y1] = t;
            return new Field(blocks2);
        } else
            return null;

    }


    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < prisoners.length; i++) {
            for (int j = 0; j < prisoners.length; j++) {
                s.append(String.format("%d ", prisoners[i][j]));
            }

        }
        return s.toString();
    }

    private static int[][] deepCopy(int[][] original) {
        if (original == null) {
            return null;
        }

        final int[][] result = new int[3][];
        for (int i = 0; i < 3; i++) {
            result[i] = new int[3];
            for (int j = 0; j < 3; j++) {
                result[i][j] = original[i][j];
            }
        }
        return result;
    }
}
