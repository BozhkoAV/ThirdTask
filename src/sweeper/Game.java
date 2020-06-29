package sweeper;

public class Game {

    private Bomb bomb; // содержит кол-во мин и состояния клеток на поле: бомбы, численные значения в клетках рядом
    private Flag flag; // содержит кол-во закрытых клеток и состояния клеток на поле: флаги, открытые и закрытые клетки
    private GameState state; // содержит текущее состояние игры

    // возваращает текущее состояние игры
    public GameState getState() { return state; }

    // конструктор, создаёт игру с заданным числом столбцов, рядов и бомб
    public Game (int cols, int rows, int bombs) {
        // задаём размер поля, который будет использоваться для создания дальнейших полей
        BoardOrganization.setSize(new Pair(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    // задаёт стартовые значения в начале игры
    public void start () {
        bomb.start();
        flag.start();
        state = GameState.PLAYED;
    }

    // возвращает состояние конкретной клетки поля,
    // если в поле флагов клетка имеет значение opened значит под ней мина
    // получается слой флагов - верхний, слой мин - нижний
    public Cell getCell (Pair coordinate) {
        if (flag.get(coordinate) == Cell.opened) {
            return bomb.get(coordinate);
        } else {
            return flag.get(coordinate);
        }
    }

    // открывает конкретную клетку, выставляя в неё нужное состояние
    private void openCell(Pair coordinate) {
        switch (flag.get(coordinate)) {
            case flaged : return;
            case closed : switch (bomb.get(coordinate)) {
                case zero: openCellsAround (coordinate); return;
                case bomb: openBombs (coordinate); return;
                default: flag.setOpenedToCell(coordinate);
            }
        }
    }

    // проверяет на наличие победного состояния в игре
    private void checkWinner () {
        if ((state == GameState.PLAYED) && (flag.getCountClosedCells() == bomb.getTotalBombs())) {
            state = GameState.WINNER;
        }
    }

    // обрабатывает нажатие на левую кнопку, открывает клетку
    public void pressLeftButton (Pair coordinate) {
        if (gameOver ()) return;
        openCell (coordinate);
        checkWinner();
    }

    // открывает все мины в случае нажатия на мину, завершает игру
    private void openBombs(Pair bombed) {
        state = GameState.BOMBED;
        flag.setBombedToCell(bombed);
        for (Pair coordinate : BoardOrganization.getAllCoordinates())
            if (bomb.get(coordinate) == Cell.bomb) {
                flag.setOpenedToClosedBombCell(coordinate);
            } else {
                flag.setNobombToFlagedCell(coordinate);
            }
    }

    // открывает клетки вокруг открываемой
    private void openCellsAround(Pair coordinate) {
        flag.setOpenedToCell(coordinate);
        for (Pair around : BoardOrganization.getCoordinatesAround(coordinate))
            openCell(around);
    }

    // обрабатывает нажатие на правую кнопку, ставит флаг
    public void pressRightButton(Pair coordinate) {
        if (gameOver ()) return;
        flag.setFlagedToCell (coordinate);
    }

    // проверка на окончание игры
    private boolean gameOver() { return state != GameState.PLAYED; }

}