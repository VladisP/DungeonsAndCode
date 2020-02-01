package ru.iu9.game.dungeonsandcode.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;

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

    public void moveHeroUp(HeroMoveAction onMoveEndAction) {
        mDungeonView.moveHeroUp(onMoveEndAction);
    }

    public void moveHeroLeft(HeroMoveAction onMoveEndAction) {
        mDungeonView.moveHeroLeft(onMoveEndAction);
    }

    public void moveHeroRight(HeroMoveAction onMoveEndAction) {
        mDungeonView.moveHeroRight(onMoveEndAction);
    }

    public void moveHeroDown(HeroMoveAction onMoveEndAction) {
        mDungeonView.moveHeroDown(onMoveEndAction);
    }
}
