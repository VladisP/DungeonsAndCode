package ru.iu9.game.dungeonsandcode.dungeon.config;

import ru.iu9.game.dungeonsandcode.dungeon.entities.helper_entities.PositionPair;

public class TrapConfig {

    private int trapType;
    private PositionPair trapPosition;

    public TrapConfig() {
    }

    public int getTrapType() {
        return trapType;
    }

    public void setTrapType(int trapType) {
        this.trapType = trapType;
    }

    public PositionPair getTrapPosition() {
        return trapPosition;
    }

    public void setTrapPosition(PositionPair trapPosition) {
        this.trapPosition = trapPosition;
    }
}
