package uet.oop.bomberman.entities.destroyable;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class BombExplosionNormal extends BombExplosion {
    public BombExplosionNormal(int xUnit, int yUnit, Sprite sprite, Character[][] map) {
        super(xUnit, yUnit, sprite, map);
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
        explosions.add(new BombItem(xUnit, yUnit, Sprite.bomb));

        // Top
        if (map[yUnit - 1][xUnit].compareTo('#') != 0) {
            if (map[yUnit - 1][xUnit].compareTo('*') == 0)
                explosions.add(BombermanGame.getEntity(xUnit, yUnit - 1));
            else
                explosions.add(new FlameTop(xUnit, yUnit - 1, Sprite.explosion_vertical_top_last));
        }

        // Right
        if (map[yUnit][xUnit + 1].compareTo('#') != 0) {
            if (map[yUnit][xUnit + 1].compareTo('*') == 0)
                explosions.add(BombermanGame.getEntity(xUnit + 1, yUnit));
            else
                explosions.add(new FlameRight(xUnit + 1, yUnit, Sprite.explosion_horizontal_right_last));
        }

        // Down
        if (map[yUnit + 1][xUnit].compareTo('#') != 0) {
            if (map[yUnit + 1][xUnit].compareTo('*') == 0)
                explosions.add(BombermanGame.getEntity(xUnit, yUnit + 1));
            else
                explosions.add(new FlameDown(xUnit, yUnit + 1, Sprite.explosion_vertical_down_last));
        }

        // Left
        if (map[yUnit][xUnit - 1].compareTo('#') != 0) {
            if (map[yUnit][xUnit - 1].compareTo('*') == 0)
                explosions.add(BombermanGame.getEntity(xUnit - 1, yUnit));
            else
                explosions.add(new FlameLeft(xUnit - 1, yUnit, Sprite.explosion_horizontal_left_last));
        }
    }

    /**
     * Check bomberman is in flame.
     * If bomberman is in flame
     * set status of bomberman is die.
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