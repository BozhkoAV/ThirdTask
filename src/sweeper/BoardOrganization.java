package sweeper;

import java.util.ArrayList;
import java.util.Random;

public class BoardOrganization {
    // все методы и поля в классе статические, чтобы к ним было можно обращаться, не создавая экземпляр

    private static Pair boardSize; // размер поля
    private static ArrayList<Pair> allCoordinates; // массив всех клеток поля
    private static Random random = new Random();

    // создаём массив клеток нужного размера
    public static void setSize (Pair size) {
        boardSize = size;
        allCoordinates = new ArrayList<>();
        for (int y = 0; y < boardSize.y; y++)
            for (int x = 0; x < boardSize.x; x++)
                allCoordinates.add(new Pair(x,y));
    }

    // возращаем размер поля
    public static Pair getSize() { return boardSize; }

    // возвращаем массив клеток
    public static ArrayList<Pair> getAllCoordinates () { return allCoordinates; }

    // проверяем попадает ли клетка в диапазон клеток поля
    static boolean isBelongBoard (Pair coordinate) {
        return coordinate.x >= 0 && coordinate.x < boardSize.x && coordinate.y >= 0 && coordinate.y < boardSize.y;
    }

    // возвращаем случайную клетку
    static Pair getRandomCoordinate () { return new Pair(random.nextInt(boardSize.x), random.nextInt(boardSize.y)); }

    // получаем для конкретной клетки массив координат вокруг
    static ArrayList<Pair> getCoordinatesAround (Pair coordinate) {
        Pair around;
        ArrayList<Pair> list = new ArrayList<>();
        int x = coordinate.x;
        for (int y = coordinate.y - 2; y <= coordinate.y + 2; y++) {
            if ((isBelongBoard(around = new Pair(x, y))) && (!around.equals(coordinate))) {
                list.add(around);
            }
        }
        x = coordinate.y % 2 == 0 ? x - 1 : x + 1;
        for (int y = coordinate.y - 1; y <= coordinate.y + 1; y += 2) {
            if (isBelongBoard(around = new Pair(x, y))) {
                list.add(around);
            }
        }
        return list;
    }
}
