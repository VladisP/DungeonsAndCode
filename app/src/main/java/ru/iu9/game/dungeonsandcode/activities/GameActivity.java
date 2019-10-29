package ru.iu9.game.dungeonsandcode.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.CodeFragment;
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
            addFragment(dungeonFragment, R.id.dungeon_fragment_container);
        }

        if (codeFragment == null) {
            codeFragment = CodeFragment.newInstance();
            addFragment(codeFragment, R.id.code_fragment_container);
        }
    }

    private void addFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }
}
