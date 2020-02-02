package ru.iu9.game.dungeonsandcode.code.helpers;

public class CommandListItem {

    private int mImageId;
    private CommandType mType;

    public CommandListItem(int imageId, CommandType type) {
        mImageId = imageId;
        mType = type;
    }

    public int getImageId() {
        return mImageId;
    }

    public CommandType getType() {
        return mType;
    }
}
