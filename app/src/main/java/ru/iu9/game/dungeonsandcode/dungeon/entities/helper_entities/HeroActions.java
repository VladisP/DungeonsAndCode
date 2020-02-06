package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.DodgeAction;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class HeroActions {

    private MoveAction mOnMoveAction;
    private MoveAction mOnMoveEndAction;
    private MoveAction mOnMoveToTrapAction;
    private DodgeAction mDodgeAction;

    public HeroActions(
            MoveAction onMoveAction,
            MoveAction onMoveEndAction,
            MoveAction onMoveToTrapAction,
            DodgeAction dodgeAction
    ) {
        mOnMoveAction = onMoveAction;
        mOnMoveEndAction = onMoveEndAction;
        mOnMoveToTrapAction = onMoveToTrapAction;
        mDodgeAction = dodgeAction;
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

    public boolean isDodge(TrapType trapType) {
        return mDodgeAction.isDodge(trapType);
    }
}
