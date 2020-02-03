package ru.iu9.game.dungeonsandcode.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.iu9.game.dungeonsandcode.DncApplication;
import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.code.CodeFragment;
import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;
import ru.iu9.game.dungeonsandcode.dungeon.DungeonFragment;
import ru.iu9.game.dungeonsandcode.dungeon.DungeonGenerator;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;

import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.HeroMoveAction;

public class GameActivity extends AppCompatActivity implements CodeFragment.HeroMoveListener {

    private DungeonConfig mDungeonConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        String json = DncApplication.from(this).getJsonRepo().getJsonByIndex(0);
        mDungeonConfig = DungeonGenerator.generateConfig(this, json);

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

    public DungeonConfig getDungeonConfig() {
        return mDungeonConfig;
    }

    @Override
    public void moveUp(HeroMoveAction onMoveEndAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroUp(onMoveEndAction);
        }
    }

    @Override
    public void moveLeft(HeroMoveAction onMoveEndAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroLeft(onMoveEndAction);
        }
    }

    @Override
    public void moveRight(HeroMoveAction onMoveEndAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroRight(onMoveEndAction);
        }
    }

    @Override
    public void moveDown(HeroMoveAction onMoveEndAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroDown(onMoveEndAction);
        }
    }

    @Override
    public void changeDirection(HeroDirection heroDirection) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.changeHeroDirection(heroDirection);
        }
    }
}
