package uet.oop.bomberman.entities.destroyable.bomb.flame;

import uet.oop.bomberman.graphics.Sprite;

public class FlameRight extends Flame {
    public FlameRight(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
        animate = 0;
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.explosion_horizontal_right_last, Sprite.explosion_horizontal_right_last1,
                Sprite.explosion_horizontal_right_last2, animate, time);
        animate();
    }
}
