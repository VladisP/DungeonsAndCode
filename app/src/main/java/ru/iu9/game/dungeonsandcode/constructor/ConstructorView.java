package ru.iu9.game.dungeonsandcode.constructor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.List;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.entities.ConstructorPart;
import ru.iu9.game.dungeonsandcode.constructor.entities.ConstructorPartType;

public class ConstructorView extends View {

    private static final int PADDING_MIN_SIZE = 24;
    private static final int FLOORS_ROW_COUNT = 8;

    private ConstructorPart[][] mFloors;
    private ConstructorPart mHero;
    private ConstructorPart mTreasure;
    private List<ConstructorPart> mMonsters;
    private List<ConstructorPart> mTraps;
    private Bitmap mBackgroundImage;

    public ConstructorView(Context context) {
        super(context);
    }

    public ConstructorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mBackgroundImage = createBackgroundImage(getWidth(), getHeight());
        mFloors = createFloors(Math.min(getHeight(), getWidth()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        drawFloors(canvas);
    }

    private Bitmap createBackgroundImage(int width, int height) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.scene_background),
                width,
                height,
                true
        );
    }

    private ConstructorPart[][] createFloors(int globalSize) {
        int floorSize = (globalSize - PADDING_MIN_SIZE * 2) / FLOORS_ROW_COUNT;
        int paddingTop = (getHeight() - floorSize * FLOORS_ROW_COUNT) / 2;
        int paddingLeft = (getWidth() - floorSize * FLOORS_ROW_COUNT) / 2;
        int coordXValue = paddingLeft;
        int coordYValue = paddingTop;

        ConstructorPart[][] floors = new ConstructorPart[FLOORS_ROW_COUNT][FLOORS_ROW_COUNT];

        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                floors[i][j] = new ConstructorPart(
                        coordXValue,
                        coordYValue,
                        coordXValue + floorSize,
                        coordYValue + floorSize,
                        getResources(),
                        ConstructorPartType.FLOOR
                );

                coordXValue += floorSize;
            }

            coordXValue = paddingLeft;
            coordYValue += floorSize;
        }

        return floors;
    }

    private void drawFloors(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                mFloors[i][j].draw(canvas);
            }
        }
    }
}
