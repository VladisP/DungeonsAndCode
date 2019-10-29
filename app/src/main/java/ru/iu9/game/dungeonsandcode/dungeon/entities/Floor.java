package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;

public class Floor extends DungeonPart {

    private boolean mIsWall;

    public Floor(int left, int top, int right, int bottom, Resources resources, boolean isWall) {
        super(left, top, right, bottom);
        mIsWall = isWall;
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
}
