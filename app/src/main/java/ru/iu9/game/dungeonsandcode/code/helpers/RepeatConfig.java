package ru.iu9.game.dungeonsandcode.code.helpers;

import java.util.List;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.code.CodeFragment.InterpreterActionListener;

public class RepeatConfig {

    private int mRepNum;
    private int mNestLevel;
    private int mStartLine;
    private List<CodeLine> mProgram;
    private HeroMoveListener mMoveListener;
    private InterpreterActionListener mInterpreterActionListener;

    public RepeatConfig(
            int repNum,
            int nestLevel,
            int startLine,
            List<CodeLine> program,
            HeroMoveListener moveListener,
            InterpreterActionListener interpreterActionListener
    ) {
        mRepNum = repNum;
        mNestLevel = nestLevel;
        mStartLine = startLine;
        mProgram = program;
        mMoveListener = moveListener;
        mInterpreterActionListener = interpreterActionListener;
    }

    public int getRepNum() {
        return mRepNum;
    }

    public int getNestLevel() {
        return mNestLevel;
    }

    public int getStartLine() {
        return mStartLine;
    }

    public List<CodeLine> getProgram() {
        return mProgram;
    }

    public HeroMoveListener getMoveListener() {
        return mMoveListener;
    }

    public InterpreterActionListener getInterpreterActionListener() {
        return mInterpreterActionListener;
    }
}
