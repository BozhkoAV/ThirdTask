package sweeper;

public enum Cell {
    //возможные состояния клеток
    zero,
    num1,
    num2,
    num3,
    num4,
    num5,
    num6,
    bomb,
    opened,
    closed,
    flaged,
    bombed,
    nobomb;

    public Object image; // к каждому состоянию клетки привязано изображение

    // используется в incNumbersAroundBomb, увеличивает значение в клетке при обнаружении мины по соседству
    Cell setNextNumberCell () { return Cell.values() [this.ordinal() + 1]; }

}