package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameTop extends Flame {

    public FlameTop(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_vertical_top_last, Sprite.explosion_vertical_top_last1,
                Sprite.explosion_vertical_top_last2, animate, time);
        animate();
    }
}
