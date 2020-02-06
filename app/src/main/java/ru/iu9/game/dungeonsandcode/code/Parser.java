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

    static boolean parseDodgeScript(List<CodeLine> script) {
        currentLineNum = 0;
        currentNestLevel = 0;

        return parseScript(script);
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

    private static boolean parseScript(List<CodeLine> script) {
        if (currentLineNum >= script.size()) {
            return true;
        }

        CodeLine codeLine = script.get(currentLineNum);

        if (codeLine.getNestingLevel() != currentNestLevel) {
            return false;
        }

        if (isPrimaryDodgeCommand(codeLine.getCommandType())) {
            currentLineNum++;

            return parseScript(script);
        }

        if (codeLine.getCommandType() == CommandType.IF) {
            if (!parseCond(script)) {
                return false;
            }

            return parseScript(script);
        }

        return false;
    }

    static boolean isPrimaryDodgeCommand(CommandType type) {
        return type == CommandType.DODGE_LEFT ||
                type == CommandType.DODGE_TOP ||
                type == CommandType.DODGE_RIGHT ||
                type == CommandType.DODGE_BOTTOM;
    }

    private static boolean parseCond(List<CodeLine> script) {
        currentLineNum++;
        currentNestLevel++;

        if (!parseCondBody(script)) {
            return false;
        }

        currentNestLevel--;

        return parseElifCond(script);
    }

    private static boolean parseCondBody(List<CodeLine> script) {
        if (currentLineNum >= script.size()) {
            return true;
        }

        CodeLine codeLine = script.get(currentLineNum);

        if (codeLine.getNestingLevel() != currentNestLevel) {
            return codeLine.getNestingLevel() < currentNestLevel;
        }

        if (isPrimaryDodgeCommand(codeLine.getCommandType())) {
            currentLineNum++;

            return parseCondBody(script);
        }

        return false;
    }

    private static boolean parseElifCond(List<CodeLine> script) {
        if (currentLineNum >= script.size() || script.get(currentLineNum).getCommandType() != CommandType.ELIF) {
            return parseElseCond(script);
        }

        currentLineNum++;
        currentNestLevel++;

        if (!parseCondBody(script)) {
            return false;
        }

        currentNestLevel--;

        return parseElifCond(script);
    }

    private static boolean parseElseCond(List<CodeLine> script) {
        if (currentLineNum >= script.size() || script.get(currentLineNum).getCommandType() != CommandType.ELSE) {
            return true;
        }

        currentLineNum++;
        currentNestLevel++;

        boolean isCorrectBody = parseCondBody(script);

        currentNestLevel--;

        return isCorrectBody;
    }
}
