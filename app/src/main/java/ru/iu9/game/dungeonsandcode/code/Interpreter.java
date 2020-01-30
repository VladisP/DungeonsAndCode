package ru.iu9.game.dungeonsandcode.code;

import java.util.List;

import ru.iu9.game.dungeonsandcode.code.helpers.HeroDirection;

import static ru.iu9.game.dungeonsandcode.code.CodeFragment.*;

class Interpreter {

    private static HeroDirection sHeroDirection = HeroDirection.TOP;

    static void run(List<String> program, HeroMoveListener heroMoveListener) {
        sHeroDirection = HeroDirection.TOP;

        for (String command : program) {
            switch (command) {
                case "move()":
                    move(heroMoveListener);
                    break;
                case "turnLeft()":
                    turnLeft();
                    break;
                case "turnRight()":
                    turnRight();
                    break;
            }
        }
    }

    private static void move(HeroMoveListener heroMoveListener) {
        switch (sHeroDirection) {
            case TOP:
                heroMoveListener.moveUp();
                break;
            case RIGHT:
                heroMoveListener.moveRight();
                break;
            case BOTTOM:
                heroMoveListener.moveDown();
                break;
            case LEFT:
                heroMoveListener.moveLeft();
                break;
        }
    }

    private static void turnLeft() {
        switch (sHeroDirection) {
            case TOP:
                sHeroDirection = HeroDirection.LEFT;
                break;
            case RIGHT:
                sHeroDirection = HeroDirection.TOP;
                break;
            case BOTTOM:
                sHeroDirection = HeroDirection.RIGHT;
                break;
            case LEFT:
                sHeroDirection = HeroDirection.BOTTOM;
                break;
        }
    }

    private static void turnRight() {
        switch (sHeroDirection) {
            case TOP:
                sHeroDirection = HeroDirection.RIGHT;
                break;
            case RIGHT:
                sHeroDirection = HeroDirection.BOTTOM;
                break;
            case BOTTOM:
                sHeroDirection = HeroDirection.LEFT;
                break;
            case LEFT:
                sHeroDirection = HeroDirection.TOP;
                break;
        }
    }
}
