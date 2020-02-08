package ru.iu9.game.dungeonsandcode.code.helpers;

import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.TrapType;

public class CodeLine {

    private static final int INDENT_VALUE = 4;

    private static final String COMMAND_MOVE_TEXT = "move()";
    private static final String COMMAND_TURN_LEFT_TEXT = "turnLeft()";
    private static final String COMMAND_TURN_RIGHT_TEXT = "turnRight()";
    private static final String COMMAND_REPEAT_TEXT = "repeat";
    private static final String COMMAND_SUBROUTINE_TEXT = "f()";
    private static final String COMMAND_IF_TEXT = "if";
    private static final String COMMAND_ELIF_TEXT = "elif";
    private static final String COMMAND_ELSE_TEXT = "else";
    private static final String COMMAND_DODGE_LEFT_TEXT = "dodgeLeft()";
    private static final String COMMAND_DODGE_TOP_TEXT = "dodgeTop()";
    private static final String COMMAND_DODGE_RIGHT_TEXT = "dodgeRight()";
    private static final String COMMAND_DODGE_BOTTOM_TEXT = "dodgeBottom()";

    private static final String IDENT_TRAP_LEFT = "trapLeft";
    private static final String IDENT_TRAP_TOP = "trapTop";
    private static final String IDENT_TRAP_RIGHT = "trapRight";
    private static final String IDENT_TRAP_BOTTOM = "trapBottom";

    private CommandType mCommandType;
    private int mNestingLevel;
    private int mRepNum;
    private TrapType mTrapType;
    private String mLineText;

    public CodeLine(CommandType commandType, int nestingLevel) {
        this(commandType, nestingLevel, 0, TrapType.LEFT);
    }

    public CodeLine(CommandType commandType, int nestingLevel, int repNum) {
        this(commandType, nestingLevel, repNum, TrapType.LEFT);
    }

    public CodeLine(CommandType commandType, int nestingLevel, TrapType trapType) {
        this(commandType, nestingLevel, 0, trapType);
    }

    private CodeLine(CommandType commandType, int nestingLevel, int repNum, TrapType trapType) {
        mCommandType = commandType;
        mNestingLevel = nestingLevel;
        mRepNum = repNum;
        mTrapType = trapType;
        mLineText = createLineText(commandType, nestingLevel, repNum, trapType);
    }

    private String typeToString(TrapType trapType) {
        switch (trapType) {
            case LEFT:
                return IDENT_TRAP_LEFT;
            case TOP:
                return IDENT_TRAP_TOP;
            case RIGHT:
                return IDENT_TRAP_RIGHT;
            case BOTTOM:
            default:
                return IDENT_TRAP_BOTTOM;
        }
    }

    private String createLineText(CommandType commandType, int nestingLevel, int repNum, TrapType trapType) {
        StringBuilder lineText = new StringBuilder();

        for (int i = 0; i < INDENT_VALUE * nestingLevel; i++) {
            lineText.append(" ");
        }

        switch (commandType) {
            case MOVE:
                lineText.append(COMMAND_MOVE_TEXT);
                break;
            case TURN_LEFT:
                lineText.append(COMMAND_TURN_LEFT_TEXT);
                break;
            case TURN_RIGHT:
                lineText.append(COMMAND_TURN_RIGHT_TEXT);
                break;
            case REPEAT:
                lineText.append(COMMAND_REPEAT_TEXT).append(" ").append(repNum).append(":");
                break;
            case SUBROUTINE:
                lineText.append(COMMAND_SUBROUTINE_TEXT);
                break;
            case IF:
                lineText.append(COMMAND_IF_TEXT).append(" (").append(typeToString(trapType)).append("):");
                break;
            case ELIF:
                lineText.append(COMMAND_ELIF_TEXT).append(" (").append(typeToString(trapType)).append("):");
                break;
            case ELSE:
                lineText.append(COMMAND_ELSE_TEXT).append(":");
                break;
            case DODGE_LEFT:
                lineText.append(COMMAND_DODGE_LEFT_TEXT);
                break;
            case DODGE_TOP:
                lineText.append(COMMAND_DODGE_TOP_TEXT);
                break;
            case DODGE_RIGHT:
                lineText.append(COMMAND_DODGE_RIGHT_TEXT);
                break;
            case DODGE_BOTTOM:
                lineText.append(COMMAND_DODGE_BOTTOM_TEXT);
                break;
        }

        return lineText.toString();
    }

    public CommandType getCommandType() {
        return mCommandType;
    }

    public int getNestingLevel() {
        return mNestingLevel;
    }

    public int getRepNum() {
        return mRepNum;
    }

    public TrapType getTrapType() {
        return mTrapType;
    }

    public String getLineText() {
        return mLineText;
    }
}
