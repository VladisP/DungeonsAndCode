package ru.iu9.game.dungeonsandcode.dungeon.entities;

public class PositionPair {

    private int mRowPosition;
    private int mColumnPosition;

    public PositionPair(int rowPosition, int columnPosition) {
        mRowPosition = rowPosition;
        mColumnPosition = columnPosition;
    }

    int getRowPosition() {
        return mRowPosition;
    }

    void setRowPosition(int rowPosition) {
        mRowPosition = rowPosition;
    }

    int getColumnPosition() {
        return mColumnPosition;
    }

    void setColumnPosition(int columnPosition) {
        mColumnPosition = columnPosition;
    }
}
