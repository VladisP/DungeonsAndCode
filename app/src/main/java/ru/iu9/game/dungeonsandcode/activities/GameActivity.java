package ru.iu9.game.dungeonsandcode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.dungeon.DungeonFragment;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment dungeonFragment = fragmentManager.findFragmentById(R.id.dungeon_fragment_container);
        Fragment codeFragment = fragmentManager.findFragmentById(R.id.code_fragment_container);

        if (dungeonFragment == null) {
            dungeonFragment = DungeonFragment.newInstance();

            fragmentManager.beginTransaction()
                    .add(R.id.dungeon_fragment_container, dungeonFragment)
                    .commit();
        }
    }
}
