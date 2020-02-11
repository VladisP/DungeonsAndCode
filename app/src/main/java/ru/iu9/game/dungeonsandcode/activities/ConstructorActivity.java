package ru.iu9.game.dungeonsandcode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;

import ru.iu9.game.dungeonsandcode.DncApplication;
import ru.iu9.game.dungeonsandcode.R;
import ru.iu9.game.dungeonsandcode.constructor.ConstructorFragment;
import ru.iu9.game.dungeonsandcode.constructor.EditorFragment;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorEventListener;
import ru.iu9.game.dungeonsandcode.constructor.helpers.ConstructorPartType;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;
import ru.iu9.game.dungeonsandcode.repositories.JsonRepo;

public class ConstructorActivity extends AppCompatActivity implements ConstructorEventListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructor);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment constructorFragment = fragmentManager.findFragmentById(R.id.constructor_fragment_container);
        Fragment editorFragment = fragmentManager.findFragmentById(R.id.editor_fragment_container);

        if (constructorFragment == null) {
            constructorFragment = new ConstructorFragment();
            addFragment(constructorFragment, R.id.constructor_fragment_container);
        }

        if (editorFragment == null) {
            editorFragment = new EditorFragment();
            addFragment(editorFragment, R.id.editor_fragment_container);
        }
    }

    private void addFragment(Fragment fragment, int containerId) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    @Override
    public void switchPartType(ConstructorPartType newPartType) {
        ConstructorFragment constructorFragment = (ConstructorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.constructor_fragment_container);

        if (constructorFragment != null) {
            constructorFragment.switchPartType(newPartType);
        }
    }

    @Override
    public void removeLastAction() {
        ConstructorFragment constructorFragment = (ConstructorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.constructor_fragment_container);

        if (constructorFragment != null) {
            constructorFragment.removeLastAction();
        }
    }

    @Override
    public void removeAll() {
        ConstructorFragment constructorFragment = (ConstructorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.constructor_fragment_container);

        if (constructorFragment != null) {
            constructorFragment.removeAll();
        }
    }

    @Override
    public void saveLevel() {
        ConstructorFragment constructorFragment = (ConstructorFragment) getSupportFragmentManager()
                .findFragmentById(R.id.constructor_fragment_container);

        if (constructorFragment != null) {
            DungeonConfig config = constructorFragment.getDungeonConfig();

            if (config == null) {
                showErrorMessage(R.string.hero_and_treasure_exists);
                return;
            }

            JsonRepo jsonRepo = DncApplication.from(this).getJsonRepo();
            Gson gson = jsonRepo.getGson();
            String levelJson = gson.toJson(config);

            jsonRepo.saveJson(levelJson);

            startActivity(new Intent(ConstructorActivity.this, StartActivity.class));
        }
    }

    @Override
    public void showErrorMessage(int msgId) {
        Toast.makeText(this, msgId, Toast.LENGTH_SHORT).show();
    }
}
