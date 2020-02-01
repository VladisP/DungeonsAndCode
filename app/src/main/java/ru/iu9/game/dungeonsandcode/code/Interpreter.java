package ru.iu9.game.dungeonsandcode.code;

import java.util.List;

import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.HeroMoveAction;

class Interpreter {

    static final String COMMAND_MOVE = "move()";
    static final String COMMAND_TURN_LEFT = "turnLeft()";
    static final String COMMAND_TURN_RIGHT = "turnRight()";

    private static HeroDirection sHeroDirection = HeroDirection.TOP;
    private static int sCurrentCommand = 0;

    static void run(final List<String> program, final HeroMoveListener heroMoveListener) {
        String currentCommand = program.get(sCurrentCommand);

        switch (currentCommand) {
            case COMMAND_MOVE:
                move(heroMoveListener, new HeroMoveAction() {
                    @Override
                    public void moveCallback() {
                        onCommandEndAction(program, heroMoveListener);
                    }
                });
                break;
            case COMMAND_TURN_LEFT:
                turnLeft(heroMoveListener);
                onCommandEndAction(program, heroMoveListener);
                break;
            case COMMAND_TURN_RIGHT:
                turnRight(heroMoveListener);
                onCommandEndAction(program, heroMoveListener);
                break;
        }
    }

    private static void onCommandEndAction(final List<String> program, final HeroMoveListener heroMoveListener) {
        if (sCurrentCommand == program.size() - 1) {
            sCurrentCommand = 0;
        } else {
            sCurrentCommand++;
            run(program, heroMoveListener);
        }
    }

    private static void move(HeroMoveListener heroMoveListener, HeroMoveAction onMoveEndAction) {
        switch (sHeroDirection) {
            case TOP:
                heroMoveListener.moveUp(onMoveEndAction);
                break;
            case RIGHT:
                heroMoveListener.moveRight(onMoveEndAction);
                break;
            case BOTTOM:
                heroMoveListener.moveDown(onMoveEndAction);
                break;
            case LEFT:
                heroMoveListener.moveLeft(onMoveEndAction);
                break;
        }
    }

    private static void turnLeft(HeroMoveListener heroMoveListener) {
        switch (sHeroDirection) {
            case TOP:
                sHeroDirection = HeroDirection.LEFT;
                heroMoveListener.changeDirection(HeroDirection.LEFT);
                break;
            case RIGHT:
                sHeroDirection = HeroDirection.TOP;
                heroMoveListener.changeDirection(HeroDirection.TOP);
                break;
            case BOTTOM:
                sHeroDirection = HeroDirection.RIGHT;
                heroMoveListener.changeDirection(HeroDirection.RIGHT);
                break;
            case LEFT:
                sHeroDirection = HeroDirection.BOTTOM;
                heroMoveListener.changeDirection(HeroDirection.BOTTOM);
                break;
        }
    }

    private static void turnRight(HeroMoveListener heroMoveListener) {
        switch (sHeroDirection) {
            case TOP:
                sHeroDirection = HeroDirection.RIGHT;
                heroMoveListener.changeDirection(HeroDirection.RIGHT);
                break;
            case RIGHT:
                sHeroDirection = HeroDirection.BOTTOM;
                heroMoveListener.changeDirection(HeroDirection.BOTTOM);
                break;
            case BOTTOM:
                sHeroDirection = HeroDirection.LEFT;
                heroMoveListener.changeDirection(HeroDirection.LEFT);
                break;
            case LEFT:
                sHeroDirection = HeroDirection.TOP;
                heroMoveListener.changeDirection(HeroDirection.TOP);
                break;
        }
    }
}
