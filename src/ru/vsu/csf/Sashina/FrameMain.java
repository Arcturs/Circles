package ru.vsu.csf.Sashina;

import ru.vsu.csf.Sashina.Logic.*;
import utils.util.JTableUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

public class FrameMain extends JFrame {

    private JPanel panelMain;
    private JTable gameField;
    private JLabel Score;
    private JLabel scoreValue;
    private JLabel bestScoreValue;
    private JLabel bestScore;
    private JButton buttonNewGame;

    private Logic logic;

    private final int DEFAULT_CELL_SIZE = 90;

    private Cell a = null;
    private Cell b = null;

    public FrameMain() {
        this.setTitle("Circle");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        gameField.setRowHeight(DEFAULT_CELL_SIZE);
        JTableUtils.initJTableForArray(gameField, DEFAULT_CELL_SIZE, false, false,
                false, false);
        gameField.setIntercellSpacing(new Dimension(0, 0));
        gameField.setEnabled(false);

        bestScoreValue.setText(String.valueOf(BestScore.readScore()));

        newGame();

        gameField.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final class DrawComponent extends Component {
                private int row = 0, column = 0;

                @Override
                public void paint(Graphics gr) {
                    Graphics2D g2d = (Graphics2D) gr;
                    int width = getWidth() - 2;
                    int height = getHeight() - 2;
                    Cell cell = logic.getCell(row, column);
                    paintCell(cell, g2d, width, height);
                }
            }

            DrawComponent comp = new DrawComponent();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                comp.row = row;
                comp.column = column;
                return comp;
            }
        });

        updateView();

        buttonNewGame.addActionListener(e -> {
            newGame();
        });

        gameField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = gameField.rowAtPoint(e.getPoint());
                int col = gameField.columnAtPoint(e.getPoint());
                if ((logic.getCell(row, col).isClosed()) &&
                        (logic.getCell(row, col).getColour() != Colours.disappeared)) {
                    if (a == null) {
                        a = logic.operateWithCell(row, col);
                    } else {
                        b = logic.operateWithCell(row, col);
                    }
                    updateView();
                }
            }

            public void mouseReleased (MouseEvent e) {
                if ((a != null) && (b != null)) {
                    logic.compareCircles(a, b);
                    logic.checkGameState();
                    a = null;
                    b = null;
                    try {
                        TimeUnit.MILLISECONDS.sleep(90);
                        updateView();
                    } catch (InterruptedException interruptedException) {
                        return;
                    }
                }
            }
        });
    }

    private void paintCell(Cell cell, Graphics2D g2d, int cellWidth, int cellHeight) {

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int size = Math.min(cellWidth, cellHeight);

        Color backColor;

        if (cell.isClosed()) {
            if (cell.getColour() != Colours.disappeared) {
                backColor = Colours.closed;
            } else {
                backColor = Colours.disappeared;
            }
        } else {
            backColor = cell.getColour();
        }

        g2d.setColor(backColor);
        g2d.fillOval(0, 0, size, size);
    }

    private void newGame () {
        a = null;
        b = null;
        logic = new Logic();
        logic.newGame();
        JTableUtils.resizeJTable(gameField, logic.getNumberOfRow(), logic.getNumberOfCol(), gameField.getRowHeight(),
                gameField.getRowHeight());
        updateView();
    }

    private void updateView () {
        gameField.repaint();

        scoreValue.setText(String.valueOf(logic.getScore()));

        if (logic.getState() != GameState.playing) {
            if (logic.getState() == GameState.notStarted) {
                newGame();
            } else {
                int bs = Integer.parseInt(bestScoreValue.getText());
                int s = logic.endGame();
                JOptionPane.showInputDialog("Игра закончилась!\n" + " \n" +
                        "Ваш счет: " + s + "\n" + "Рекорд: ");
                if (s > bs) {
                    bestScoreValue.setText(String.valueOf(s));
                    BestScore.writeScore(s);
                }

            }
        }
    }
}
