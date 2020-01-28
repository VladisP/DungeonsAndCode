package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;

import com.google.gson.Gson;

import ru.iu9.game.dungeonsandcode.DncApplication;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class DungeonGenerator {

    public static DungeonConfig generateConfig(Context context, String json) {
        Gson gson = DncApplication.from(context).getJsonRepo().getGson();
        return gson.fromJson(json, DungeonConfig.class);
    }

    public static boolean[][] generateWallsMap(DungeonConfig config, int size) {
        boolean[][] map = getInitialWallsMap(size);

        for (PositionPair pair : config.getWallsPosition()) {
            map[pair.getRowPosition()][pair.getColumnPosition()] = true;
        }

        return map;
    }

    private static boolean[][] getInitialWallsMap(int size) {
        boolean[][] map = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = false;
            }
        }

        return map;
    }
}
