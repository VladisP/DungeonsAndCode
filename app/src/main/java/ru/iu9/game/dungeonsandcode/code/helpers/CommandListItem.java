package ru.iu9.game.dungeonsandcode.code.helpers;

public class CommandListItem {

    private int mImageId;
    private String mCommandText;

    public CommandListItem(int imageId, String commandText) {
        this.mImageId = imageId;
        this.mCommandText = commandText;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getCommandText() {
        return mCommandText;
    }
}
