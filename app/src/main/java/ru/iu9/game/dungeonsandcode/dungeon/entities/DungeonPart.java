package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class DungeonPart {

    int mLeft;
    int mTop;
    int mRight;
    int mBottom;

    private Bitmap mBackgroundImage;

    DungeonPart(int left, int top, int right, int bottom, Resources resources) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        mBackgroundImage = createBackgroundImage(resources);
    }

    public int getLeft() {
        return mLeft;
    }

    public int getTop() {
        return mTop;
    }

    public int getRight() {
        return mRight;
    }

    public int getBottom() {
        return mBottom;
    }

    protected abstract Bitmap createBackgroundImage(Resources resources);

    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, mLeft, mTop, null);
    }
}
