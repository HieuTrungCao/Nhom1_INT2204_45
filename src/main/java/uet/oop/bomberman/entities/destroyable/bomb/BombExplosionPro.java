package uet.oop.bomberman.entities.destroyable.bomb;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.destroyable.*;
import uet.oop.bomberman.entities.destroyable.bomb.flame.*;
import uet.oop.bomberman.graphics.Sprite;

public class BombExplosionPro extends BombExplosion {

    public BombExplosionPro(int xUnit, int yUnit, Sprite sprite, Character[][] map) {
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
            if (map[yUnit - 1][xUnit].compareTo('*') == 0) {
                explosions.add(BombermanGame.getEntity(xUnit, yUnit - 1));
                if (map[yUnit - 2][xUnit].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit, yUnit - 2));
                }
            }
            else {
                explosions.add(new FlameVertical(xUnit, yUnit - 1, Sprite.explosion_vertical));
                if (map[yUnit - 2][xUnit].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit, yUnit - 2));
                } else {
                    explosions.add(new FlameTop(xUnit, yUnit - 2, Sprite.explosion_vertical_top_last));
                }
            }
        }

        // Right
        if (map[yUnit][xUnit + 1].compareTo('#') != 0) {
            if (map[yUnit][xUnit + 1].compareTo('*') == 0) {
                explosions.add(BombermanGame.getEntity(xUnit + 1, yUnit));
                if (map[yUnit][xUnit + 2].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit + 2, yUnit));
                }
            }
            else {
                explosions.add(new FlameHorizontal(xUnit + 1, yUnit, Sprite.explosion_horizontal));
                if (map[yUnit][xUnit + 2].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit + 2, yUnit));
                } else {
                    explosions.add(new FlameRight(xUnit + 2, yUnit, Sprite.explosion_horizontal_right_last));
                }
            }
        }

        // Down
        if (map[yUnit + 1][xUnit].compareTo('#') != 0) {
            if (map[yUnit + 1][xUnit].compareTo('*') == 0) {
                explosions.add(BombermanGame.getEntity(xUnit, yUnit + 1));
                if (map[yUnit + 2][xUnit].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit, yUnit + 2));
                }
            }
            else {
                explosions.add(new FlameVertical(xUnit, yUnit + 1, Sprite.explosion_vertical));
                if (map[yUnit + 2][xUnit].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit, yUnit + 2));
                } else {
                    explosions.add(new FlameDown(xUnit, yUnit + 2, Sprite.explosion_vertical_down_last));
                }
            }
        }

        // Left
        if (map[yUnit][xUnit - 1].compareTo('#') != 0) {
            if (map[yUnit][xUnit - 1].compareTo('*') == 0) {
                explosions.add(BombermanGame.getEntity(xUnit - 1, yUnit));
                if (map[yUnit][xUnit - 2].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit - 2, yUnit));
                }
            }
            else {
                explosions.add(new FlameHorizontal(xUnit - 1, yUnit, Sprite.explosion_horizontal));
                if (map[yUnit][xUnit - 2].compareTo('*') == 0) {
                    explosions.add(BombermanGame.getEntity(xUnit - 2, yUnit));
                } else {
                    explosions.add(new FlameLeft(xUnit - 2, yUnit, Sprite.explosion_horizontal_left_last));
                }
            }
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

        int bombX = x / Sprite.SCALED_SIZE;
        int bombY = y / Sprite.SCALED_SIZE;

        if(map[bombY][bombX - 1].compareTo('#') == 0 && map[bombY][bombX + 1].compareTo('#') == 0) {
            if (x == this.x)
                if (y > (this.y - 3 * Sprite.SCALED_SIZE) && (y < this.y + 3 * Sprite.SCALED_SIZE))
                    return true;
        } else if(map[bombY - 1][bombX].compareTo('#') == 0 && map[bombY + 1][bombX].compareTo('#') == 0) {
            if (y == this.y)
                if (x > (this.x - 3 * Sprite.SCALED_SIZE) && (x < this.x + 3 * Sprite.SCALED_SIZE))
                    return true;
        } else {

            if (y == this.y) {
                if (x > (this.x - 3 * Sprite.SCALED_SIZE) && (x < this.x + 3 * Sprite.SCALED_SIZE))
                    return true;
            } else if (x == this.x) {
                if (y > (this.y - 3 * Sprite.SCALED_SIZE) && (y < this.y + 3 * Sprite.SCALED_SIZE))
                    return true;
            }
        }

        return false;
    }
}
