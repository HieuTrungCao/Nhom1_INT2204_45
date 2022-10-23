package uet.oop.bomberman.AI;

import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;

public abstract class AI {

    protected int currentFrame = 0;
    protected boolean isAlive = true;
    protected int currentDirect = 0;
    protected boolean isMoving = false;
    protected Sprite deadSprite;
    //    protected AI ai;
    protected int speed = 2;
    protected int point = 100;
    protected Character[][] map;
    //HashMap map những ký tự đại diện với việc entity đó có cho đi qua không
    protected Character[] block;

    //tọa độ của entity hiện tại
    int x, y;

    public AI(Character[][] map) {
        this.map = map;
        block = new Character[]{'#', '*', 'x', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    public void setMap(Character[][] map) {
        this.map = map;
    }

    public abstract int calculateDirect();

    protected boolean checkDirect(int direct) {
        if (direct == 1) {
            int xFuture1 = x / Sprite.SCALED_SIZE;
            int yFuture1 = (y - speed) / Sprite.SCALED_SIZE;
            int xFuture2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y - speed) / Sprite.SCALED_SIZE;

            return Arrays.stream(block).noneMatch(character -> character == map[yFuture1][xFuture1])
                    && Arrays.stream(block).noneMatch(character -> character == map[yFuture2][xFuture2]);
        }
        if (direct == 2) {
            int xFuture1 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return Arrays.stream(block).noneMatch(character -> character == map[yFuture1][xFuture1])
                    && Arrays.stream(block).noneMatch(character -> character == map[yFuture2][xFuture2]);
        }
        if (direct == 3) {
            int xFuture1 = x / Sprite.SCALED_SIZE;
            int yFuture1 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int xFuture2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return Arrays.stream(block).noneMatch(character -> character == map[yFuture1][xFuture1])
                    && Arrays.stream(block).noneMatch(character -> character == map[yFuture2][xFuture2]);
        }
        if (direct == 0) {
            int xFuture1 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return Arrays.stream(block).noneMatch(character -> character == map[yFuture1][xFuture1])
                    && Arrays.stream(block).noneMatch(character -> character == map[yFuture2][xFuture2]);
        }
        return true;
    }

    public abstract void move();

    public void update() {
        currentFrame++;
        if (currentFrame % 90 == 0) {
            currentDirect = calculateDirect();
        }
    }


    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getCurrentDirect() {
        return currentDirect;
    }

    public void setCurrentDirect(int currentDirect) {
        this.currentDirect = currentDirect;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Sprite getDeadSprite() {
        return deadSprite;
    }

    public void setDeadSprite(Sprite deadSprite) {
        this.deadSprite = deadSprite;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
