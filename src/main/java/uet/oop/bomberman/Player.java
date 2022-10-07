package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.destroyable.BombExplosionNormal;
import uet.oop.bomberman.entities.destroyable.BombExplosionPro;
import uet.oop.bomberman.graphics.Sprite;

import static javafx.scene.input.KeyCode.*;

public class Player {
    public static Bomber bomberman;
    private int mark;

    private Character[][] map;

    public Player(Character[][] map) {
        bomberman = new Bomber(1, 1, Sprite.player_right);
        mark = 0;
        this.map = map;
    }

    public void play(KeyCode keyCode) {
        if(!bomberman.isAlive())
            return;

        if(keyCode == UP && checkMap(1)) {
            bomberman.moveUp();
        } else if(keyCode == DOWN && checkMap(3)) {
            bomberman.moveDown();
        } else if(keyCode == LEFT && checkMap(4)) {
            bomberman.moveLeft();
        } else if(keyCode == RIGHT && checkMap(2)) {
            bomberman.moveRight();
        } else if(keyCode == SPACE) {
            addBomb(false);
        } else if(keyCode == P) {
            addBomb(true);
        }
    }

    public void update() {
        bomberman.update();
    }
    public void render(GraphicsContext gc) {
        bomberman.render(gc);
    }

    // them bomb vao vi tri dang dung
    // su ly bom no
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
}
