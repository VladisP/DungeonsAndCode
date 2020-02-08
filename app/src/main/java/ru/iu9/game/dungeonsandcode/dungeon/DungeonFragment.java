package ru.iu9.game.dungeonsandcode.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.*;

public class DungeonFragment extends Fragment {

    private DungeonView mDungeonView;

    public static DungeonFragment newInstance() {
        return new DungeonFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDungeonView = (DungeonView) view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDungeonView = null;
    }

    public void moveHeroUp(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mDungeonView.moveHeroUp(onMoveEndAction, dodgeAction);
    }

    public void moveHeroLeft(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mDungeonView.moveHeroLeft(onMoveEndAction, dodgeAction);
    }

    public void moveHeroRight(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mDungeonView.moveHeroRight(onMoveEndAction, dodgeAction);
    }

    public void moveHeroDown(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        mDungeonView.moveHeroDown(onMoveEndAction, dodgeAction);
    }

    public void changeHeroDirection(HeroDirection heroDirection) {
        mDungeonView.changeHeroDirection(heroDirection);
    }

    public void restartGame() {
        mDungeonView.restartGame();
    }
}
