package uet.oop.bomberman.entities.destroyable.bomb;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.destroyable.bomb.flame.FlameDown;
import uet.oop.bomberman.entities.destroyable.bomb.flame.FlameLeft;
import uet.oop.bomberman.entities.destroyable.bomb.flame.FlameRight;
import uet.oop.bomberman.entities.destroyable.bomb.flame.FlameTop;
import uet.oop.bomberman.graphics.Sprite;

public class BombExplosionNormal extends BombExplosion {
    public BombExplosionNormal(int xUnit, int yUnit, Sprite sprite, short putBy, Character[][] map) {
        super(xUnit, yUnit, sprite, putBy, map);
        addItems(xUnit, yUnit);
    }

    /**
     * Check map to add Flame and brick to BombExplostionNormal.
     *
     * @param xUnit is horizontal
     * @param yUnit is vertical
     */

    @Override
    public void addItems(int xUnit, int yUnit) {
        explosions.add(new Bomb(xUnit, yUnit, Sprite.bomb));

        // Top
        if (map[yUnit - 1][xUnit].compareTo('#') != 0) {
            if (checkLayer(map[yUnit - 1][xUnit]))
                explosions.add(Management.getEntity(xUnit, yUnit - 1));
            else
                explosions.add(new FlameTop(xUnit, yUnit - 1, Sprite.explosion_vertical_top_last));
        }

        // Right
        if (map[yUnit][xUnit + 1].compareTo('#') != 0) {
            if (checkLayer(map[yUnit][xUnit + 1]))
                explosions.add(Management.getEntity(xUnit + 1, yUnit));
            else
                explosions.add(new FlameRight(xUnit + 1, yUnit, Sprite.explosion_horizontal_right_last));
        }

        // Down
        if (map[yUnit + 1][xUnit].compareTo('#') != 0) {
            if (checkLayer(map[yUnit + 1][xUnit]))
                explosions.add(Management.getEntity(xUnit, yUnit + 1));
            else
                explosions.add(new FlameDown(xUnit, yUnit + 1, Sprite.explosion_vertical_down_last));
        }

        // Left
        if (map[yUnit][xUnit - 1].compareTo('#') != 0) {
            if (checkLayer(map[yUnit][xUnit - 1]))
                explosions.add(Management.getEntity(xUnit - 1, yUnit));
            else
                explosions.add(new FlameLeft(xUnit - 1, yUnit, Sprite.explosion_horizontal_left_last));
        }
    }

    /**
     * Check entity is in flame.
     * If entity is in flame
     * return true
     * else return false
     */
    @Override
    public boolean checkDeadEntity(Entity entity) {
        int x = entity.getX();
        int y = entity.getY();

        if (y == this.y) {
            if (x > (this.x - 2 * Sprite.SCALED_SIZE) && (x < this.x + 2 * Sprite.SCALED_SIZE))
                return true;
        } else if (x == this.x) {
            if (y > (this.y - 2 * Sprite.SCALED_SIZE) && (y < this.y + 2 * Sprite.SCALED_SIZE))
                return true;
        }

        return false;
    }
}
