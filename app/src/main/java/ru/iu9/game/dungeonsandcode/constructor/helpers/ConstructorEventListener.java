package ru.iu9.game.dungeonsandcode.constructor.helpers;

public interface ConstructorEventListener {
    void switchPartType(ConstructorPartType newPartType);

    void showErrorMessage(int msgId);
}
