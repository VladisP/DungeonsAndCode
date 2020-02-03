package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class Trap extends DungeonPart {

    private static final int LEFT_DELTA = 64;

    private TrapType mTrapType;
    private boolean mIsAnimated;

    public Trap(int left, int top, int right, int bottom, Resources resources, TrapType trapType) {
        super(left - LEFT_DELTA, top, right - LEFT_DELTA, bottom);
        mTrapType = trapType;
        mIsAnimated = false;
        mBackgroundImage = createBackgroundImage(resources);
    }

    boolean isAnimated() {
        return mIsAnimated;
    }

    void setAnimated(boolean animated) {
        mIsAnimated = animated;
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

    void startAnimation(final MoveAction trapMoveAction) {
        final int startLeft = mLeft;
        final int endLeft = mLeft + (mRight - mLeft) * 2 / 3;

        PropertyValuesHolder propertyLeft = PropertyValuesHolder.ofInt(
                ANIMATION_PROPERTY_LEFT,
                startLeft, endLeft
        );

        ValueAnimator trapAnimator = new ValueAnimator();
        trapAnimator.setValues(propertyLeft);
        trapAnimator.setDuration(ANIMATION_DURATION);

        trapAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentLeft = (int) animation.getAnimatedValue(ANIMATION_PROPERTY_LEFT);

                if (currentLeft == endLeft) {
                    mLeft = startLeft;
                    setAnimated(false);
                } else {
                    mLeft = currentLeft;
                }

                trapMoveAction.moveCallback();
            }
        });

        trapAnimator.start();
    }
}
