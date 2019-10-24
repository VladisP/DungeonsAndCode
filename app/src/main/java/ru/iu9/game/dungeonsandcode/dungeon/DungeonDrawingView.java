package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import ru.iu9.game.dungeonsandcode.R;

public class DungeonDrawingView extends View {

    private static final String LOG_TAG = "DnC_Log_Tag";

    private static final int PADDING_MIN_SIZE = 24;
    private static final int DUNGEON_PART_ROW_COUNT = 8;

    private ArrayList<DungeonPart> mDungeonParts = null;
    private Paint mDungeonPartPaint;
    private Paint mBackgroundPaint;
    private Paint mBorderPaint;
    private Bitmap mMainCharacterImage;

    public DungeonDrawingView(Context context) {
        this(context, null);
    }

    public DungeonDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDungeonPartPaint = new Paint();
        mDungeonPartPaint.setColor(0x22ff0000);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(0xfff8efe0);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.BLACK);

        mMainCharacterImage = BitmapFactory.decodeResource(getResources(), R.drawable.main_character);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        buildDungeonParts(Math.min(getHeight(), getWidth()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPaint(mBackgroundPaint);

        for (DungeonPart part : mDungeonParts) {
            Bitmap image = Bitmap.createScaledBitmap(mMainCharacterImage, part.getRight() - part.getLeft(), part.getRight() - part.getLeft(), false);
            canvas.drawBitmap(image, part.getLeft(), part.getTop(), null);
            canvas.drawRect(part.getLeft(), part.getTop(), part.getRight(), part.getBottom(), mDungeonPartPaint);
            canvas.drawLine(part.getLeft(), part.getTop(), part.getRight(), part.getTop(), mBorderPaint);
            canvas.drawLine(part.getLeft(), part.getTop(), part.getLeft(), part.getBottom(), mBorderPaint);
            canvas.drawLine(part.getLeft(), part.getBottom(), part.getRight(), part.getBottom(), mBorderPaint);
            canvas.drawLine(part.getRight(), part.getTop(), part.getRight(), part.getBottom(), mBorderPaint);
        }
    }

    private void buildDungeonParts(int globalSize) {
        int partSize = (globalSize - PADDING_MIN_SIZE * 2) / DUNGEON_PART_ROW_COUNT;
        int paddingTop = (getHeight() - partSize * DUNGEON_PART_ROW_COUNT) / 2;
        int paddingLeft = (getWidth() - partSize * DUNGEON_PART_ROW_COUNT) / 2;
        int coordXValue = paddingLeft;
        int coordYValue = paddingTop;
        mDungeonParts = new ArrayList<>(DUNGEON_PART_ROW_COUNT * DUNGEON_PART_ROW_COUNT);

        for (int i = 0; i < DUNGEON_PART_ROW_COUNT; i++) {

            for (int j = 0; j < DUNGEON_PART_ROW_COUNT; j++) {
                mDungeonParts.add(
                        new DungeonPart(coordXValue
                                , coordYValue
                                , coordXValue + partSize
                                , coordYValue + partSize)
                );
                coordXValue += partSize;
            }

            coordXValue = paddingLeft;
            coordYValue += partSize;
        }
    }
}
