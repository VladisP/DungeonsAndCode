package ru.iu9.game.dungeonsandcode.code.helpers;

public interface CodeEditor {
    void addCodeLine(CodeLine codeLine);

    void removeLastLine();

    void runProgram();

    void deleteProgram();

    int getNestingLevel();

    void incNestingLevel();

    void decNestingLevel();
}
