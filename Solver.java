package com.company;

import java.util.*;

public class Solver {
    private Field initial;    //
    private List<Field> result = new ArrayList<Field>();   // сюда записываем цепочку ходов

    private class ITEM{    // Запоминаем предыдущие позиции
        private ITEM prevBoard;  // ссылка на предыдущую позицию
        private Field board;   // текущая позиция

        private ITEM(ITEM prevBoard, Field board) {
            this.prevBoard = prevBoard;
            this.board = board;
        }

        public Field getBoard() {
            return board;
        }


    }

    public Solver(Field initial) {
        this.initial = initial;

        //  Метод, позволяющий организовать очередь. Для определения позиции в очереди используем ценностную функцию: количество предыдущих шагов+эвристическая функция
        PriorityQueue<ITEM> priorityQueue = new PriorityQueue<ITEM>(10, new Comparator<ITEM>() {

            public int compare(ITEM o1, ITEM o2) {
                return new Integer(cost(o1)).compareTo(new Integer(cost(o2)));
            }
        });


        // Первый шаг
        priorityQueue.add(new ITEM(null, initial));

        while (true){
            ITEM board = priorityQueue.poll(); //  Второй шаг

            //   Если обнаружено решение, сохраняем всю последовательность ходов в пустой лист
            if(board.board.isGoal()) {
                itemToList(new ITEM(board, board.board));
                return;
            }

            //   шаг 3
            Iterator iterator = board.board.neighbors().iterator(); // Соседние позиции
            while (iterator.hasNext()){
                Field board1 = (Field) iterator.next();

                //Не учитываем соседнюю позицию, из которой только что пришли
                if(board1!= null && !alreadyInPath(board, board1))
                    priorityQueue.add(new ITEM(board, board1));
            }

        }
    }

    //  Вычисляем ценностную функцию
    private static int cost(ITEM item){
        ITEM item2 = item;
        int c= 0;   // g(x) - количество ранее сделанных ходов. Идем до конца списка, пока не получим null.
        int cost = item.getBoard().N();  // N(x)- количество цифр, находящихся не на своих местах
        while (true){
            c++;
            item2 = item2.prevBoard;
            if(item2 == null) {
                // g(x)+N(x)
                return cost + c;
            }
        }
    }

    //  сохранение
    private void itemToList(ITEM item){
        ITEM item2 = item;
        while (true){
            item2 = item2.prevBoard;
            if(item2 == null) {
                Collections.reverse(result);
                return;
            }
            result.add(item2.board);
        }
    }

    // Проверяем, была ли данная позиция в пути
    private boolean alreadyInPath(ITEM item, Field board){
        ITEM item2 = item;
        while (true){
            if(item2.board.equals(board)) return true;
            item2 = item2.prevBoard;
            if(item2 == null) return false;
        }
    }




    public int moves() {

        return result.size() - 1;
    }


    // Возвращаем результат
    public Iterable<Field> solution() {
        return result;
    }


}