package uet.oop.bomberman.entities.destroyable;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Player;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;
import java.util.List;

public abstract class BombExplosion extends AnimatedEntities {
    // time is life circle of bomb item
    protected final int time = BombItem.time;

    // contain bomb, flame, brick when bomb is explode
    protected final List<Entity> explosions;

    // store map
    protected final Character[][] map;

    public BombExplosion(int xUnit, int yUnit, Sprite sprite, Character[][] map) {
        super(xUnit, yUnit, sprite);
        explosions = new LinkedList<>();
        this.map = map;
    }

    /**
     * Check map to add Flame and brick to BombExplostionNormal.
     *
     * @param xUnit is horizontal
     * @param yUnit is vertical
     */
    public abstract void addItems(int xUnit, int yUnit);

    @Override
    public void update() {
        explosions.get(0).update();
        if (animate >= time) {
            updateExplosion();
            checkDead();
        }
        animate();

        if (animate == time * 2) {
            removeBrick();
            BombermanGame.bombs.remove();
        }
    }

    private void checkDead() {
        if (checkDeadEntity(Player.bomberman)) {
            Player.bomberman.setIsAlive(false);
        }

        for (int i = 0; i < BombermanGame.characters.size(); i++) {
            if (checkDeadEntity(BombermanGame.characters.get(i))) {
                BombermanGame.characters.remove(i);
            }
        }
    }

    /**
     * Check bomberman is in flame.
     * If bomberman is in flame
     * set status of bomberman is die.
     */
    public abstract boolean checkDeadEntity(Entity entity);

    /**
     * If brick is destroyed by bomb
     * and then remove brick and draw grass or item to window.
     */
    private void removeBrick() {
        for (int i = 0; i < explosions.size(); i++) {
            if (explosions.get(i) instanceof LayeredEntity) {
                ((LayeredEntity) explosions.get(i)).removeTop();
                int y = explosions.get(i).getY() / Sprite.SCALED_SIZE;
                int x = explosions.get(i).getX() / Sprite.SCALED_SIZE;
                map[y][x] = ' ';
            }
        }
    }

    /**
     * Processing animate of flame and brick
     * when bomb is explode.
     */
    private void updateExplosion() {
        for (int i = 1; i < explosions.size(); i++)
            explosions.get(i).update();
    }

    @Override
    public void render(GraphicsContext gc) {
        explosions.get(0).render(gc);
        if (animate >= time) {
            renderFlame(gc);
        }
    }

    /**
     * Draw flame to window.
     *
     * @param gc is only GraphicsContext of program
     */
    private void renderFlame(GraphicsContext gc) {
        for (int i = 1; i < explosions.size(); i++)
            if (explosions.get(i) instanceof Flame)
                explosions.get(i).render(gc);
    }
}