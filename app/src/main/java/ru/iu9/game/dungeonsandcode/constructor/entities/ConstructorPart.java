package ru.iu9.game.dungeonsandcode.constructor.entities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorPartType;
import ru.iu9.game.dungeonsandcode.dungeon.entities.DungeonPart;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class ConstructorPart extends DungeonPart {

    private ConstructorPartType mPartType;
    private PositionPair mPosition;
    private Bitmap mForegroundImage;
    private int mForegroundLeftDelta;
    private int mForegroundTopDelta;

    public ConstructorPart(int left,
                           int top,
                           int right,
                           int bottom,
                           PositionPair position,
                           Resources resources
    ) {
        super(left, top, right, bottom);
        mPosition = position;
        mPartType = ConstructorPartType.FLOOR;
        mBackgroundImage = createBackgroundImage(resources);
        mForegroundImage = null;
        mForegroundLeftDelta = 0;
        mForegroundTopDelta = 0;
    }

    public ConstructorPart(int left, int top, int right, int bottom, PositionPair position) {
        super(left, top, right, bottom);
        mPosition = position;
        mForegroundLeftDelta = 0;
        mForegroundTopDelta = 0;
    }

    public ConstructorPartType getPartType() {
        return mPartType;
    }

    public void setPartType(ConstructorPartType partType, Resources resources) {
        mPartType = ConstructorPartType.FLOOR;
        mBackgroundImage = createBackgroundImage(resources);
        mPartType = partType;
        mForegroundImage = createBackgroundImage(resources);
    }

    public PositionPair getPosition() {
        return mPosition;
    }

    public void setForegroundLeftDelta(int foregroundLeftDelta) {
        mForegroundLeftDelta = foregroundLeftDelta;
    }

    public void setForegroundTopDelta(int foregroundTopDelta) {
        mForegroundTopDelta = foregroundTopDelta;
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

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, mLeft, mTop, null);

        if (mForegroundImage != null) {
            canvas.drawBitmap(
                    mForegroundImage,
                    mLeft + mForegroundLeftDelta,
                    mTop + mForegroundTopDelta,
                    null
            );
        }
    }
}
