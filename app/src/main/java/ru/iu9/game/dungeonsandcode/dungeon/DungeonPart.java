package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class DungeonPart {

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    private Bitmap mBackgroundImage;

    DungeonPart(int left, int top, int right, int bottom, Resources resources) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        mBackgroundImage = createBackgroundImage(resources);
    }

    int getLeft() {
        return mLeft;
    }

    int getTop() {
        return mTop;
    }

    int getRight() {
        return mRight;
    }

    int getBottom() {
        return mBottom;
    }

    protected abstract Bitmap createBackgroundImage(Resources resources);

    void draw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, mLeft, mTop, null);
    }
}
