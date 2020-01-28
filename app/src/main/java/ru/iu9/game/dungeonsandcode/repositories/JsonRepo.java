package ru.iu9.game.dungeonsandcode.repositories;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import ru.iu9.game.dungeonsandcode.log.Logger;

public class JsonRepo {

    private static final String CONFIG_FOLDER = "dungeon_configs";

    private AssetManager mAssetManager;
    private Gson mGson;

    public JsonRepo(Context context) {
        mAssetManager = context.getAssets();
        mGson = new Gson();
    }

    public Gson getGson() {
        return mGson;
    }

    public String getJsonByIndex(int index) {
        try {
            String fileName = Objects.requireNonNull(mAssetManager.list(CONFIG_FOLDER))[index];
            InputStream inputStream = mAssetManager.open(CONFIG_FOLDER + "/" + fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            Logger.log("getJsonByIndex failed!");
            e.printStackTrace();
            return null;
        }
    }
}
