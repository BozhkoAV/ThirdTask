import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import sweeper.*;

public class HexagonalMineSweeper extends JFrame {

    private Game game; // создаёт экземпляр класса Игра, через который осуществляется управление игрой
    private JPanel panel; // контейнер для размещения компонентов
    private JLabel label;
    private JButton button;

    private int COLS = 7; // кол-во столбцов
    private int ROWS = 13; // кол-во рядов

    public void setCOLS(int COLS) { this.COLS = COLS; }
    public void setROWS(int ROWS) { this.ROWS = ROWS; }

    HexagonalMineSweeper() { this(10, 10, 10); }

    private final int width = 1000; // ширина окна
    private final int height = 500; // высота окна

    public static void main(String[] args) { SwingUtilities.invokeLater(HexagonalMineSweeper::new); }

    // запускает процессы при начале игры
    HexagonalMineSweeper (int cols, int rows, int bombs) {
        game = new Game(cols, rows, bombs);
        setCOLS(cols);
        setROWS(rows);
        game.start();
        setImages();
        initLabel();
        initButton();
        initPanel();
        initFrame();
    }

    private void initLabel () {
        label = new JLabel("Нажми на любую ячейку, чтобы начать игру");
        add (label, BorderLayout.SOUTH); // Отображается снизу
    }

    private void initButton () {
        button = new JButton("Заново");
        add(button, BorderLayout.NORTH); // Отображается сверху
    }

    // заполняет панель объектами и добаляем её в окно
    private void initPanel () {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) { // отрисовка компонента и его положение
                super.paintComponent(g);
                for (Pair coordinate : BoardOrganization.getAllCoordinates()) {
                    int x;
                    if (coordinate.y % 2 == 0) {
                        x = coordinate.x * 4 * width / (4 * COLS + 1);
                    } else {
                        x = (coordinate.x * 4 + 2) * width / (4 * COLS + 1);
                    }
                    g.drawImage((Image) game.getCell(coordinate).image, x, coordinate.y * height / (ROWS + 1),
                            3 * width / (4 * COLS + 1), 2 * height / (ROWS + 1), this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() { // слушатель определяет по координатам нажатия мыши
            @Override
            public void mousePressed(MouseEvent e) {
                int xZone = width / (4 * COLS + 1);
                int yZone = height / (ROWS + 1);
                int x = e.getX() / (4 * xZone);
                int y1 = e.getY();

                if ((e.getX() % (4 * xZone) > xZone) && (e.getX() % (4 * xZone) < 2 * xZone)) {
                    int y = 2 * (y1 / (2 * yZone));
                    if ((y1 > y * yZone) && (y1 < (y + 2) * yZone)) {
                        Pair coordinate = new Pair(x, y);
                        if (e.getButton() == MouseEvent.BUTTON1) // левая кнопка мыши
                            game.pressLeftButton(coordinate);
                        if (e.getButton() == MouseEvent.BUTTON3) // правая кнока мыши
                            game.pressRightButton(coordinate);
                    }
                }

                if (e.getX() % (4 * xZone) > 3 * xZone) {
                    int y = ((y1 + yZone) / (2 * yZone)) * 2 - 1;
                    y1 = y1 + yZone;
                    if ((y1 > (y / 2 + 1) * 2 * yZone) && (y1 < (y / 2 + 2) * 2 * yZone)) {
                        Pair coordinate = new Pair(x, y);
                        if (e.getButton() == MouseEvent.BUTTON1) // левая кнопка мыши
                            game.pressLeftButton(coordinate);
                        if (e.getButton() == MouseEvent.BUTTON3) // правая кнока мыши
                            game.pressRightButton(coordinate);
                    }
                }
                button.addActionListener(actionEvent -> {
                    game.start();
                    panel.repaint();
                });
                label.setText(getGameState()); // метка изменяется в соответствии с состоянием игры
                panel.repaint(); // после каждого нажатия на кнопки мыши перерисовываем панель игры
            }
        });

        //определяем размер панели
        panel.setPreferredSize(new Dimension(BoardOrganization.getSize().x * (width / COLS),
                BoardOrganization.getSize().y * (height / ROWS)));
        add (panel);
    }

    // присваивает значение метки в соответствии с состоянием игры
    private String getGameState() {
        switch (game.getState()) {
            case PLAYED: return "Еще есть неоткрытые клетки";
            case BOMBED: return "Поражение";
            case WINNER: return "Победа";
            default: return "";
        }
    }

    // формирует окно создаваемое при запуске игры
    private void initFrame () {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // завершает программу при закрытии окна
        setTitle("Hexagonal Mine Sweeper");
        setVisible(true); // делает окно видимым
        pack(); // устанавливает размер окна достаточный для отображения
    }

    // присваивает изображение каждой клетке исходя из её значения
    private void setImages () {
        for (Cell cell : Cell.values())
            cell.image = getImage(cell.name());
    }

    // возвращает изображение по его имени
    private Image getImage (String name) {
        String filename = "img/" + name + ".png";
        // создаёт объект "изображение", которое берёт из папки ресурсов
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}
