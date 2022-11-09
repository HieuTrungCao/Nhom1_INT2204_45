package uet.oop.bomberman.entities.destroyable.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.Player;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Enemy;
import uet.oop.bomberman.entities.destroyable.bomb.flame.Flame;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.LinkedList;
import java.util.List;

public abstract class BombExplosion extends AnimatedEntities {
    // time is life circle of bomb item
    protected final int time = Bomb.time;

    // contain bomb, flame, brick when bomb is explode
    protected final List<Entity> explosions;

    // store map
    protected final Character[][] map;

    // Lưu xem bomb được đặc từ player 1 hoặc 2
    protected short putBy;

    protected boolean isPlaySound;
    public BombExplosion(int xUnit, int yUnit, Sprite sprite, short putBy, Character[][] map) {
        super(xUnit, yUnit, sprite);
        explosions = new LinkedList<>();
        this.map = map;
        this.putBy = putBy;
        this.isPlaySound = false;
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
        ((Bomb) explosions.get(0)).setAnimate(this.getAnimate());
        explosions.get(0).update();
        if (animate >= time) {
            if (!isPlaySound) {
                Sound.bombExplode.restart();
                Sound.bombExplode.start();
                isPlaySound = true;
            }
        }
        if (animate >= time) {
            updateExplosion();
            checkDead();
        }
        animate();

        if (animate >= time * 2) {
            removeBrick();
            map[super.getY()/Sprite.SCALED_SIZE][super.getX()/Sprite.SCALED_SIZE] = ' ';
            Management.bombs.remove(0);
        }
    }

    /**
     * Khi bom nổ kiểm tra xem
     * - bomber có trong vùng đang nổ hay không
     * nếu có thì bomber sẽ chết
     * - quái có trong phạm vi bom nổ hay không
     * nếu có thì quái bị tiêu diệt
     * - có bất kì bức tường nào trong phạm vi nổ hay không
     * nếu có thì tường sẽ bị phá hủy và hiện ra các item hoặc glass
     * - Có quả bom bào ở trong phạm vi hay không
     * nếu có thì bom này cũng sẽ bị nổ
     */
    private void checkDead() {
        for (Player player : Management.players) {
            if (checkDeadEntity(player.getBomberman())) {
                player.getBomberman().setIsAlive(false);
                player.getBomberman().setAnimate(animate);
            }
        }

        for (int i = 0; i < Management.characters.size(); i++) {
            if (checkDeadEntity(Management.characters.get(i)) &&
                    ((Enemy) Management.characters.get(i)).isAlive()) {
                ((Enemy) Management.characters.get(i)).minusLife();
                /**
                 * gọi đến hàm cộng điểm ở đây
                 */
                Management.players.get(putBy - 1).setMark(
                        ((Enemy) Management.characters.get(i)).getPoint());
            }
        }

        for(int i = 0; i < Management.bombs.size(); i++){
            if (checkDeadEntity(Management.bombs.get(i))
                && Management.bombs.get(i).getAnimate() < this.getAnimate()) {
                Management.bombs.get(i).setAnimate(this.time);
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

                if (checkLayer(map[y][x])) {
                    map[y][x] = ' ';
                }
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

    /**
     * Kiểm tra xem kí tự cần kiểm tra xem có thuộc phần kí tự phá hủy đc không
     * *, B, F, S, H
     * @return true nếu thuộc những kí tự trên
     */
    public boolean checkLayer(Character character) {
        return character.compareTo('*') == 0 || character.compareTo('B') == 0 ||
                character.compareTo('F') == 0 || character.compareTo('S') == 0 ||
                character.compareTo('H') == 0 || character.compareTo('x') == 0;
    }
}
