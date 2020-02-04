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

    public static final int DELTA = 64;

    private TrapType mTrapType;
    private boolean mIsAnimated;

    public Trap(int left, int top, int right, int bottom, Resources resources, TrapType trapType) {
        super(left, top, right, bottom);
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
        switch (mTrapType) {
            case LEFT:
                startAnimation(trapMoveAction, mLeft, mLeft + (mRight - mLeft) * 2 / 3);
                break;
            case TOP:
                startAnimation(trapMoveAction, mTop, mTop + (mBottom - mTop) * 2 / 3);
                break;
            case RIGHT:
                startAnimation(trapMoveAction, mLeft, mLeft - (mRight - mLeft) * 2 / 3);
                break;
            case BOTTOM:
                startAnimation(trapMoveAction, mTop, mTop - (mBottom - mTop) * 2 / 3);
                break;
        }
    }

    private void startAnimation(final MoveAction trapMoveAction, final int startValue, final int endValue) {
        PropertyValuesHolder property = PropertyValuesHolder.ofInt(
                ANIMATION_PROPERTY,
                startValue, endValue
        );

        ValueAnimator trapAnimator = new ValueAnimator();
        trapAnimator.setValues(property);
        trapAnimator.setDuration(ANIMATION_DURATION);

        trapAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int currentValue = (int) animation.getAnimatedValue(ANIMATION_PROPERTY);

                if (currentValue == endValue) {
                    setValue(startValue);
                    setAnimated(false);
                } else {
                    setValue(currentValue);
                }

                trapMoveAction.moveCallback();
            }
        });

        trapAnimator.start();
    }

    private void setValue(int value) {
        switch (mTrapType) {
            case LEFT:
            case RIGHT:
                mLeft = value;
                break;
            case TOP:
            case BOTTOM:
                mTop = value;
                break;
        }
    }
}
