package ru.iu9.game.dungeonsandcode.constructor.helpers;

public interface ConstructorEventListener {
    void switchPartType(ConstructorPartType newPartType);

    void removeLastAction();

    void removeAll();

    void showErrorMessage(int msgId);
}
