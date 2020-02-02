package ru.iu9.game.dungeonsandcode.code;

import java.util.List;

import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.*;
import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.HeroMoveAction;

class Interpreter {

    private static HeroDirection sHeroDirection = HeroDirection.TOP;
    private static int sCurrentLine = 0;

    static void run(final List<CodeLine> program, final HeroMoveListener heroMoveListener, final InterpreterActionListener interpreterActionListener) {
        if (sCurrentLine >= program.size()) {
            sCurrentLine = 0;
            interpreterActionListener.onInterpretationFinished();
            return;
        }

        CodeLine currentLine = program.get(sCurrentLine);

        switch (currentLine.getCommandType()) {
            case MOVE:
                move(heroMoveListener, new HeroMoveAction() {
                    @Override
                    public void moveCallback() {
                        onCommandEndAction(program, heroMoveListener, interpreterActionListener);
                    }
                });
                break;
            case TURN_LEFT:
                turnLeft(heroMoveListener);
                onCommandEndAction(program, heroMoveListener, interpreterActionListener);
                break;
            case TURN_RIGHT:
                turnRight(heroMoveListener);
                onCommandEndAction(program, heroMoveListener, interpreterActionListener);
                break;
        }
    }

    private static void onCommandEndAction(final List<CodeLine> program, final HeroMoveListener heroMoveListener, InterpreterActionListener interpreterActionListener) {
        sCurrentLine++;
        run(program, heroMoveListener, interpreterActionListener);
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
