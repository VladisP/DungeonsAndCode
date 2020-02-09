package ru.iu9.game.dungeonsandcode.constructor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Stack;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.entities.ConstructorPart;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorEventListener;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorPartType;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Trap;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class ConstructorView extends View {

    private static final int PADDING_MIN_SIZE = 24;
    private static final int FLOORS_ROW_COUNT = 8;

    private ConstructorEventListener mConstructorEventListener;
    private ConstructorPartType mCurrentPartType = ConstructorPartType.WALL;
    private ConstructorPart[][] mParts;
    private Bitmap mBackgroundImage;
    private boolean mHasHero = false;
    private boolean mHasTreasure = false;
    private Stack<PositionPair> mHistoryStack = new Stack<>();

    public ConstructorView(Context context) {
        super(context);
    }

    public ConstructorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mConstructorEventListener = (ConstructorEventListener) context;
    }

    void setCurrentPartType(ConstructorPartType newPartType) {
        mCurrentPartType = newPartType;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mBackgroundImage = createBackgroundImage(getWidth(), getHeight());
        mParts = createFloors(Math.min(getHeight(), getWidth()));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        drawParts(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mConstructorEventListener = null;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            createConstructorPart((int) event.getX(), (int) event.getY());
            return true;
        }

        return false;
    }

    private void createConstructorPart(int x, int y) {
        ConstructorPart part = findPartByCoords(x, y);

        if (part == null) {
            return;
        }

        if (part.getPartType() != ConstructorPartType.FLOOR) {
            mConstructorEventListener.showErrorMessage(R.string.cell_is_taken);
            return;
        }

        if (mCurrentPartType == ConstructorPartType.HERO && mHasHero) {
            mConstructorEventListener.showErrorMessage(R.string.unique_hero);
            return;
        }

        if (mCurrentPartType == ConstructorPartType.TREASURE && mHasTreasure) {
            mConstructorEventListener.showErrorMessage(R.string.unique_treasure);
            return;
        }

        mHistoryStack.push(part.getPosition());

        ConstructorPart newPart = new ConstructorPart(
                part.getLeft(),
                part.getTop(),
                part.getRight(),
                part.getBottom(),
                part.getPosition()
        );

        newPart.setForegroundLeftDelta(getLeftDelta());
        newPart.setForegroundTopDelta(getTopDelta());

        switch (mCurrentPartType) {
            case WALL:
                newPart.setPartType(ConstructorPartType.WALL, getResources());
                break;
            case HERO:
                mHasHero = true;
                newPart.setPartType(ConstructorPartType.HERO, getResources());
                break;
            case TREASURE:
                mHasTreasure = true;
                newPart.setPartType(ConstructorPartType.TREASURE, getResources());
                break;
            case MONSTER:
                newPart.setPartType(ConstructorPartType.MONSTER, getResources());
                break;
            case TRAP_LEFT:
                newPart.setPartType(ConstructorPartType.TRAP_LEFT, getResources());
                break;
            case TRAP_TOP:
                newPart.setPartType(ConstructorPartType.TRAP_TOP, getResources());
                break;
            case TRAP_RIGHT:
                newPart.setPartType(ConstructorPartType.TRAP_RIGHT, getResources());
                break;
            case TRAP_BOTTOM:
                newPart.setPartType(ConstructorPartType.TRAP_BOTTOM, getResources());
                break;
        }

        mParts[part.getPosition().getRowPosition()][part.getPosition().getColumnPosition()] = newPart;
        invalidate();
    }

    private ConstructorPart findPartByCoords(int x, int y) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                ConstructorPart part = mParts[i][j];

                if (part.getLeft() <= x && part.getRight() >= x && part.getTop() <= y && part.getBottom() >= y) {
                    return part;
                }
            }
        }

        return null;
    }

    private int getLeftDelta() {
        return mCurrentPartType == ConstructorPartType.TRAP_LEFT ? -Trap.DELTA
                : mCurrentPartType == ConstructorPartType.TRAP_RIGHT ? Trap.DELTA
                : 0;
    }

    private int getTopDelta() {
        return mCurrentPartType == ConstructorPartType.TRAP_TOP ? -Trap.DELTA
                : mCurrentPartType == ConstructorPartType.TRAP_BOTTOM ? Trap.DELTA
                : 0;
    }

    void removeLastAddedPart() {
        if (mHistoryStack.empty()) {
            return;
        }

        PositionPair position = mHistoryStack.pop();
        ConstructorPart basePart = mParts[position.getRowPosition()][position.getColumnPosition()];

        if (basePart.getPartType() == ConstructorPartType.HERO) {
            mHasHero = false;
        } else if (basePart.getPartType() == ConstructorPartType.TREASURE) {
            mHasTreasure = false;
        }

        mParts[position.getRowPosition()][position.getColumnPosition()] = new ConstructorPart(
                basePart.getLeft(),
                basePart.getTop(),
                basePart.getRight(),
                basePart.getBottom(),
                position,
                getResources()
        );

        invalidate();
    }

    void removeAll() {
        while (!mHistoryStack.empty()) {
            removeLastAddedPart();
        }
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
                        new PositionPair(i, j),
                        getResources()
                );

                coordXValue += floorSize;
            }

            coordXValue = paddingLeft;
            coordYValue += floorSize;
        }

        return floors;
    }

    private void drawParts(Canvas canvas) {
        drawNotTrapParts(canvas);
        drawTraps(canvas);
    }

    private void drawNotTrapParts(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                if (!isTrap(mParts[i][j])) {
                    mParts[i][j].draw(canvas);
                }
            }
        }
    }

    private void drawTraps(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                if (isTrap(mParts[i][j])) {
                    mParts[i][j].draw(canvas);
                }
            }
        }
    }

    private boolean isTrap(ConstructorPart part) {
        return part.getPartType() == ConstructorPartType.TRAP_LEFT ||
                part.getPartType() == ConstructorPartType.TRAP_TOP ||
                part.getPartType() == ConstructorPartType.TRAP_RIGHT ||
                part.getPartType() == ConstructorPartType.TRAP_BOTTOM;
    }
}
