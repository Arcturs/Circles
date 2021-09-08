package ru.vsu.csf.Sashina.Logic;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Logic {

    private GameState state = GameState.notStarted;
    private final int numberOfCol = 5;
    private final int numberOfRow = 4;
    private Cell [][] array = null;
    private int score = 0;
    private int openedCells = 0;

    private static final Random RND = new Random();

    public Logic () {

    }

    public Cell getCell (int row, int column) {
        return array[row][column];
    }

    public int getNumberOfCol () {
        return numberOfCol;
    }

    public int getNumberOfRow () {
        return numberOfRow;
    }

    public int getScore () {
        return score;
    }

    public GameState getState () {
        return state;
    }

    private Color getColour (int num) {
        switch(num) {
            case 1: return Colours.colour1;
            case 2: return Colours.colour2;
            case 3: return Colours.colour3;
            case 4: return Colours.colour4;
            case 5: return Colours.colour5;
            case 6: return Colours.colour6;
            case 7: return Colours.colour7;
            case 8: return Colours.colour8;
            case 9: return Colours.colour9;
            case 10: return Colours.colour10;
            default: return Colours.disappeared;
        }
    }

    public void newGame () {
        array = new Cell[numberOfRow][numberOfCol];
        score = 0;
        openedCells = 0;
        int [] arr = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

        for (int i = 0; i < numberOfRow; i++) {
            for (int j = 0; j < numberOfCol; j++) {
                int rnd = RND.nextInt(10);
                while (arr[rnd] == 0) {
                    rnd = RND.nextInt(10);
                }
                array[i][j] = new Cell(getColour(rnd + 1), true);
                arr[rnd]--;
            }
        }

        state = GameState.playing;
    }

    public void compareCircles (Cell a, Cell b) {
        if (a.getColour() == b.getColour()) {
            score += 25;
            a.setColor(Colours.disappeared);
            b.setColor(Colours.disappeared);
        } else {
            score -= 5;
            openedCells -= 2;
        }
        a.makeItClosed();
        b.makeItClosed();
    }

    public Cell operateWithCell (int row, int col) {
        Cell a = getCell(row, col);
        a.makeItOpened();
        openedCells++;
        return a;
    }

    private void openedCells () {
        openedCells++;
    }

    public void checkGameState () {
        if (openedCells == 20) {
            state = GameState.end;
        }
    }

    public int endGame () {
        return score;
    }
}
