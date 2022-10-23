package uet.oop.bomberman.entities.destroyable;

import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends DestroyableEntities {

    private final int time = Bomb.time;
    private int annimte;
    private boolean isDestroying;
    public Brick(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        isDestroying = false;
        annimte = 0;
    }

    @Override
    public void update() {
        //if(isDestroying) {
            sprite = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2, annimte++, time);
        //}
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Brick) {
            return x == ((Brick) obj).x && y == ((Brick) obj).y;
        }

        return false;
    }
}
