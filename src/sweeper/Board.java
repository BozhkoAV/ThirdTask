package sweeper;

public class Board {

    private Cell [] [] board; // поле - двумерный массив клеток

    // конструктор, создаём поле клеток со значением полученным на входе
    Board (Cell defaultCell) {
        board = new Cell [BoardOrganization.getSize().x] [BoardOrganization.getSize().y];
        for (Pair coordinate : BoardOrganization.getAllCoordinates())
            board [coordinate.x] [coordinate.y] = defaultCell;
    }

    // получаем значение конкреной клетки поля
    Cell get (Pair coordinate) {
        if (BoardOrganization.isBelongBoard (coordinate))
            return board [coordinate.x] [coordinate.y];
        return null;
    }

    // присваиваем значение конкретной клетке
    void set (Pair coordinate, Cell cell) {
        if (BoardOrganization.isBelongBoard (coordinate))
            board [coordinate.x] [coordinate.y] = cell;
    }

}
