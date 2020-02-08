package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;

public class Floor extends DungeonPart {

    private boolean mIsWall;
    private Trap mTrap;
    private boolean mHasMonster;
    private boolean mHasTreasure;

    public Floor(int left, int top, int right, int bottom, Resources resources, boolean isWall) {
        super(left, top, right, bottom);
        mIsWall = isWall;
        mTrap = null;
        mHasMonster = false;
        mHasTreasure = false;
        mBackgroundImage = createBackgroundImage(resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        int drawableId = mIsWall ? R.drawable.wall : R.drawable.rect_floor;

        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, drawableId)
                , mRight - mLeft
                , mBottom - mTop
                , false
        );
    }

    boolean isWall() {
        return mIsWall;
    }

    Trap getTrap() {
        return mTrap;
    }

    public void setTrap(Trap trap) {
        mTrap = trap;
    }

    boolean hasMonster() {
        return mHasMonster;
    }

    public void setMonster(boolean hasMonster) {
        mHasMonster = hasMonster;
    }

    boolean hasTreasure() {
        return mHasTreasure;
    }

    public void setTreasure(boolean hasTreasure) {
        mHasTreasure = hasTreasure;
    }
}
