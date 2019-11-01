package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

public class PositionPair {

    private int mRowPosition;
    private int mColumnPosition;

    public PositionPair(int rowPosition, int columnPosition) {
        mRowPosition = rowPosition;
        mColumnPosition = columnPosition;
    }

    public int getRowPosition() {
        return mRowPosition;
    }

    public void setRowPosition(int rowPosition) {
        mRowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return mColumnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        mColumnPosition = columnPosition;
    }
}
