package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

public interface DialogEventListener {
    void showEndgameDialog(int msgId);

    void restartGame();

    void showErrorMessage(String msg);
}
