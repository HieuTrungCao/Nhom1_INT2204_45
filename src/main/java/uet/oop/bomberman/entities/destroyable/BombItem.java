package uet.oop.bomberman.entities.destroyable;

import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends AnimatedEntities {

    public static final int time = 60;

    public BombItem(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    @Override
    public void update() {
        if (animate < time) {
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, time);
        } else if (animate < 2 * time) {
            sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, animate, time);
        }
        animate();
    }

}
