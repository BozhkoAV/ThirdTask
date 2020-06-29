package sweeper;

public class Pair { // создал отдельный класс для пары двух целых чисел - используется для обозначения координат клеток
    public int x;
    public int y;

    // конструктор
    public Pair (int x, int y) {
        this.x = x;
        this.y = y;
    }

    // проверка на равенство
    @Override
    public boolean equals(Object other) {
        // проверка на принадлежность к классу
        if ((other.getClass() != null) && (other.getClass() == this.getClass())) {
            Pair otherPair = (Pair) other;
            return otherPair.x == x && otherPair.y == y;
        }
        return false;
    }
}
