package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.FlameItem;
import uet.oop.bomberman.entities.destroyable.HeartItem;
import uet.oop.bomberman.entities.destroyable.SpeedItem;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionNormal;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosionPro;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import static javafx.scene.input.KeyCode.*;

public class Player {
    // Nhân vật Bomber
    public static Bomber bomberman;

    // Lưu điểm
    private int mark;
    // Lưu số mạng của nhân vật
    private int heart;

    // Lưu số lần được tăng tốc
    private int speed;

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
    public Player(Character[][] map) {
        bomberman = new Bomber(1, 1, Sprite.player_right);
        this.map = map;
        this.mark = 0;
        this.heart = 10;
        this.speed = 2;
        this.bombPro = 0;
        this.isSetSpeed = false;
    }

    /**
     * Sử lý sự kiện từ bàn phím để điều khiển bomber
     * @param keyCode mã code của phím
     */
    public void play(KeyCode keyCode) {
        if(!bomberman.isAlive()){
            return;
        }

        if (checkVictory()) {
            // todo something
            System.out.println("Won");
            Sound.bombExplode.close();
            return;
        }

        if((keyCode == UP || keyCode == W) && checkMap(1)) {
            bomberman.moveUp();
            direction = false;
        } else if((keyCode == DOWN || keyCode == S) && checkMap(3)) {
            bomberman.moveDown();
            direction = false;
        } else if((keyCode == LEFT || keyCode == A) && checkMap(4)) {
            bomberman.moveLeft();
            direction = true;
        } else if((keyCode == RIGHT || keyCode == D) && checkMap(2)) {
            bomberman.moveRight();
        } else if(keyCode == SPACE) {
            addBomb(false);
            direction = true;
        } else if(keyCode == P && bombPro > 0) {
            addBomb(true);
            bombPro--;
        } else if (keyCode == R && speed > 0) {
            isSetSpeed = true;
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
                    speed++;
                }

                else if (((LayeredEntity) entity).getTopEntity() instanceof FlameItem) {
                    ((LayeredEntity) entity).removeTop();
                    /**
                     * to do something
                     */
                    bombPro++;
                }

                else if (((LayeredEntity) entity).getTopEntity() instanceof HeartItem) {
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
                bomberman.setX(32);
                bomberman.setY(32);
                bomberman.setSpeed(false);
            }
        }

        if (isSetSpeed) {
            if ((direction && bomberman.getX() % (2 * bomberman.getSpeed()) == 0)
                    || (!direction && bomberman.getY() % (2 * bomberman.getSpeed()) == 0)) {
                setSpeed(true);
                speed--;
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
        if(!pro)
            BombermanGame.bombs.add(new BombExplosionNormal(x, y, null, map));
        else
            BombermanGame.bombs.add(new BombExplosionPro(x, y, null, map));
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
}
