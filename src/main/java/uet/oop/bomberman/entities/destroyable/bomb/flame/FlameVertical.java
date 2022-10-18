package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameVertical extends Flame {

    public FlameVertical(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                Sprite.explosion_vertical2, animate, time);
        animate();
    }
}
