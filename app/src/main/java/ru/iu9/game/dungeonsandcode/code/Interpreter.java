package ru.iu9.game.dungeonsandcode.code;

import java.util.List;
import java.util.Stack;

import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.code.helpers.RepeatConfig;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.code.CodeFragment.InterpreterActionListener;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.DodgeAction;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

class Interpreter {

    private static HeroDirection sHeroDirection = HeroDirection.TOP;
    private static int sCurrentLine = 0;
    private static int sDodgeCurrentLineNum = 0;
    private static Stack<Integer> sSaveLineNumbers = new Stack<>();

    static void run(
            final List<CodeLine> program,
            final HeroMoveListener heroMoveListener,
            final DodgeAction dodgeAction,
            final InterpreterActionListener interpreterActionListener
    ) {
        if (sCurrentLine >= program.size()) {
            sCurrentLine = 0;
            interpreterActionListener.onInterpretationFinished();
            return;
        }

        CodeLine currentLine = program.get(sCurrentLine);

        switch (currentLine.getCommandType()) {
            case MOVE:
                move(
                        heroMoveListener,
                        new MoveAction() {
                            @Override
                            public void moveCallback() {
                                onCommandEndAction(program, heroMoveListener, dodgeAction, interpreterActionListener);
                            }
                        },
                        dodgeAction
                );
                break;
            case TURN_LEFT:
                turnLeft(heroMoveListener);
                onCommandEndAction(program, heroMoveListener, dodgeAction, interpreterActionListener);
                break;
            case TURN_RIGHT:
                turnRight(heroMoveListener);
                onCommandEndAction(program, heroMoveListener, dodgeAction, interpreterActionListener);
                break;
            case REPEAT:
                RepeatConfig mainConfig = new RepeatConfig(
                        currentLine.getRepNum(),
                        currentLine.getNestingLevel() + 1,
                        ++sCurrentLine,
                        program,
                        heroMoveListener,
                        dodgeAction,
                        interpreterActionListener
                );

                repeat(mainConfig, new Stack<RepeatConfig>());
                break;
            case SUBROUTINE:
                sSaveLineNumbers.push(sCurrentLine + 1);
                sCurrentLine = 0;
                interpreterActionListener.onSubroutineCall(null, null);
                break;
        }
    }

    static void restoreCurrentLine() {
        sCurrentLine = sSaveLineNumbers.pop();
    }

    static void reset() {
        sHeroDirection = HeroDirection.TOP;
        sCurrentLine = 0;
        sDodgeCurrentLineNum = 0;
        sSaveLineNumbers.clear();
    }

    private static void onCommandEndAction(
            final List<CodeLine> program,
            final HeroMoveListener heroMoveListener,
            final DodgeAction dodgeAction,
            InterpreterActionListener interpreterActionListener
    ) {
        sCurrentLine++;

        try {
            run(program, heroMoveListener, dodgeAction, interpreterActionListener);
        } catch (StackOverflowError error) {
            interpreterActionListener.onInfinityRecursion();
        }
    }

