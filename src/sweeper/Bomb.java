package sweeper;

public class Bomb {

    private Board bombMap; // запоминает состояние клеток
    private int totalBombs; // общее число мин

    // конструктор класса, принимающий на вход кол-во мин на поле (регулирует это значение)
    Bomb (int totalBombs) {
        this.totalBombs = totalBombs;
        int maxBombs = BoardOrganization.getSize().x * BoardOrganization.getSize().y;
        if (this.totalBombs > maxBombs)
            this.totalBombs = maxBombs;
    }

    // возвращает число мин
    int getTotalBombs()
    {
        return totalBombs;
    }

    // возвращаяет соостояние конкретной клетки поля
    Cell get (Pair coordinate)
    {
        return bombMap.get(coordinate);
    }

    // ставит мину в случайную клетку
    private void placeBomb () {
        Pair coordinate = BoardOrganization.getRandomCoordinate();
        while (Cell.bomb == bombMap.get(coordinate)) {
            coordinate = BoardOrganization.getRandomCoordinate();
        }
        bombMap.set(new Pair(coordinate.x, coordinate.y), Cell.bomb);
        getNumbersAroundBomb(coordinate);
    }

    // расставляет мины по полю в начале игры
    void start() {
        bombMap = new Board(Cell.zero);
        for (int j = 0; j < totalBombs; j++)
            placeBomb ();
    }

    // увеличивает значение в клетке, являющейся соседней для данной клетки с миной, на один, если в этой клетке не мина
    private void getNumbersAroundBomb (Pair coordinate) {
        for (Pair around : BoardOrganization.getCoordinatesAround(coordinate))
            if (Cell.bomb != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).setNextNumberCell());
    }
}
