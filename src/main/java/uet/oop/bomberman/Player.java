package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.items.FlameItem;
import uet.oop.bomberman.entities.destroyable.items.HeartItem;
import uet.oop.bomberman.entities.destroyable.items.SpeedItem;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionNormal;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionPro;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.util.Set;

import static javafx.scene.input.KeyCode.*;

public class Player {
    // Nhân vật Bomber
    private Bomber bomberman;

    // Lưu điểm
    private int mark;
    // Lưu số mạng của nhân vật
    private int heart;

    // Số lượng bomb nhân vật có
    private int bomb;
    // Lưu số lượng của bomb nạp VIP mà nhân vật ăn được
    private int bombPro;

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

    // Đếm số player
    private static short count = 0;

    // Lưu thứ tự lấy sprite
    private short numBomberman;

    // Lưu xem tăng hay giảm speed
    private boolean isIncreaseSpeed;
    public Player(Character[][] map, short numBomberman) {
        bomberman = new Bomber(1, 1, Bomber.player_right[numBomberman], numBomberman);
        this.map = map;
        this.numBomberman = numBomberman;
        this.mark = 0;
        this.heart = 10;
        this.bomb = 10;
        this.bombPro = 0;
        this.isSetSpeed = false;
        this.num = (++ this.count);
        System.out.println(count);
    }

    /**
     * Sử lý sự kiện từ bàn phím để điều khiển bomber
     * @param codes mã code của phím
     */
    public void play(Set<KeyCode> codes) {
        if(!bomberman.isAlive()){
            return;
        }

        if (checkVictory()) {
            // todo something
            System.out.println("Won");
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
                addBomb(false);
                bomb--;
                direction = true;
            } else if (codes.contains(F) && bombPro > 0) {
                addBomb(true);
                bombPro--;
                bomb--;
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
            } else if (codes.contains(J) && bomb > 0) {
                addBomb(false);
                bomb--;
                direction = true;
            } else if (codes.contains(K) && bombPro > 0) {
                addBomb(true);
                bombPro--;
                bomb--;
            }
        }
        eatItem();
    }

    private void setSpeed(boolean increase) {
        bomberman.setSpeed(increase);
        isSetSpeed = false;
    }

    /**
     * Kiểm tra và ăn các item hiện ra khi phá tưởng
     */
    private void eatItem() {
        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;

        Entity entity = BombermanGame.getEntity(x, y);

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
                    isIncreaseSpeed = true;
                }

                else if (((LayeredEntity) entity).getTopEntity() instanceof FlameItem) {
                    ((LayeredEntity) entity).removeTop();
                    /**
                     * to do something
                     */
                    mark += 50;
                    bombPro++;
                }

                else if (((LayeredEntity) entity).getTopEntity() instanceof HeartItem) {
                    mark += 50;
                    heart++;
                }
            }
        }
    }

    /**
     * Kiểm tra xem bomber đã đi vào cổng chưa
     * Nếu đi vào cổng thì chiến thắng
     * @return
     */
    private boolean checkVictory() {
        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;

        Entity entity = BombermanGame.getEntity(x, y);

        if (entity instanceof LayeredEntity) {
            if (((LayeredEntity) entity).getTopEntity() instanceof Portal) {
                if (entity.getX() == bomberman.getX() &&
                    entity.getY() == bomberman.getY())
                    return true;
            }
        }

        return false;
    }

    public void update() {
        if(!bomberman.isAlive()
                && bomberman.getAnimate_die() == bomberman.getTime_die()){
            if (heart > 1) {
                bomberman.setIsAlive(true);
                heart--;
                bomberman.setX(48);
                bomberman.setY(48);
                bomberman.setSpeed(false);
            }
        }

        if (isSetSpeed) {
            if ((direction && bomberman.getX() % (2 * bomberman.getSpeed()) == 0)
                    || (!direction && bomberman.getY() % (2 * bomberman.getSpeed()) == 0)) {
                setSpeed(isIncreaseSpeed);
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
     *        pro is false thì đặt bom loại bình thường
     */
    private void addBomb(boolean pro) {
        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;
        map[y][x] = 'b';
        if(!pro)
            BombermanGame.bombs.add(new BombExplosionNormal(x, y, null, num ,map));
        else
            BombermanGame.bombs.add(new BombExplosionPro(x, y, null, num, map));
    }

    // kiem tra xem co bi vuong gi khong
    // neu vuong thi khong di duoc
    // top : 1
    // right : 2
    // bottom : 3
    // left : 4
    private boolean checkMap(int s) {

        int x = bomberman.getX() / Sprite.SCALED_SIZE;
        int y = bomberman.getY() / Sprite.SCALED_SIZE;

        if (s == 1) {
            if(x * Sprite.SCALED_SIZE != bomberman.getX()) {
                return false;
            }
            if (y * Sprite.SCALED_SIZE == bomberman.getY()) {
                return Character.valueOf(map[y - 1][x]).compareTo('#') != 0 &&
                        Character.valueOf(map[y - 1][x]).compareTo('*') != 0;
            }
        }

        if (s == 2) {
            if (y * Sprite.SCALED_SIZE != bomberman.getY()){
                return false;
            }

            if(x * Sprite.SCALED_SIZE == bomberman.getX()){
                return Character.valueOf(map[y][x + 1]).compareTo('#') != 0 &&
                        Character.valueOf(map[y][x + 1]).compareTo('*') != 0;
            }
        }

        if (s == 3) {
            if(x * Sprite.SCALED_SIZE != bomberman.getX()) {
                return false;
            }
            if (y * Sprite.SCALED_SIZE == bomberman.getY()) {
                return Character.valueOf(map[y + 1][x]).compareTo('#') != 0 &&
                        Character.valueOf(map[y + 1][x]).compareTo('*') != 0;
            }
        }

        if (s == 4) {
            if (y * Sprite.SCALED_SIZE != bomberman.getY()){
                return false;
            }

            if(x * Sprite.SCALED_SIZE == bomberman.getX()){
                return Character.valueOf(map[y][x - 1]).compareTo('#') != 0 &&
                        Character.valueOf(map[y][x - 1]).compareTo('*') != 0;
            }
        }

        return true;
    }

    // Trả về bombers
    public Bomber getBomberman() {
        return bomberman;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getHeart() {
        return heart;
    }

    public int getSpeed() {
        return bomberman.getSpeed();
    }

    public int getBomb() {
        return bomb;
    }

    public int getFlame() {
        return bombPro;
    }

    public void setSpeedLevel(int speed) {
        if (speed < bomberman.getSpeed()) {
            isSetSpeed = true;
            isIncreaseSpeed = false;    
        } else if (speed > bomberman.getSpeed()) {
            isSetSpeed = true;
            isIncreaseSpeed = true;
        }
    }
}
