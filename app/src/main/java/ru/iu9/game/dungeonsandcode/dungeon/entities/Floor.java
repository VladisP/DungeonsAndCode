package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;

public class Floor extends DungeonPart {

    public Floor(int left, int top, int right, int bottom, Resources resources) {
        super(left, top, right, bottom, resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.rect_floor)
                , mRight - mLeft
                , mBottom - mTop
                , false
        );
    }
}
