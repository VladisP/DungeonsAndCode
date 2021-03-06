package ru.iu9.game.dungeonsandcode.constructor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorPartType;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;

public class ConstructorFragment extends Fragment {

    private ConstructorView mConstructorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_constructor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mConstructorView = (ConstructorView) view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mConstructorView = null;
    }

    public void switchPartType(ConstructorPartType newPartType) {
        mConstructorView.setCurrentPartType(newPartType);
    }

    public void removeLastAction() {
        mConstructorView.removeLastAddedPart();
    }

    public void removeAll() {
        mConstructorView.removeAll();
    }

    public DungeonConfig getDungeonConfig() {
        return mConstructorView.getDungeonConfig();
    }
}