    private static void move(HeroMoveListener heroMoveListener, MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        switch (sHeroDirection) {
            case TOP:
                heroMoveListener.moveUp(onMoveEndAction, dodgeAction);
                break;
            case RIGHT:
                heroMoveListener.moveRight(onMoveEndAction, dodgeAction);
                break;
            case BOTTOM:
                heroMoveListener.moveDown(onMoveEndAction, dodgeAction);
                break;
            case LEFT:
                heroMoveListener.moveLeft(onMoveEndAction, dodgeAction);
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

    static void repeat(final RepeatConfig mainConfig, final Stack<RepeatConfig> outerConfigs) {
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
                                mainConfig.getDodgeAction(),
                                mainConfig.getInterpreterActionListener()
                        ),
                        outerConfigs
                );
            } else if (mainConfig.getNestLevel() == 1) {
                run(
                        mainConfig.getProgram(),
                        mainConfig.getMoveListener(),
                        mainConfig.getDodgeAction(),
                        mainConfig.getInterpreterActionListener()
                );
            } else if (mainConfig.getNestLevel() > 1) {
                RepeatConfig outerConfig = outerConfigs.pop();

                repeat(
                        new RepeatConfig(
                                outerConfig.getRepNum(),
                                outerConfig.getNestLevel(),
                                outerConfig.getStartLine(),
                                outerConfig.getProgram(),
                                outerConfig.getMoveListener(),
                                outerConfig.getDodgeAction(),
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
                move(
                        mainConfig.getMoveListener(),
                        new MoveAction() {
                            @Override
                            public void moveCallback() {
                                onRepeatCommandEndAction(mainConfig, outerConfigs);
                            }
                        },
                        mainConfig.getDodgeAction()
                );
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
                                mainConfig.getDodgeAction(),
                                mainConfig.getInterpreterActionListener()
                        ),
                        outerConfigs
                );
                break;
            case SUBROUTINE:
                sSaveLineNumbers.push(sCurrentLine + 1);
                sCurrentLine = 0;
                mainConfig.getInterpreterActionListener().onSubroutineCall(mainConfig, outerConfigs);
                break;
        }
    }

    private static void onRepeatCommandEndAction(final RepeatConfig mainConfig, final Stack<RepeatConfig> outerConfigs) {
        sCurrentLine++;

        try {
            repeat(mainConfig, outerConfigs);
        } catch (StackOverflowError error) {
            mainConfig.getInterpreterActionListener().onInfinityRecursion();
        }
    }

    private static CodeLine peekDodgeCodeLine(List<CodeLine> dodgeScript) {
        return sDodgeCurrentLineNum >= dodgeScript.size() ? null : dodgeScript.get(sDodgeCurrentLineNum);
    }

    static boolean isDodged(TrapType trapType, List<CodeLine> dodgeScript) {
        sDodgeCurrentLineNum = 0;

        return isDodgedHelper(trapType, dodgeScript);
    }

    private static boolean isDodgedHelper(TrapType trapType, List<CodeLine> dodgeScript) {
        CodeLine dodgeLine = peekDodgeCodeLine(dodgeScript);

        if (dodgeLine == null) {
            return false;
        }

        if (Parser.isPrimaryDodgeCommand(dodgeLine.getCommandType())) {
            return isDodged(trapType, dodgeLine.getCommandType());
        }

        if (dodgeLine.getTrapType() == trapType) {
            return checkCondBody(trapType, dodgeScript);
        }

        return checkScriptTail(trapType, dodgeScript);
    }

    private static boolean isDodged(TrapType trapType, CommandType commandType) {
        return (trapType == TrapType.LEFT && commandType == CommandType.DODGE_LEFT) ||
                (trapType == TrapType.TOP && commandType == CommandType.DODGE_TOP) ||
                (trapType == TrapType.RIGHT && commandType == CommandType.DODGE_RIGHT) ||
                (trapType == TrapType.BOTTOM && commandType == CommandType.DODGE_BOTTOM);
    }

    private static boolean checkCondBody(TrapType trapType, List<CodeLine> dodgeScript) {
        sDodgeCurrentLineNum++;

        CodeLine condBodyFirstLine = peekDodgeCodeLine(dodgeScript);

        return condBodyFirstLine != null &&
                condBodyFirstLine.getNestingLevel() == 1 &&
                isDodged(trapType, condBodyFirstLine.getCommandType());
    }

    private static boolean checkScriptTail(TrapType trapType, List<CodeLine> dodgeScript) {
        sDodgeCurrentLineNum++;
        CodeLine dodgeLine = peekDodgeCodeLine(dodgeScript);

        while (dodgeLine != null && dodgeLine.getNestingLevel() == 1) {
            sDodgeCurrentLineNum++;
            dodgeLine = peekDodgeCodeLine(dodgeScript);
        }

        if (dodgeLine == null) {
            return false;
        }

        if (dodgeLine.getCommandType() == CommandType.ELIF && dodgeLine.getTrapType() == trapType) {
            return checkCondBody(trapType, dodgeScript);
        } else if (dodgeLine.getCommandType() == CommandType.ELIF) {
            return checkScriptTail(trapType, dodgeScript);
        }

        if (dodgeLine.getCommandType() == CommandType.ELSE) {
            return checkCondBody(trapType, dodgeScript);
        }

        return isDodgedHelper(trapType, dodgeScript);
    }
}
