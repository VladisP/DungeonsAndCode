package ru.iu9.game.dungeonsandcode.code;

import java.util.List;
import java.util.Stack;

import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.code.helpers.RepeatConfig;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.*;
import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

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
                move(heroMoveListener, new MoveAction() {
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
            case REPEAT:
                RepeatConfig mainConfig = new RepeatConfig(
                        currentLine.getRepNum(),
                        currentLine.getNestingLevel() + 1,
                        ++sCurrentLine,
                        program,
                        heroMoveListener,
                        interpreterActionListener
                );

                repeat(mainConfig, new Stack<RepeatConfig>());
                break;
        }
    }

    static void reset() {
        sHeroDirection = HeroDirection.TOP;
        sCurrentLine = 0;
    }

    private static void onCommandEndAction(final List<CodeLine> program, final HeroMoveListener heroMoveListener, InterpreterActionListener interpreterActionListener) {
        sCurrentLine++;
        run(program, heroMoveListener, interpreterActionListener);
    }

    private static void move(HeroMoveListener heroMoveListener, MoveAction onMoveEndAction) {
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

    private static void repeat(final RepeatConfig mainConfig, final Stack<RepeatConfig> outerConfigs) {
        if (
                sCurrentLine >= mainConfig.getProgram().size() ||
                        mainConfig.getProgram().get(sCurrentLine).getNestingLevel() < mainConfig.getNestLevel()
        ) {
            if (mainConfig.getRepNum() > 1) {
                sCurrentLine = mainConfig.getStartLine();

                repeat(
                        new RepeatConfig(
                                mainConfig.getRepNum() - 1,
                                mainConfig.getNestLevel(),
                                mainConfig.getStartLine(),
                                mainConfig.getProgram(),
                                mainConfig.getMoveListener(),
                                mainConfig.getInterpreterActionListener()
                        ),
                        outerConfigs
                );
            } else if (mainConfig.getNestLevel() == 1) {
                run(mainConfig.getProgram(), mainConfig.getMoveListener(), mainConfig.getInterpreterActionListener());
            } else if (mainConfig.getNestLevel() > 1) {
                RepeatConfig outerConfig = outerConfigs.pop();

                repeat(
                        new RepeatConfig(
                                outerConfig.getRepNum(),
                                outerConfig.getNestLevel(),
                                outerConfig.getStartLine(),
                                outerConfig.getProgram(),
                                outerConfig.getMoveListener(),
                                outerConfig.getInterpreterActionListener()
                        ),
                        outerConfigs
                );
            }

            return;
        }

        CodeLine currentLine = mainConfig.getProgram().get(sCurrentLine);

        switch (currentLine.getCommandType()) {
            case MOVE:
                move(mainConfig.getMoveListener(), new MoveAction() {
                    @Override
                    public void moveCallback() {
                        onRepeatCommandEndAction(mainConfig, outerConfigs);
                    }
                });
                break;
            case TURN_LEFT:
                turnLeft(mainConfig.getMoveListener());
                onRepeatCommandEndAction(mainConfig, outerConfigs);
                break;
            case TURN_RIGHT:
                turnRight(mainConfig.getMoveListener());
                onRepeatCommandEndAction(mainConfig, outerConfigs);
                break;
            case REPEAT:
                outerConfigs.push(mainConfig);

                repeat(
                        new RepeatConfig(
                                currentLine.getRepNum(),
                                mainConfig.getNestLevel() + 1,
                                ++sCurrentLine,
                                mainConfig.getProgram(),
                                mainConfig.getMoveListener(),
                                mainConfig.getInterpreterActionListener()
                        ),
                        outerConfigs
                );
                break;
        }
    }

    private static void onRepeatCommandEndAction(final RepeatConfig mainConfig, final Stack<RepeatConfig> outerConfigs) {
        sCurrentLine++;
        repeat(mainConfig, outerConfigs);
    }
}
