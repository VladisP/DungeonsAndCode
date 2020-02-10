package ru.iu9.game.dungeonsandcode.activities;

import android.content.Context;
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

    private class LevelHolder extends RecyclerView.ViewHolder {

        private Button mLevelButton;

        LevelHolder(@NonNull View itemView) {
            super(itemView);
            mLevelButton = itemView.findViewById(R.id.start_level_button);
        }

        void bind(int levelNumber) {
            mLevelButton.setText(String.format(Locale.US, "Уровень %d", levelNumber));
        }
    }

    private class LevelAdapter extends RecyclerView.Adapter<LevelHolder> {

        private Context mContext;
        private List<Integer> mLevelNumbers;

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
            int levelNumber = mLevelListItems.get(position);
            holder.bind(levelNumber);
        }

        @Override
        public int getItemCount() {
            return mLevelNumbers.size();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        initLevelListItems();
    }

    @Override
    protected void onStart() {
        super.onStart();
        RecyclerView levelList = findViewById(R.id.levels_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        levelList.setLayoutManager(layoutManager);
        levelList.setAdapter(new LevelAdapter(this, mLevelListItems));
    }

    private void initLevelListItems() {
        mLevelListItems = new ArrayList<>();
        int levelsCount = DncApplication.from(this).getJsonRepo().getJsonFilesCount();

        for (int i = 1; i <= levelsCount; i++) {
            mLevelListItems.add(i);
        }
    }
}
