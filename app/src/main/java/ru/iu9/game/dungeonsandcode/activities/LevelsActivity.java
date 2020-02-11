package ru.iu9.game.dungeonsandcode.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.iu9.game.dungeonsandcode.DncApplication;
import ru.iu9.game.dungeonsandcode.R;

public class LevelsActivity extends AppCompatActivity {

    private List<Integer> mLevelListItems;
    private List<Integer> mCustomListItems;

    private class LevelHolder extends RecyclerView.ViewHolder {

        private Button mLevelButton;

        LevelHolder(@NonNull View itemView) {
            super(itemView);
            mLevelButton = itemView.findViewById(R.id.start_level_button);
        }

        void bind(final int levelNumber, final boolean isCustom) {
            mLevelButton.setText(String.format(Locale.US, "Уровень %d", levelNumber));

            mLevelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startLevel(levelNumber - 1, isCustom);
                }
            });
        }
    }

    private class LevelAdapter extends RecyclerView.Adapter<LevelHolder> {

        private Context mContext;
        List<Integer> mLevelNumbers;

        LevelAdapter(Context context, List<Integer> levelNumbers) {
            mContext = context;
            mLevelNumbers = levelNumbers;
        }

        @NonNull
        @Override
        public LevelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.level_list_item, parent, false);

            return new LevelHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LevelHolder holder, int position) {
            int levelNumber = mLevelNumbers.get(position);
            holder.bind(levelNumber, false);
        }

        @Override
        public int getItemCount() {
            return mLevelNumbers.size();
        }
    }

    private class CustomLevelAdapter extends LevelAdapter {

        CustomLevelAdapter(Context context, List<Integer> levelNumbers) {
            super(context, levelNumbers);
        }

        @Override
        public void onBindViewHolder(@NonNull LevelHolder holder, int position) {
            int levelNumber = mLevelNumbers.get(position);
            holder.bind(levelNumber, true);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        initLevelListItems();
        initCustomLevelListItems();
    }

    @Override
    protected void onStart() {
        super.onStart();

        initLevelList();
        initCustomLevelList();
    }

    private void initLevelListItems() {
        mLevelListItems = new ArrayList<>();
        int levelsCount = DncApplication.from(this).getJsonRepo().getJsonFilesCount();

        for (int i = 1; i <= levelsCount; i++) {
            mLevelListItems.add(i);
        }
    }

    private void initCustomLevelListItems() {
        mCustomListItems = new ArrayList<>();
        int customLevelsCount = DncApplication.from(this).getJsonRepo().getCustomJsonFilesCount();

        for (int i = 1; i <= customLevelsCount; i++) {
            mCustomListItems.add(i);
        }
    }

    private void initLevelList() {
        RecyclerView levelList = findViewById(R.id.levels_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        levelList.setLayoutManager(layoutManager);
        levelList.setAdapter(new LevelAdapter(this, mLevelListItems));
    }

    private void initCustomLevelList() {
        RecyclerView customLevelList = findViewById(R.id.custom_levels_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        customLevelList.setLayoutManager(layoutManager);
        customLevelList.setAdapter(new CustomLevelAdapter(this, mCustomListItems));
    }

    private void startLevel(int levelNumber, boolean isCustom) {
        Intent intent = GameActivity.newIntent(LevelsActivity.this, levelNumber, isCustom);
        startActivity(intent);
    }
}
