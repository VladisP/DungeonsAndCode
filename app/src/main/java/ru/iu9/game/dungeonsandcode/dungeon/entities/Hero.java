package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;

public class Hero extends DungeonPart {

    private PositionPair mPositionPair;

    public Hero(int left, int top, int right, int bottom, Resources resources, PositionPair positionPair) {
        super(left, top, right, bottom, resources);
        mPositionPair = positionPair;
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.hero)
                , mRight - mLeft
                , mBottom - mTop
                , false
        );
    }

    public void moveUp(Floor[][] floors) {
        int rowPosition = mPositionPair.getRowPosition();

        if (rowPosition == 0 || floors[rowPosition - 1][mPositionPair.getColumnPosition()].isWall()) {
            return;
        }

        mPositionPair.setRowPosition(rowPosition - 1);
        bindWithFloor(floors);
    }

    public void moveLeft(Floor[][] floors) {
        int columnPosition = mPositionPair.getColumnPosition();

        if (columnPosition == 0 || floors[mPositionPair.getRowPosition()][columnPosition - 1].isWall()) {
            return;
        }

        mPositionPair.setColumnPosition(columnPosition - 1);
        bindWithFloor(floors);
    }

    public void moveRight(Floor[][] floors) {
        int columnPosition = mPositionPair.getColumnPosition();

        if (columnPosition == floors.length - 1 || floors[mPositionPair.getRowPosition()][columnPosition + 1].isWall()) {
            return;
        }

        mPositionPair.setColumnPosition(columnPosition + 1);
        bindWithFloor(floors);
    }

    public void moveDown(Floor[][] floors) {
        int rowPosition = mPositionPair.getRowPosition();

        if (rowPosition == floors.length - 1 || floors[rowPosition + 1][mPositionPair.getColumnPosition()].isWall()) {
            return;
        }

        mPositionPair.setRowPosition(rowPosition + 1);
        bindWithFloor(floors);
    }

    private void bindWithFloor(Floor[][] floors) {
        Floor heroFloor = floors[mPositionPair.getRowPosition()][mPositionPair.getColumnPosition()];
        mLeft = heroFloor.mLeft;
        mTop = heroFloor.mTop;
        mRight = heroFloor.mRight;
        mBottom = heroFloor.mBottom;
    }
}
