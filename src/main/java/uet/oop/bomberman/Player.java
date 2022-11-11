package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionNormal;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionPro;
import uet.oop.bomberman.entities.destroyable.items.BombItem;
import uet.oop.bomberman.entities.destroyable.items.FlameItem;
import uet.oop.bomberman.entities.destroyable.items.HeartItem;
import uet.oop.bomberman.entities.destroyable.items.SpeedItem;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Queue;
import java.util.Set;

import static javafx.scene.input.KeyCode.*;

public class Player {
    // Đếm số player
    private static short count = 0;
    // Nhân vật Bomber
    private Bomber bomberman;
    // Lưu điểm
    private int mark;
    // Bonus score tính theo số mạng và bomb còn lại
    private int bonusScore;
    // Lưu số mạng của nhân vật
    private int heart;
    // Số lượng bomb nhân vật có
    private int defaultHeart;
    private int bomb;
    private int defaultBomb;
    // Xem có đang ở bomb nạp VIP ko
    private boolean isBombPro;
    // Lưu trữ map để sử lý check map khi di chuyển nhanh hơn
    private Character[][] map;
    // Lưu trạng thái xem có đang tăng tốc độ hay không
    // để sử lý tình trạng tăng tốc độ dẫn đến đi xuyên tường
    private boolean isSetSpeed;
    // Lưu xem nhan vật đi theo hướng nào
    // true là đi theo chiều ngang
    // false là đi theo chiều dọc
    private boolean direction;
    // Lưu thứ tự của player
    private short num;
    // Lưu thứ tự lấy sprite
    private short numBomberman;

    // Lưu xem tăng hay giảm speed
    private boolean isIncreaseSpeed;

    public Player(Character[][] map, short numBomberman, int heart, int bomb) {
        bomberman = new Bomber(1, 1, Bomber.player_right[numBomberman], numBomberman);
        this.map = map;
        this.numBomberman = numBomberman;
        this.mark = 0;
        this.heart = heart;
        this.defaultHeart = heart;
        this.bomb = bomb;
        this.defaultBomb = bomb;
        this.isBombPro = false;
        this.isSetSpeed = false;
        this.num = (++this.count);
    }

    public Player(short numBomberman, int heart, int bomb) {
        bomberman = new Bomber(1, 1, Bomber.player_right[numBomberman], numBomberman);
        this.numBomberman = numBomberman;
        this.mark = 0;
        this.heart = heart;
        this.defaultHeart = heart;
        this.bomb = bomb;
        this.defaultBomb = bomb;
        this.isBombPro = false;
        this.isSetSpeed = false;
        this.num = (++this.count);
    }

    public void setMap(Character[][] map) {
        this.map = map;
    }

    public static void setCount(short count) {
        Player.count = count;
    }

    public int getDefaultHeart() {
        return defaultHeart;
    }

    public int getDefaultBomb() {
        return defaultBomb;
    }

    public void reset() {
        this.bomb = defaultBomb;
        this.heart = defaultHeart;
        this.mark = 0;
        this.isBombPro = false;
        this.isSetSpeed = false;
    }

    public short getNumBomberman() {
        return numBomberman;
    }

    /**
     * Sử lý sự kiện từ bàn phím để điều khiển bomber
     *
     * @param codes mã code của phím
     */
    public void play(Set<KeyCode> codes) {
        if (!bomberman.isAlive()) {
            return;
        }
        if (num == 1) {
            if (codes.contains(W) && checkMap(1)) {
                bomberman.moveUp();
                direction = false;
            } else if (codes.contains(S) && checkMap(3)) {
                bomberman.moveDown();
                direction = false;
            } else if (codes.contains(A) && checkMap(4)) {
                bomberman.moveLeft();
                direction = true;
                
            } else if (codes.contains(D) && checkMap(2)) {
                bomberman.moveRight();
                
            } else if (codes.contains(SPACE) && bomb > 0) {
                addBomb(isBombPro);
                bomb--;
                direction = true;
                
            }
        }
        if (num == 2) {
            if (codes.contains(UP) && checkMap(1)) {
                bomberman.moveUp();
                direction = false;
                
            } else if (codes.contains(DOWN) && checkMap(3)) {
                bomberman.moveDown();
                direction = false;
                
            } else if (codes.contains(LEFT) && checkMap(4)) {
                bomberman.moveLeft();
                direction = true;
                
            } else if (codes.contains(RIGHT) && checkMap(2)) {
                bomberman.moveRight();
                
            } else if (codes.contains(ENTER) && bomb > 0) {
                addBomb(isBombPro);
                bomb--;
                direction = true;
                
            }
        }
        eatItem();
    }

