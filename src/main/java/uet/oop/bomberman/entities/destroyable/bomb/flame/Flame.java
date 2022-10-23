package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends AnimatedEntities {

    protected final int time;
    public Flame(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        time = Bomb.time;
    }
}
