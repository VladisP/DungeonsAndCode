package ru.iu9.game.dungeonsandcode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
import ru.iu9.game.dungeonsandcode.dungeon.dialog.EndgameFragment;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.DialogEventListener;
import ru.iu9.game.dungeonsandcode.repositories.JsonRepo;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.HeroMoveListener;
import static ru.iu9.game.dungeonsandcode.code.CodeFragment.newInstance;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.DodgeAction;
import static ru.iu9.game.dungeonsandcode.dungeon.DungeonView.MoveAction;

public class GameActivity extends AppCompatActivity implements HeroMoveListener, DialogEventListener {

    private static final String EXTRA_LEVEL_NUMBER = "ru.iu9.game.dungeonsandcode.level_number";
    private static final String EXTRA_IS_CUSTOM = "ru.iu9.game.dungeonsandcode.is_custom";
    private static final String DIALOG_TAG = "EndgameDialog";

    private DungeonConfig mDungeonConfig;

    static Intent newIntent(Context packageContext, int levelNumber, boolean isCustom) {
        Intent intent = new Intent(packageContext, GameActivity.class);
        intent.putExtra(EXTRA_LEVEL_NUMBER, levelNumber);
        intent.putExtra(EXTRA_IS_CUSTOM, isCustom);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        int levelNumber = getIntent().getIntExtra(EXTRA_LEVEL_NUMBER, 0);
        boolean isCustom = getIntent().getBooleanExtra(EXTRA_IS_CUSTOM, false);
        JsonRepo jsonRepo = DncApplication.from(this).getJsonRepo();

        String json = isCustom ? jsonRepo.getCustomJsonByIndex(levelNumber) : jsonRepo.getJsonByIndex(levelNumber);
        mDungeonConfig = DungeonGenerator.generateConfig(this, json);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment dungeonFragment = fragmentManager.findFragmentById(R.id.dungeon_fragment_container);
        Fragment codeFragment = fragmentManager.findFragmentById(R.id.code_fragment_container);

        if (dungeonFragment == null) {
            dungeonFragment = DungeonFragment.newInstance();
            addFragment(dungeonFragment, R.id.dungeon_fragment_container);
        }

        if (codeFragment == null) {
            codeFragment = newInstance();
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
    public void moveUp(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroUp(onMoveEndAction, dodgeAction);
        }
    }

    @Override
    public void moveLeft(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroLeft(onMoveEndAction, dodgeAction);
        }
    }

    @Override
    public void moveRight(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroRight(onMoveEndAction, dodgeAction);
        }
    }

    @Override
    public void moveDown(MoveAction onMoveEndAction, DodgeAction dodgeAction) {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.moveHeroDown(onMoveEndAction, dodgeAction);
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

    @Override
    public void showEndgameDialog(int msgId) {
        EndgameFragment endgameDialog = EndgameFragment.newInstance(msgId);
        endgameDialog.show(getSupportFragmentManager(), DIALOG_TAG);
    }

    @Override
    public void restartGame() {
        DungeonFragment dungeonFragment = (DungeonFragment) getSupportFragmentManager()
                .findFragmentById(R.id.dungeon_fragment_container);

        if (dungeonFragment != null) {
            dungeonFragment.restartGame();
        }

        CodeFragment codeFragment = (CodeFragment) getSupportFragmentManager()
                .findFragmentById(R.id.code_fragment_container);

        if (codeFragment != null) {
            codeFragment.reset();
        }
    }

    @Override
    public void showErrorMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
