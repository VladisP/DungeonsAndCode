package ru.iu9.game.dungeonsandcode.dungeon;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.iu9.game.dungeonsandcode.R;

public class DungeonFragment extends Fragment {

    public static DungeonFragment newInstance() {
        return new DungeonFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dungeon, container, false);
    }
}
