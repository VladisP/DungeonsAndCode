package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import ru.iu9.game.dungeonsandcode.R;

public class DungeonDrawingView extends View {

    private static final String LOG_TAG = "DnC_Log_Tag";

    private static final int PADDING_MIN_SIZE = 24;
    private static final int FLOORS_ROW_COUNT = 8;

    private Floor[][] mFloors;
    private Hero mHero;
    private Bitmap mBackgroundImage;

    public DungeonDrawingView(Context context) {
        this(context, null);
    }

    public DungeonDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mFloors = createFloors(Math.min(getHeight(), getWidth()));
        mBackgroundImage = createBackgroundImage(getWidth(), getHeight());
        mHero = createHero();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        drawFloors(canvas);
        mHero.draw(canvas);
    }

    private Bitmap createBackgroundImage(int width, int height) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.scene_background)
                , width
                , height
                , true
        );
    }

    private Floor[][] createFloors(int globalSize) {
        int floorSize = (globalSize - PADDING_MIN_SIZE * 2) / FLOORS_ROW_COUNT;
        int paddingTop = (getHeight() - floorSize * FLOORS_ROW_COUNT) / 2;
        int paddingLeft = (getWidth() - floorSize * FLOORS_ROW_COUNT) / 2;
        int coordXValue = paddingLeft;
        int coordYValue = paddingTop;

        Floor[][] floors = new Floor[FLOORS_ROW_COUNT][FLOORS_ROW_COUNT];

        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {

            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {

                floors[i][j] = new Floor(
                        coordXValue
                        , coordYValue
                        , coordXValue + floorSize
                        , coordYValue + floorSize
                        , getResources()
                );
                coordXValue += floorSize;
            }

            coordXValue = paddingLeft;
            coordYValue += floorSize;
        }

        return floors;
    }

    private Hero createHero() {
        Floor startFloor = mFloors[FLOORS_ROW_COUNT - 1][0];

        return new Hero(
                startFloor.getLeft()
                , startFloor.getTop()
                , startFloor.getRight()
                , startFloor.getBottom()
                , getResources()
        );
    }

    private void drawFloors(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                mFloors[i][j].draw(canvas);
            }
        }
    }
}
