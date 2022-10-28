package uet.oop.bomberman;

import uet.oop.bomberman.graphics.Sprite;

public class Coordinate {
    public static int toUnit(int x) {
        return x / Sprite.SCALED_SIZE;
    }

    public static int toPixel(int xUnit){
        return xUnit * Sprite.SCALED_SIZE;
    }
}
