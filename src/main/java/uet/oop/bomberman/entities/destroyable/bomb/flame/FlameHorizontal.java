package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameHorizontal extends Flame {
    public FlameHorizontal(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                Sprite.explosion_horizontal2, animate, time);
        animate();
    }
}
