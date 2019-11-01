package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Floor;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Hero;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Monster;
import ru.iu9.game.dungeonsandcode.dungeon.entities.PositionPair;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Trap;
import ru.iu9.game.dungeonsandcode.dungeon.entities.TrapType;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Treasure;

public class DungeonView extends View {

    private static final String LOG_TAG = "DnC_Log_Tag";

    private static final int PADDING_MIN_SIZE = 24;
    private static final int FLOORS_ROW_COUNT = 8;

    private Floor[][] mFloors;
    private Hero mHero;
    private Treasure mTreasure;
    private Monster mMonster;
    private Trap mTrap;
    private Bitmap mBackgroundImage;

    public DungeonView(Context context) {
        this(context, null);
    }

    public DungeonView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mFloors = createFloors(Math.min(getHeight(), getWidth()));
        mBackgroundImage = createBackgroundImage(getWidth(), getHeight());
        mHero = createHero();
        mTreasure = createTreasure();
        mMonster = createMonster();
        mTrap = createTrap();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        drawFloors(canvas);
        mTreasure.draw(canvas);
        mMonster.draw(canvas);
        mTrap.draw(canvas);
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

        //TODO: delete this array later...
        boolean[][] isWalls = {
                {false, false, true, true, true, true, true, true}
                , {true, true, false, false, false, false, false, true}
                , {false, false, false, false, true, true, false, true}
                , {false, true, true, true, true, true, false, true}
                , {false, true, false, false, false, true, false, true}
                , {false, true, false, false, false, false, true, true}
                , {false, true, false, false, false, false, true, true}
                , {false, true, false, false, false, false, true, true}
        };

        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {

            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {

                floors[i][j] = new Floor(
                        coordXValue
                        , coordYValue
                        , coordXValue + floorSize
                        , coordYValue + floorSize
                        , getResources()
                        , isWalls[i][j]
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
                , new PositionPair(FLOORS_ROW_COUNT - 1, 0)
        );
    }

    private Treasure createTreasure() {
        Floor startFloor = mFloors[4][6];

        return new Treasure(
                startFloor.getLeft()
                , startFloor.getTop()
                , startFloor.getRight()
                , startFloor.getBottom()
                , getResources()
        );
    }

    private Monster createMonster() {
        Floor startFloor = mFloors[2][3];

        return new Monster(
                startFloor.getLeft()
                , startFloor.getTop()
                , startFloor.getRight()
                , startFloor.getBottom()
                , getResources()
        );
    }

    private Trap createTrap() {
        Floor startFloor = mFloors[2][6];

        return new Trap(
                startFloor.getLeft()
                , startFloor.getTop()
                , startFloor.getRight()
                , startFloor.getBottom()
                , getResources()
                , TrapType.LEFT
        );
    }

    private void drawFloors(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                mFloors[i][j].draw(canvas);
            }
        }
    }

    public void moveHeroUp() {
        mHero.moveUp(mFloors);
        invalidate();
    }

    public void moveHeroLeft() {
        mHero.moveLeft(mFloors);
        invalidate();
    }

    public void moveHeroRight() {
        mHero.moveRight(mFloors);
        invalidate();
    }

    public void moveHeroDown() {
        mHero.moveDown(mFloors);
        invalidate();
    }
}
