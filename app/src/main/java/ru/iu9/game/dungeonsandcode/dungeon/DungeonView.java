package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.activities.GameActivity;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;
import ru.iu9.game.dungeonsandcode.dungeon.config.TrapConfig;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Floor;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Hero;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Monster;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Trap;
import ru.iu9.game.dungeonsandcode.dungeon.entities.Treasure;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.DialogEventListener;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.HeroActions;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

public class DungeonView extends View {

    private static final int PADDING_MIN_SIZE = 24;
    private static final int FLOORS_ROW_COUNT = 8;

    private DialogEventListener mDialogEventListener;
    private DungeonConfig mDungeonConfig;
    private Floor[][] mFloors;
    private Hero mHero;
    private Treasure mTreasure;
    private Monster[] mMonsters;
    private Trap[] mTraps;
    private Bitmap mBackgroundImage;

    public DungeonView(Context context) {
        this(context, null);
    }

    public DungeonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        GameActivity gameActivity = (GameActivity) context;

        mDungeonConfig = gameActivity.getDungeonConfig();
        mDialogEventListener = gameActivity;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mFloors = createFloors(Math.min(getHeight(), getWidth()));
        mBackgroundImage = createBackgroundImage(getWidth(), getHeight());
        mHero = createHero();
        mTreasure = createTreasure();
        mMonsters = createMonsters();
        mTraps = createTraps();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBackgroundImage, 0, 0, null);
        drawFloors(canvas);
        drawMonsters(canvas);
        drawTraps(canvas);
        mTreasure.draw(canvas);
        mHero.draw(canvas);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDialogEventListener = null;
    }

    private Bitmap createBackgroundImage(int width, int height) {
        return Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(getResources(), R.drawable.scene_background),
                width,
                height,
                true
        );
    }

    private Floor[][] createFloors(int globalSize) {
        int floorSize = (globalSize - PADDING_MIN_SIZE * 2) / FLOORS_ROW_COUNT;
        int paddingTop = (getHeight() - floorSize * FLOORS_ROW_COUNT) / 2;
        int paddingLeft = (getWidth() - floorSize * FLOORS_ROW_COUNT) / 2;
        int coordXValue = paddingLeft;
        int coordYValue = paddingTop;

        Floor[][] floors = new Floor[FLOORS_ROW_COUNT][FLOORS_ROW_COUNT];

        boolean[][] isWalls = DungeonGenerator.generateWallsMap(mDungeonConfig, FLOORS_ROW_COUNT);

        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                floors[i][j] = new Floor(
                        coordXValue,
                        coordYValue,
                        coordXValue + floorSize,
                        coordYValue + floorSize,
                        getResources(),
                        isWalls[i][j]
                );

                coordXValue += floorSize;
            }

            coordXValue = paddingLeft;
            coordYValue += floorSize;
        }

        return floors;
    }

    private Hero createHero() {
        int heroRowPos = mDungeonConfig.getHeroPosition().getRowPosition();
        int heroColPos = mDungeonConfig.getHeroPosition().getColumnPosition();

        Floor startFloor = mFloors[heroRowPos][heroColPos];

        return new Hero(
                startFloor.getLeft(),
                startFloor.getTop(),
                startFloor.getRight(),
                startFloor.getBottom(),
                getResources(),
                mDungeonConfig.getHeroPosition()
        );
    }

    private Treasure createTreasure() {
        int treasureRowPos = mDungeonConfig.getTreasurePosition().getRowPosition();
        int treasureColPos = mDungeonConfig.getTreasurePosition().getColumnPosition();

        Floor startFloor = mFloors[treasureRowPos][treasureColPos];
        startFloor.setTreasure(true);

        return new Treasure(
                startFloor.getLeft(),
                startFloor.getTop(),
                startFloor.getRight(),
                startFloor.getBottom(),
                getResources()
        );
    }

    private Monster[] createMonsters() {
        int monstersCount = mDungeonConfig.getMonstersPosition().length;

        Monster[] monsters = new Monster[monstersCount];

        for (int i = 0; i < monstersCount; i++) {
            PositionPair monsterPosition = mDungeonConfig.getMonstersPosition()[i];
            Floor startFloor = mFloors[monsterPosition.getRowPosition()][monsterPosition.getColumnPosition()];

            monsters[i] = new Monster(
                    startFloor.getLeft(),
                    startFloor.getTop(),
                    startFloor.getRight(),
                    startFloor.getBottom(),
                    getResources()
            );

            startFloor.setMonster(true);
        }

        return monsters;
    }

    private TrapType convertToTrapType(int type) {
        switch (type) {
            case 0:
                return TrapType.LEFT;
            case 1:
                return TrapType.TOP;
            case 2:
                return TrapType.RIGHT;
            case 3:
                return TrapType.BOTTOM;
            default:
                return null;
        }
    }

    private int getLeftDelta(TrapType trapType) {
        return trapType == TrapType.LEFT ? -Trap.DELTA
                : trapType == TrapType.RIGHT ? Trap.DELTA
                : 0;
    }

    private int getTopDelta(TrapType trapType) {
        return trapType == TrapType.TOP ? -Trap.DELTA
                : trapType == TrapType.BOTTOM ? Trap.DELTA
                : 0;
    }

    private Trap[] createTraps() {
        int trapsCount = mDungeonConfig.getTrapConfigs().length;

        Trap[] traps = new Trap[trapsCount];

        for (int i = 0; i < trapsCount; i++) {
            TrapConfig trapConfig = mDungeonConfig.getTrapConfigs()[i];
            PositionPair trapPosition = trapConfig.getTrapPosition();

            Floor startFloor = mFloors[trapPosition.getRowPosition()][trapPosition.getColumnPosition()];
            TrapType trapType = convertToTrapType(trapConfig.getTrapType());

            traps[i] = new Trap(
                    startFloor.getLeft() + getLeftDelta(trapType),
                    startFloor.getTop() + getTopDelta(trapType),
                    startFloor.getRight() + getLeftDelta(trapType),
                    startFloor.getBottom() + getTopDelta(trapType),
                    getResources(),
                    trapType
            );

            startFloor.setTrap(traps[i]);
        }

        return traps;
    }

    private void drawFloors(Canvas canvas) {
        for (int i = 0; i < FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < FLOORS_ROW_COUNT; j++) {
                mFloors[i][j].draw(canvas);
            }
        }
    }

    private void drawMonsters(Canvas canvas) {
        for (Monster monster : mMonsters) {
            monster.draw(canvas);
        }
    }

    private void drawTraps(Canvas canvas) {
        for (Trap trap : mTraps) {
            trap.draw(canvas);
        }
    }

    private HeroActions makeHeroMoveActions(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        return new HeroActions(
                new MoveAction() {
                    @Override
                    public void moveCallback() {
                        invalidate();
                    }
                },
                onMoveEndAction,
                new MoveAction() {
                    @Override
                    public void moveCallback() {
                        mDialogEventListener.showEndgameDialog(R.string.trap_message);
                    }
                },
                new MoveAction() {
                    @Override
                    public void moveCallback() {
                        mDialogEventListener.showEndgameDialog(R.string.monster_message);
                    }
                },
                new MoveAction() {
                    @Override
                    public void moveCallback() {
                        mDialogEventListener.showEndgameDialog(R.string.treasure_message);
                    }
                },
                dodgeAction
        );
    }

    public void moveHeroUp(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mHero.moveUp(mFloors, makeHeroMoveActions(onMoveEndAction, dodgeAction));
    }

    public void moveHeroLeft(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mHero.moveLeft(mFloors, makeHeroMoveActions(onMoveEndAction, dodgeAction));
    }

    public void moveHeroRight(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mHero.moveRight(mFloors, makeHeroMoveActions(onMoveEndAction, dodgeAction));
    }

    public void moveHeroDown(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mHero.moveDown(mFloors, makeHeroMoveActions(onMoveEndAction, dodgeAction));
    }

    public void changeHeroDirection(HeroDirection heroDirection) {
        mHero.changeDirection(heroDirection, new MoveAction() {
            @Override
            public void moveCallback() {
                invalidate();
            }
        });
    }

    public void restartGame() {
        mHero.reset(mFloors);
        invalidate();
    }

    public interface MoveAction {
        void moveCallback();
    }

    public interface DodgeAction {
        boolean isDodge(TrapType trapType);
    }
}
