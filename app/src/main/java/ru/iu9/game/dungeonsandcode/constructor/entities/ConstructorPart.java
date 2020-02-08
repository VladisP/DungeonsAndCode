package ru.iu9.game.dungeonsandcode.constructor.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.entities.DungeonPart;

public class ConstructorPart extends DungeonPart {

    private ConstructorPartType mPartType;

    public ConstructorPart(int left,
                           int top,
                           int right,
                           int bottom,
                           Resources resources,
                           ConstructorPartType partType
    ) {
        super(left, top, right, bottom);
        mPartType = partType;
        mBackgroundImage = createBackgroundImage(resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        int drawableId = -1;

        switch (mPartType) {
            case FLOOR:
                drawableId = R.drawable.rect_floor;
                break;
            case WALL:
                drawableId = R.drawable.wall;
                break;
            case HERO:
                drawableId = R.drawable.hero_top_anim_1;
                break;
            case TREASURE:
                drawableId = R.drawable.treasure;
                break;
            case MONSTER:
                drawableId = R.drawable.monster;
                break;
            case TRAP_LEFT:
                drawableId = R.drawable.left_trap;
                break;
            case TRAP_TOP:
                drawableId = R.drawable.top_trap;
                break;
            case TRAP_RIGHT:
                drawableId = R.drawable.right_trap;
                break;
            case TRAP_BOTTOM:
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