    /**
     * Kiểm tra và ăn các item hiện ra khi phá tưởng
     */
    private void eatItem() {
        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;

        Entity entity = Management.getEntity(x, y);

        if (entity instanceof LayeredEntity) {
            if (!(((LayeredEntity) entity).getTopEntity() instanceof Grass)
                    && !(((LayeredEntity) entity).getTopEntity() instanceof Brick)) {
                if (((LayeredEntity) entity).getTopEntity() instanceof SpeedItem) {
                    ((LayeredEntity) entity).removeTop();
                    /**
                     * To do something
                     */
                    mark += 50;
                    isSetSpeed = true;
                } else if (((LayeredEntity) entity).getTopEntity() instanceof FlameItem) {
                    ((LayeredEntity) entity).removeTop();
                    /**
                     * to do something
                     */
                    mark += 50;
                    isBombPro = true;
                } else if (((LayeredEntity) entity).getTopEntity() instanceof HeartItem) {
                    ((LayeredEntity) entity).removeTop();
                    mark += 50;
                    heart++;
                } else if (((LayeredEntity) entity).getTopEntity() instanceof BombItem) {
                    ((LayeredEntity) entity).removeTop();
                    mark += 50;
                    bomb++;
                }
                Sound.powerUp.start();
                Sound.powerUp.restart();
            }
        }
    }

    /**
     * Kiểm tra xem bomber đã đi vào cổng chưa
     * Nếu đi vào cổng thì chiến thắng
     *
     * @return
     */
    public boolean checkVictory() {
        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;

        Entity entity = Management.getEntity(x, y);

        if (entity instanceof LayeredEntity) {
            if (((LayeredEntity) entity).getTopEntity() instanceof Portal) {
                if (entity.getX() == bomberman.getX() &&
                        entity.getY() == bomberman.getY() &&
                        Management.characters.size() == 0) {
                    bonusScore = bomb * 30 + heart * 100;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLose() {
        if (this.heart == 0) {
            return true;
        }
        return false;
    }

    public void update() {
        if (!bomberman.isAlive()
                && bomberman.getAnimate_die() == bomberman.getTime_die()) {
            if (heart > 0) {
                bomberman.setIsAlive(true);
                heart--;
                bomberman.setX(48);
                bomberman.setY(48);
                bomberman.setSpeed(false);
                isBombPro = false;
            }
        }

        if (isSetSpeed) {
            if ((direction && bomberman.getX() % (2 * bomberman.getSpeed()) == 0)
                    || (!direction && bomberman.getY() % (2 * bomberman.getSpeed()) == 0)) {
                setSpeed(true);
                isSetSpeed = false;
            }
        }
        bomberman.update();
    }

    public void render(GraphicsContext gc) {
        bomberman.render(gc);
    }

    /**
     * Them bom vao vi tri đang đứng
     *
     * @param pro is true thì đặt bom nạp vip
     *            pro is false thì đặt bom loại bình thường
     */
    private void addBomb(boolean pro) {
        int x = (bomberman.getX() + Sprite.SCALED_SIZE / 2) / Sprite.SCALED_SIZE;
        int y = (bomberman.getY() + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
        map[y][x] = 'b';
        if (!pro)
            Management.bombs.add(new BombExplosionNormal(x, y, null, num, map));
        else
            Management.bombs.add(new BombExplosionPro(x, y, null, num, map));
    }

    // kiem tra xem co bi vuong gi khong
    // neu vuong thi khong di duoc
    // top : 1
    // right : 2
    // bottom : 3
    // left : 4
    private boolean checkMap(int s) {
        int x = bomberman.getX();
        int y = bomberman.getY();

        int speed = bomberman.getSpeed();


        if (s == 1) {
            int xFuture1 = x / Sprite.SCALED_SIZE;
            int yFuture1 = (y - speed) / Sprite.SCALED_SIZE;
            int xFuture2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y - speed) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (s == 2) {
            int xFuture1 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (s == 3) {
            int xFuture1 = x / Sprite.SCALED_SIZE;
            int yFuture1 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int xFuture2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (s == 4) {
            int xFuture1 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }


        return true;
    }

    private boolean isBlock(Character character) {
        return character.compareTo('#') == 0 || character.compareTo('*') == 0 ||
                character.compareTo('B') == 0 || character.compareTo('F') == 0 ||
                character.compareTo('S') == 0 || character.compareTo('H') == 0 ||
                character.compareTo('x') == 0;
    }

    // Trả về bombers
    public Bomber getBomberman() {
        return bomberman;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark += mark;
    }

    public int getHeart() {
        return heart;
    }

    public int getSpeed() {
        return bomberman.getSpeed();
    }

    private void setSpeed(boolean increase) {
        bomberman.setSpeed(increase);
        isSetSpeed = false;
    }

    public int getBomb() {
        return bomb;
    }

    public int getBonusScore() {
        return bonusScore;
    }

    public void setBonusScore(int bonusScore) {
        this.bonusScore = bonusScore;
    }
}