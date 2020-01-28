package ru.iu9.game.dungeonsandcode;

import android.app.Application;
import android.content.Context;

import ru.iu9.game.dungeonsandcode.repositories.JsonRepo;

public class DncApplication extends Application {

    private JsonRepo mJsonRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        mJsonRepo = new JsonRepo(this);
    }

    public JsonRepo getJsonRepo() {
        return mJsonRepo;
    }

    public static DncApplication from(Context context) {
        return (DncApplication) context.getApplicationContext();
    }
}
