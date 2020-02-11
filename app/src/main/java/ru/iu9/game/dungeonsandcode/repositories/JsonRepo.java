package ru.iu9.game.dungeonsandcode.repositories;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class JsonRepo {

    private static final String CONFIG_FOLDER = "dungeon_configs";
    private static final String CUSTOM_LEVELS_DIR_NAME = "custom_dungeon_configs";
    private static final String FILE_NAME_PREFIX = "custom_dungeon";

    private AssetManager mAssetManager;
    private Gson mGson;
    private File mCustomLevelsDir;

    public JsonRepo(Context context) {
        mAssetManager = context.getAssets();
        mGson = new Gson();
        mCustomLevelsDir = context.getDir(CUSTOM_LEVELS_DIR_NAME, Context.MODE_PRIVATE);
    }

    public Gson getGson() {
        return mGson;
    }

    public int getJsonFilesCount() {
        try {
            return Objects.requireNonNull(mAssetManager.list(CONFIG_FOLDER)).length;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getCustomJsonFilesCount() {
        return Objects.requireNonNull(mCustomLevelsDir.list()).length;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
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
            e.printStackTrace();
            return null;
        }
    }

    public String getCustomJsonByIndex(int index) {
        String json;

        try {
            String path = mCustomLevelsDir.getAbsolutePath() + "/" + FILE_NAME_PREFIX + (index + 1);
            File jsonFile = new File(path);

            InputStream inputStream = new FileInputStream(jsonFile);
            StringBuilder stringBuilder = new StringBuilder();

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String receiveString;

            while ((receiveString = bufferedReader.readLine()) != null) {
                stringBuilder.append(receiveString);
            }

            inputStream.close();
            json = stringBuilder.toString();

            return json;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void saveJson(String json) {
        try {
            int fileNameSuffix = Objects.requireNonNull(mCustomLevelsDir.list()).length + 1;

            FileWriter fileWriter = new FileWriter(
                    mCustomLevelsDir.getAbsolutePath() + "/" + FILE_NAME_PREFIX + fileNameSuffix
            );

            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
