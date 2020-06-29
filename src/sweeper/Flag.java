package sweeper;

public class Flag {

    private Board flagMap; // запоминает состояние клеток
    private int countOfClosedCells; // общее число закрытых клеток

    // закрывает все клетки на поле в начале игры
    void start () {
        flagMap = new Board(Cell.closed);
        countOfClosedCells = BoardOrganization.getSize().x * BoardOrganization.getSize().y;
    }

    // получаем значение конкреной клетки поля
    Cell get (Pair coordinate)
    {
        return flagMap.get(coordinate);
    }

    // открывает конкретную клетку поля
    public void setOpenedToCell(Pair coordinate) {
        flagMap.set(coordinate, Cell.opened);
        countOfClosedCells--;
    }

    // закрывает конкретную клетку поля
    private void setClosedToCell(Pair coordinate)
    {
        flagMap.set(coordinate, Cell.closed);
    }

    // ставит флаг в конкретную клетку поля
    public void setFlagedToBomb(Pair coordinate)
    {
        flagMap.set(coordinate, Cell.flaged);
    }

    // ставит флаг в конкретную клетку поля, если уже стоял флаг - убирает его
    void setFlagedToCell (Pair coordinate) {
        switch (flagMap.get(coordinate)) {
            case flaged : setClosedToCell (coordinate); break;
            case closed : setFlagedToBomb(coordinate); break;
        }
    }

    // возвращает количество закрытых клеток
    int getCountClosedCells() { return countOfClosedCells; }

    // отрисовывает взорванную мину в клетке
    void setBombedToCell(Pair coordinate)
    {
        flagMap.set(coordinate, Cell.bombed);
    }

    // открывает закрытую клетку
    void setOpenedToClosedBombCell(Pair coordinate) {
        if (flagMap.get(coordinate) == Cell.closed)
            flagMap.set(coordinate, Cell.opened);
    }

    // если игра проиграна, в клетке на которой стоял флаг, но мины не было, отрисует перечёркнутую мину
    void setNobombToFlagedCell(Pair coordinate) {
        if (flagMap.get(coordinate) == Cell.flaged)
            flagMap.set(coordinate, Cell.nobomb);
    }
}
