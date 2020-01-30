package ru.iu9.game.dungeonsandcode.code.helpers;

public interface CodeEditor {
    void addCodeLine(String codeLine);

    void removeLastLine();

    void runProgram();
}
