package ru.iu9.game.dungeonsandcode.code;

import java.util.List;

import ru.iu9.game.dungeonsandcode.code.helpers.CodeLine;
import ru.iu9.game.dungeonsandcode.code.helpers.CommandType;

class Parser {

    private static int currentLineNum = 0;
    private static int currentNestLevel = 0;

    static boolean parseMain(List<CodeLine> program) {
        currentLineNum = 0;
        currentNestLevel = 0;

        return parseProgram(program, false);
    }

    private static boolean parseProgram(List<CodeLine> program, boolean isRepeatBody) {
        if (currentLineNum >= program.size()) {
            return true;
        }

        CodeLine codeLine = program.get(currentLineNum);

        if (codeLine.getNestingLevel() != currentNestLevel) {
            return isRepeatBody && codeLine.getNestingLevel() < currentNestLevel;
        }

        if (isPrimaryMainCommand(codeLine.getCommandType())) {
            currentLineNum++;

            return parseProgram(program, isRepeatBody);
        }

        if (codeLine.getCommandType() == CommandType.REPEAT) {
            if (!parseRepeat(program)) {
                return false;
            }

            return parseProgram(program, isRepeatBody);
        }

        return false;
    }

    private static boolean isPrimaryMainCommand(CommandType type) {
        return type == CommandType.MOVE || type == CommandType.TURN_LEFT || type == CommandType.TURN_RIGHT;
    }

    private static boolean parseRepeat(List<CodeLine> program) {
        currentLineNum++;
        currentNestLevel++;

        boolean isCorrect = parseProgram(program, true);

        currentNestLevel--;

        return isCorrect;
    }
}
