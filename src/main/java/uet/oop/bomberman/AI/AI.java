package uet.oop.bomberman.AI;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Arrays;

public abstract class AI {


    protected int currentFrame = 0;
    protected int currentDirect = -1;
    //    protected AI ai;
    protected int speed = 1;

    boolean canChangeSpeed = false;
    public int timePassAUnit = Sprite.SCALED_SIZE / speed;

    protected int timeToUpdateDirect = timePassAUnit * 4;
    protected Character[][] map;
    protected Character[] block;
    //symbol of entity
    protected Character symbol;
    //tọa độ của entity hiện tại
    int x, y;
    //tọa độ của player hiện tại
    int px, py;

    public AI(Character[][] map) {
        this.map = map;
        block = new Character[]{'#', '*', 'x', 'b'};
        px = 1;
        py = 1;
        symbol = '1';
    }


    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void move() {
        int count = 0;
        while (!checkDirect(currentDirect)) {
            if (count > 2) currentDirect = (currentDirect + 1) % 4;
            else {
                currentDirect = calculateDirect();
                count++;
            }
        }
//        if (!checkDirect(currentDirect)){
//
//        };
        updatePositionInMap();
        switch (currentDirect) {
            case 1 -> {
                if (y - speed >= Sprite.SCALED_SIZE)
                    y -= speed;
            }
            case 2 -> {
                if (x + speed <= BombermanGame.WIDTH * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE)
                    x += speed;
            }
            case 3 -> {
                if (y + speed <= BombermanGame.HEIGHT * Sprite.SCALED_SIZE - Sprite.SCALED_SIZE)
                    y += speed;
            }
            case 0 -> {
                if (x - speed >= Sprite.SCALED_SIZE)
                    x -= speed;
            }
        }
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

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (direct == 2) {
            int xFuture1 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (direct == 3) {
            int xFuture1 = x / Sprite.SCALED_SIZE;
            int yFuture1 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int xFuture2 = (x + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + speed + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        if (direct == 0) {
            int xFuture1 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture1 = y / Sprite.SCALED_SIZE;
            int xFuture2 = (x - speed) / Sprite.SCALED_SIZE;
            int yFuture2 = (y + Sprite.SCALED_SIZE - 1) / Sprite.SCALED_SIZE;

            return !isBlock(map[yFuture1][xFuture1]) && !isBlock(map[yFuture2][xFuture2]);
        }
        return true;
    }

    public void update() {
        currentFrame++;
        if (currentFrame % timeToUpdateDirect == 0) {
            currentDirect = calculateDirect();
        }
    }

    protected void updatePositionInMap() {
        int xUnit = x / Sprite.SCALED_SIZE;
        int yUnit = y / Sprite.SCALED_SIZE;
        map[yUnit][xUnit] = ' ';
        if (currentDirect == 0) {
            xUnit = (x - speed) / Sprite.SCALED_SIZE;
            yUnit = y / Sprite.SCALED_SIZE;
        } else if (currentDirect == 1) {
            xUnit = x / Sprite.SCALED_SIZE;
            yUnit = (y - speed) / Sprite.SCALED_SIZE;
        } else if (currentDirect == 2) {
            xUnit = (x + speed) / Sprite.SCALED_SIZE;
            yUnit = y / Sprite.SCALED_SIZE;
        } else if (currentDirect == 3) {
            xUnit = x / Sprite.SCALED_SIZE;
            yUnit = (y + speed) / Sprite.SCALED_SIZE;
        }
        map[yUnit][xUnit] = '1';
    }


    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    public int getCurrentDirect() {
        return currentDirect;
    }

    public void setCurrentDirect(int currentDirect) {
        this.currentDirect = currentDirect;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
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

    protected boolean isBlock(Character y) {
        return Arrays.asList(block).contains(y);
    }
}
