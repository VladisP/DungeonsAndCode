package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

public class Trap extends DungeonPart {

    private TrapType mTrapType;

    public Trap(int left, int top, int right, int bottom, Resources resources, TrapType trapType) {
        super(left, top, right, bottom);
        mTrapType = trapType;
        mBackgroundImage = createBackgroundImage(resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        int drawableId = -1;

        switch (mTrapType) {
            case LEFT:
                drawableId = R.drawable.left_trap;
                break;
            case TOP:
                drawableId = R.drawable.top_trap;
                break;
            case RIGHT:
                drawableId = R.drawable.right_trap;
                break;
            case BOTTOM:
                drawableId = R.drawable.bottom_trap;
                break;
        }

        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, drawableId)
                , mRight - mLeft
                , mBottom - mTop
                , false
        );
    }
}
