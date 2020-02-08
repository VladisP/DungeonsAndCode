package ru.iu9.game.dungeonsandcode.code.helpers;

import java.util.List;
import java.util.Stack;

public class Frame {

    private List<CodeLine> mCurrentProgram;
    private RepeatConfig mMainConfig;
    private Stack<RepeatConfig> mOuterConfigs;

    public Frame(List<CodeLine> currentProgram) {
        this(currentProgram, null, null);
    }

    private Frame(
            List<CodeLine> currentProgram,
            RepeatConfig mainConfig,
            Stack<RepeatConfig> outerConfigs
    ) {
        mCurrentProgram = currentProgram;
        mMainConfig = mainConfig;
        mOuterConfigs = outerConfigs;
    }

    public List<CodeLine> getCurrentProgram() {
        return mCurrentProgram;
    }

    public RepeatConfig getMainConfig() {
        return mMainConfig;
    }

    public Stack<RepeatConfig> getOuterConfigs() {
        return mOuterConfigs;
    }

    public void setRepeatConfigs(RepeatConfig mainConfig, Stack<RepeatConfig> outerConfigs) {
        mMainConfig = mainConfig;
        mOuterConfigs = outerConfigs;
    }
}
