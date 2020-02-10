package ru.iu9.game.dungeonsandcode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.iu9.game.dungeonsandcode.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.open_constructor_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConstructor();
            }
        });
    }

    private void openConstructor() {
        startActivity(new Intent(StartActivity.this, ConstructorActivity.class));
    }
}
