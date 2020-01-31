package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

import android.graphics.Bitmap;

import java.util.List;

public class HeroImages {

    private Bitmap mStartImage;
    private List<Bitmap> mAnimationImages;

    public HeroImages(Bitmap startImage, List<Bitmap> animationImages) {
        mStartImage = startImage;
        mAnimationImages = animationImages;
    }

    public Bitmap getStartImage() {
        return mStartImage;
    }

    public List<Bitmap> getAnimationImages() {
        return mAnimationImages;
    }
}
