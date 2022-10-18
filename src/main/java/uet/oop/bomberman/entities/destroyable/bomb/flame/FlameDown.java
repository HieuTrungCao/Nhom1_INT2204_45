package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameDown extends Flame {
    public FlameDown(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                Sprite.explosion_vertical_down_last2, animate, time);
        animate();
    }
}
