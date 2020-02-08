package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public abstract class DungeonPart {

    static final String ANIMATION_PROPERTY = "animation";
    static final String ANIMATION_PROPERTY_TOP = "top";
    static final String ANIMATION_PROPERTY_RIGHT = "right";
    static final String ANIMATION_PROPERTY_BOT = "bot";
    static final String ANIMATION_PROPERTY_LEFT = "left";
    static final int ANIMATION_DURATION = 1000;

    protected int mLeft;
    protected int mTop;
    protected int mRight;
    protected int mBottom;

    protected Bitmap mBackgroundImage;

    public DungeonPart(int left, int top, int right, int bottom) {
        mLeft = left;
        mTop = top;
        mRight = right;
        mBottom = bottom;
    }

    DungeonPart(int left, int top, int right, int bottom, Resources resources) {
        this(left, top, right, bottom);
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
