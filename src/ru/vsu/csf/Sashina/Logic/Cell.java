package ru.vsu.csf.Sashina.Logic;

import java.awt.*;

public class Cell {

    private Color colour;
    private boolean closed;

    public Cell (Color colour, boolean closed) {
        this.colour = colour;
        this.closed = closed;
    }

    public void makeItClosed () {
        closed = true;
    }

    public void makeItOpened () {
        closed = false;
    }

    public boolean isClosed () {
        return closed;
    }

    public Color getColour () {
        return colour;
    }

    public void setColor (Color col) {
        colour = col;
    }
}
