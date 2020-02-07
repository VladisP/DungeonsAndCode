package ru.iu9.game.dungeonsandcode.dungeon.entities;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.HeroImages;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.HeroActions;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class Hero extends DungeonPart {

    private static final int ANIMATION_FREQUENCY_FACTOR = 4;

    private PositionPair mStartPosition;
    private PositionPair mCurrentPosition;
    private HeroImages mHeroTopImages;
    private HeroImages mHeroRightImages;
    private HeroImages mHeroBotImages;
    private HeroImages mHeroLeftImages;

    public Hero(int left, int top, int right, int bottom, Resources resources, PositionPair position) {
        super(left, top, right, bottom);
        mStartPosition = new PositionPair(position.getRowPosition(), position.getColumnPosition());
        mCurrentPosition = position;
        initHeroImages(resources);
        mBackgroundImage = createBackgroundImage(resources);
    }

    @Override
    protected Bitmap createBackgroundImage(Resources resources) {
        return mHeroTopImages.getStartImage();
    }

    private Bitmap createHeroImageById(Resources resources, int imageId) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(resources, imageId),
                mRight - mLeft,
                mBottom - mTop,
                false
        );
    }

    private void initHeroImages(Resources resources) {
        mHeroTopImages = createHeroImages(resources, HeroDirection.TOP);
        mHeroRightImages = createHeroImages(resources, HeroDirection.RIGHT);
        mHeroBotImages = createHeroImages(resources, HeroDirection.BOTTOM);
        mHeroLeftImages = createHeroImages(resources, HeroDirection.LEFT);
    }

    private HeroImages createHeroImages(Resources resources, HeroDirection heroDirection) {
        Bitmap heroStartImage = createHeroImageById(resources, getHeroStartImageId(heroDirection));
        List<Bitmap> heroAnimImages = new ArrayList<>();

        List<Integer> imagesIds = getHeroAnimImagesIds(heroDirection);

        for (Integer id : imagesIds) {
            heroAnimImages.add(createHeroImageById(resources, id));
        }

        return new HeroImages(heroStartImage, heroAnimImages);
    }

    private Integer getHeroStartImageId(HeroDirection heroDirection) {
        switch (heroDirection) {
            case TOP:
                return R.drawable.hero_top_anim_1;
            case RIGHT:
                return R.drawable.hero_right_anim_1;
            case BOTTOM:
                return R.drawable.hero_bot_anim_1;
            case LEFT:
            default:
                return R.drawable.hero_left_anim_1;
        }
    }

    private List<Integer> getHeroAnimImagesIds(HeroDirection heroDirection) {
        List<Integer> ids = new ArrayList<>();

        switch (heroDirection) {
            case TOP:
                ids.add(R.drawable.hero_top_anim_2);
                ids.add(R.drawable.hero_top_anim_3);
                ids.add(R.drawable.hero_top_anim_4);
                ids.add(R.drawable.hero_top_anim_5);
                ids.add(R.drawable.hero_top_anim_6);
                ids.add(R.drawable.hero_top_anim_7);
                ids.add(R.drawable.hero_top_anim_8);
                ids.add(R.drawable.hero_top_anim_9);
                break;
            case RIGHT:
                ids.add(R.drawable.hero_right_anim_2);
                ids.add(R.drawable.hero_right_anim_3);
                ids.add(R.drawable.hero_right_anim_4);
                ids.add(R.drawable.hero_right_anim_5);
                ids.add(R.drawable.hero_right_anim_6);
                ids.add(R.drawable.hero_right_anim_7);
                ids.add(R.drawable.hero_right_anim_8);
                ids.add(R.drawable.hero_right_anim_9);
                break;
            case BOTTOM:
                ids.add(R.drawable.hero_bot_anim_2);
                ids.add(R.drawable.hero_bot_anim_3);
                ids.add(R.drawable.hero_bot_anim_4);
                ids.add(R.drawable.hero_bot_anim_5);
                ids.add(R.drawable.hero_bot_anim_6);
                ids.add(R.drawable.hero_bot_anim_7);
                ids.add(R.drawable.hero_bot_anim_8);
                ids.add(R.drawable.hero_bot_anim_9);
                break;
            case LEFT:
                ids.add(R.drawable.hero_left_anim_2);
                ids.add(R.drawable.hero_left_anim_3);
                ids.add(R.drawable.hero_left_anim_4);
                ids.add(R.drawable.hero_left_anim_5);
                ids.add(R.drawable.hero_left_anim_6);
                ids.add(R.drawable.hero_left_anim_7);
                ids.add(R.drawable.hero_left_anim_8);
                ids.add(R.drawable.hero_left_anim_9);
                break;
        }

        return ids;
    }

    public void moveUp(Floor[][] floors, HeroActions heroActions) {
        int rowPosition = mCurrentPosition.getRowPosition();

        if (rowPosition == 0 || floors[rowPosition - 1][mCurrentPosition.getColumnPosition()].isWall()) {
            heroActions.moveEndAction();
            return;
        }

        mCurrentPosition.setRowPosition(rowPosition - 1);
        bindWithFloor(floors, heroActions, mHeroTopImages);
    }

    public void moveLeft(Floor[][] floors, HeroActions heroActions) {
        int columnPosition = mCurrentPosition.getColumnPosition();

        if (columnPosition == 0 || floors[mCurrentPosition.getRowPosition()][columnPosition - 1].isWall()) {
            heroActions.moveEndAction();
            return;
        }

        mCurrentPosition.setColumnPosition(columnPosition - 1);
        bindWithFloor(floors, heroActions, mHeroLeftImages);
    }

    public void moveRight(Floor[][] floors, HeroActions heroActions) {
        int columnPosition = mCurrentPosition.getColumnPosition();

        if (columnPosition == floors.length - 1 || floors[mCurrentPosition.getRowPosition()][columnPosition + 1].isWall()) {
            heroActions.moveEndAction();
            return;
        }

        mCurrentPosition.setColumnPosition(columnPosition + 1);
        bindWithFloor(floors, heroActions, mHeroRightImages);
    }

    public void moveDown(Floor[][] floors, HeroActions heroActions) {
        int rowPosition = mCurrentPosition.getRowPosition();

        if (rowPosition == floors.length - 1 || floors[rowPosition + 1][mCurrentPosition.getColumnPosition()].isWall()) {
            heroActions.moveEndAction();
            return;
        }

        mCurrentPosition.setRowPosition(rowPosition + 1);
        bindWithFloor(floors, heroActions, mHeroBotImages);
    }

    private void bindWithFloor(Floor[][] floors, final HeroActions heroActions, final HeroImages heroImages) {
        final Floor heroFloor = floors[mCurrentPosition.getRowPosition()][mCurrentPosition.getColumnPosition()];

        PropertyValuesHolder propertyTop = PropertyValuesHolder.ofInt(ANIMATION_PROPERTY_TOP, mTop, heroFloor.mTop);
        PropertyValuesHolder propertyRight = PropertyValuesHolder.ofInt(ANIMATION_PROPERTY_RIGHT, mRight, heroFloor.mRight);
        PropertyValuesHolder propertyBot = PropertyValuesHolder.ofInt(ANIMATION_PROPERTY_BOT, mBottom, heroFloor.mBottom);
        PropertyValuesHolder propertyLeft = PropertyValuesHolder.ofInt(ANIMATION_PROPERTY_LEFT, mLeft, heroFloor.mLeft);

        ValueAnimator heroMoveAnimator = new ValueAnimator();
        heroMoveAnimator.setValues(propertyTop, propertyRight, propertyBot, propertyLeft);
        heroMoveAnimator.setDuration(ANIMATION_DURATION);

        heroMoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private int animPosition = 0;
            private int frequencyCounter = 0;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (
                        (int) animation.getAnimatedValue(ANIMATION_PROPERTY_TOP) == heroFloor.mTop &&
                                (int) animation.getAnimatedValue(ANIMATION_PROPERTY_RIGHT) == heroFloor.mRight &&
                                (int) animation.getAnimatedValue(ANIMATION_PROPERTY_BOT) == heroFloor.mBottom &&
                                (int) animation.getAnimatedValue(ANIMATION_PROPERTY_LEFT) == heroFloor.mLeft
                ) {
                    mBackgroundImage = heroImages.getStartImage();

                    if (heroFloor.getTrap() != null && !heroActions.isDodge(heroFloor.getTrap().getTrapType())) {
                        heroActions.moveToTrapAction();
                    } else if (heroFloor.hasMonster()) {
                        heroActions.moveToMonsterAction();
                    } else if (heroFloor.hasTreasure()) {
                        heroActions.moveToTreasureAction();
                    } else {
                        heroActions.moveEndAction();
                    }
                } else if (frequencyCounter++ % ANIMATION_FREQUENCY_FACTOR == 0) {
                    animPosition = (animPosition + 1) % heroImages.getAnimationImages().size();
                    mBackgroundImage = heroImages.getAnimationImages().get(animPosition);
                }

                mTop = (int) animation.getAnimatedValue(ANIMATION_PROPERTY_TOP);
                mRight = (int) animation.getAnimatedValue(ANIMATION_PROPERTY_RIGHT);
                mBottom = (int) animation.getAnimatedValue(ANIMATION_PROPERTY_BOT);
                mLeft = (int) animation.getAnimatedValue(ANIMATION_PROPERTY_LEFT);

                heroActions.moveAction();

                if (heroFloor.getTrap() != null && !heroFloor.getTrap().isAnimated()) {
                    heroFloor.getTrap().setAnimated(true);
                    heroFloor.getTrap().startAnimation(heroActions.getOnMoveAction());
                }
            }
        });

        heroMoveAnimator.start();
    }

    public void changeDirection(HeroDirection heroDirection, MoveAction onChangeDirectionAction) {
        switch (heroDirection) {
            case TOP:
                mBackgroundImage = mHeroTopImages.getStartImage();
                break;
            case RIGHT:
                mBackgroundImage = mHeroRightImages.getStartImage();
                break;
            case BOTTOM:
                mBackgroundImage = mHeroBotImages.getStartImage();
                break;
            case LEFT:
                mBackgroundImage = mHeroLeftImages.getStartImage();
                break;
        }

        onChangeDirectionAction.moveCallback();
    }

    public void reset(Floor[][] floors) {
        mCurrentPosition.setRowPosition(mStartPosition.getRowPosition());
        mCurrentPosition.setColumnPosition(mStartPosition.getColumnPosition());

        Floor heroFloor = floors[mCurrentPosition.getRowPosition()][mCurrentPosition.getColumnPosition()];

        mTop = heroFloor.getTop();
        mRight = heroFloor.getRight();
        mBottom = heroFloor.getBottom();
        mLeft = heroFloor.getLeft();

        mBackgroundImage = mHeroTopImages.getStartImage();
    }
}
