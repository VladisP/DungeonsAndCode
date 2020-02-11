package ru.iu9.game.dungeonsandcode.dungeon;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import ru.iu9.game.dungeonsandcode.DncApplication;
import ru.iu9.game.dungeonsandcode.constructor.entities.ConstructorPart;
import ru.iu9.game.dungeonsandcode.dungeon.config.DungeonConfig;
import ru.iu9.game.dungeonsandcode.dungeon.config.TrapConfig;
import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class DungeonGenerator {

    public static DungeonConfig generateConfig(Context context, String json) {
        Gson gson = DncApplication.from(context).getJsonRepo().getGson();
        return gson.fromJson(json, DungeonConfig.class);
    }

    public static DungeonConfig generateConfig(ConstructorPart[][] constructorParts) {
        DungeonConfig config = new DungeonConfig();

        List<PositionPair> monstersPosition = new ArrayList<>();
        List<PositionPair> wallsPosition = new ArrayList<>();
        List<TrapConfig> trapConfigs = new ArrayList<>();

        for (int i = 0; i < DungeonView.FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < DungeonView.FLOORS_ROW_COUNT; j++) {
                ConstructorPart part = constructorParts[i][j];

                switch (part.getPartType()) {
                    case WALL:
                        wallsPosition.add(part.getPosition());
                        break;
                    case HERO:
                        config.setHeroPosition(part.getPosition());
                        break;
                    case TREASURE:
                        config.setTreasurePosition(part.getPosition());
                        break;
                    case MONSTER:
                        monstersPosition.add(part.getPosition());
                        break;
                    case TRAP_LEFT:
                        trapConfigs.add(createTrapConfig(0, part.getPosition()));
                        break;
                    case TRAP_TOP:
                        trapConfigs.add(createTrapConfig(1, part.getPosition()));
                        break;
                    case TRAP_RIGHT:
                        trapConfigs.add(createTrapConfig(2, part.getPosition()));
                        break;
                    case TRAP_BOTTOM:
                        trapConfigs.add(createTrapConfig(3, part.getPosition()));
                        break;
                }
            }
        }

        config.setMonstersPosition(monstersPosition.toArray(new PositionPair[0]));
        config.setWallsPosition(wallsPosition.toArray(new PositionPair[0]));
        config.setTrapConfigs(trapConfigs.toArray(new TrapConfig[0]));

        return config;
    }

    private static TrapConfig createTrapConfig(int type, PositionPair position) {
        TrapConfig trapConfig = new TrapConfig();

        trapConfig.setTrapType(type);
        trapConfig.setTrapPosition(position);

        return trapConfig;
    }

    static boolean[][] generateWallsMap(DungeonConfig config) {
        boolean[][] map = getInitialWallsMap();

        for (PositionPair pair : config.getWallsPosition()) {
            map[pair.getRowPosition()][pair.getColumnPosition()] = true;
        }

        return map;
    }

    private static boolean[][] getInitialWallsMap() {
        boolean[][] map = new boolean[DungeonView.FLOORS_ROW_COUNT][DungeonView.FLOORS_ROW_COUNT];

        for (int i = 0; i < DungeonView.FLOORS_ROW_COUNT; i++) {
            for (int j = 0; j < DungeonView.FLOORS_ROW_COUNT; j++) {
                map[i][j] = false;
            }
        }

        return map;
    }
}
