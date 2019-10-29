package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;

public class Hero extends DungeonPart {

    Hero(int left, int top, int right, int bottom, Resources resources) {
        super(left, top, right, bottom, resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, R.drawable.hero)
                , getRight() - getLeft()
                , getBottom() - getTop()
                , false
        );
    }
}
