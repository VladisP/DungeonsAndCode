package ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.DodgeAction;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class HeroActions {

    private MoveAction mOnMoveAction;
    private MoveAction mOnMoveEndAction;
    private MoveAction mOnMoveToTrapAction;
    private MoveAction mOnMoveToMonsterAction;
    private MoveAction mOnMoveToTreasureAction;
    private MoveAction mOnMoveToWallAction;
    private MoveAction mOnMoveToLavaAction;
    private DodgeAction mDodgeAction;

    public HeroActions(
            MoveAction onMoveAction,
            MoveAction onMoveEndAction,
            MoveAction onMoveToTrapAction,
            MoveAction onMoveToMonsterAction,
            MoveAction onMoveToTreasureAction,
            MoveAction onMoveToWallAction,
            MoveAction onMoveToLavaAction,
            DodgeAction dodgeAction
    ) {
        mOnMoveAction = onMoveAction;
        mOnMoveEndAction = onMoveEndAction;
        mOnMoveToTrapAction = onMoveToTrapAction;
        mOnMoveToMonsterAction = onMoveToMonsterAction;
        mOnMoveToTreasureAction = onMoveToTreasureAction;
        mOnMoveToWallAction = onMoveToWallAction;
        mOnMoveToLavaAction = onMoveToLavaAction;
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

    public void moveToMonsterAction() {
        mOnMoveToMonsterAction.moveCallback();
    }

    public void moveToTreasureAction() {
        mOnMoveToTreasureAction.moveCallback();
    }

    public void moveToWallAction() {
        mOnMoveToWallAction.moveCallback();
    }

    public void moveToLavaAction() {
        mOnMoveToLavaAction.moveCallback();
    }

    public boolean isDodge(TrapType trapType) {
        return mDodgeAction.isDodge(trapType);
    }
}
