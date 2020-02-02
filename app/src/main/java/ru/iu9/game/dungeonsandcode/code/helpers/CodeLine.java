package ru.iu9.game.dungeonsandcode.code.helpers;

public class CodeLine {

    private static final int INDENT_VALUE = 4;
    private static final String COMMAND_MOVE_TEXT = "move()";
    private static final String COMMAND_TURN_LEFT_TEXT = "turnLeft()";
    private static final String COMMAND_TURN_RIGHT_TEXT = "turnRight()";
    private static final String COMMAND_REPEAT_TEXT = "repeat";

    private CommandType mCommandType;
    private int mNestingLevel;
    private String mLineText;

    public CodeLine(CommandType commandType, int nestingLevel) {
        this(commandType, nestingLevel, 0);
    }

    public CodeLine(CommandType commandType, int nestingLevel, int repNum) {
        mCommandType = commandType;
        mNestingLevel = nestingLevel;
        mLineText = createLineText(commandType, nestingLevel, repNum);
    }

    private String createLineText(CommandType commandType, int nestingLevel, int repNum) {
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
        }

        return lineText.toString();
    }

    public CommandType getCommandType() {
        return mCommandType;
    }

    public int getNestingLevel() {
        return mNestingLevel;
    }

    public String getLineText() {
        return mLineText;
    }
}
