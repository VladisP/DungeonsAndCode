package ru.iu9.game.dungeonsandcode.dungeon.config;

import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class DungeonConfig {

    private PositionPair heroPosition;
    private PositionPair treasurePosition;
    private PositionPair[] monstersPosition;
    private PositionPair[] wallsPosition;
    private TrapConfig[] trapConfigs;

    public DungeonConfig() {
    }

    public PositionPair getHeroPosition() {
        return heroPosition;
    }

    public void setHeroPosition(PositionPair heroPosition) {
        this.heroPosition = heroPosition;
    }

    public PositionPair getTreasurePosition() {
        return treasurePosition;
    }

    public void setTreasurePosition(PositionPair treasurePosition) {
        this.treasurePosition = treasurePosition;
    }

    public PositionPair[] getMonstersPosition() {
        return monstersPosition;
    }

    public void setMonstersPosition(PositionPair[] monstersPosition) {
        this.monstersPosition = monstersPosition;
    }

    public PositionPair[] getWallsPosition() {
        return wallsPosition;
    }

    public void setWallsPosition(PositionPair[] wallsPosition) {
        this.wallsPosition = wallsPosition;
    }

    public TrapConfig[] getTrapConfigs() {
        return trapConfigs;
    }

    public void setTrapConfigs(TrapConfig[] trapConfigs) {
        this.trapConfigs = trapConfigs;
    }
}
