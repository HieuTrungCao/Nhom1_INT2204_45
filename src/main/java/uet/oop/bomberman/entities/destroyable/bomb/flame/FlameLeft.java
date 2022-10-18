package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameLeft extends Flame {
    public FlameLeft(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                Sprite.explosion_horizontal_left_last2, animate, time);
        animate();
    }
}
