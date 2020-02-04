package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class HeroMoveActions {

    private MoveAction mOnMoveAction;
    private MoveAction mOnMoveEndAction;
    private MoveAction mOnMoveToTrapAction;

    public HeroMoveActions(MoveAction onMoveAction, MoveAction onMoveEndAction, MoveAction onMoveToTrapAction) {
        mOnMoveAction = onMoveAction;
        mOnMoveEndAction = onMoveEndAction;
        mOnMoveToTrapAction = onMoveToTrapAction;
    }

    public MoveAction getOnMoveAction() {
        return mOnMoveAction;
    }

    public void moveAction() {
        mOnMoveAction.moveCallback();
    }

    public void moveEndAction() {
        mOnMoveEndAction.moveCallback();
    }

    public void moveToTrapAction() {
        mOnMoveToTrapAction.moveCallback();
    }
}
