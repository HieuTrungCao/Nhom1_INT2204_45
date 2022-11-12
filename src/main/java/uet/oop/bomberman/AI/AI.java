package uet.oop.bomberman.AI;

import javafx.util.Pair;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AI {


    protected int currentFrame = 0;
    protected int currentDirect = -1;
    //    protected AI ai;
    protected int speed = 1;
    public int timePassAUnit = Sprite.SCALED_SIZE / speed;
    protected int timeToUpdateDirect = timePassAUnit * 4;
    protected Character[][] map;
    protected Character[] block;
    //symbol of entity
    protected Character symbol;
    protected boolean cantFly = true;
    boolean canChangeSpeed = false;
    boolean canSlowPlayer = false;
    //tọa độ của entity hiện tại
    int x, y;
    //tọa độ của player hiện tại
    int px, py;

    public AI(Character[][] map) {
        this.map = map;
        block = new Character[]{'#', '*', 'x', 'b', 'B', 'F', 'S', 'H'};
        px = 1;
        py = 1;
        symbol = '1';
    }

    public boolean isCanChangeSpeed() {
        return canChangeSpeed;
    }

    public void setCanChangeSpeed(boolean canChangeSpeed) {
        this.canChangeSpeed = canChangeSpeed;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void move() {
        if (cantFly) changeDirectIfCollision();
        if (canSlowPlayer) slowPlayer();


//        updatePositionInMap();
        switch (currentDirect) {
            case 1 -> {
                if (y - speed >= Sprite.SCALED_SIZE)
                    y -= speed;
            }
            case 2 -> {
                if (x + speed <= Management.WIDTH * Sprite.SCALED_SIZE - 2 * Sprite.SCALED_SIZE)
                    x += speed;
            }
            case 3 -> {
                if (y + speed <= Management.HEIGHT * Sprite.SCALED_SIZE - 2 * Sprite.SCALED_SIZE)
                    y += speed;
            }
            case 0 -> {
                if (x - speed >= Sprite.SCALED_SIZE)
                    x -= speed;
            }
        }
    }

    private void slowPlayer() {
    }

    protected void changeDirectIfCollision() {
        int count = 0;
        while (!checkDirect(currentDirect)) {
            if (count > 3) currentDirect = -1;
            else if (count > 1) currentDirect = (currentDirect + 1) % 4;
            else {
                currentDirect = calculateDirect();
                count++;
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
            if (canChangeSpeed) {
                speed = Math.abs(px - x) + Math.abs(py - y) < 6 * Sprite.SCALED_SIZE ? 2 : 1;
                timePassAUnit = Sprite.SCALED_SIZE / speed;
                timeToUpdateDirect = timePassAUnit;
            }
            currentDirect = calculateDirect();
            currentFrame = 0;
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

    protected void updateNearestPlayer() {
        List<Pair<Integer, Integer>> playerCoordinate = new ArrayList<>();
        for (int i = 0; i < Management.players.size(); ++i) {
            playerCoordinate.add(new Pair<>(
                    Management.players.get(i).getBomberman().getX(), Management.players.get(i).getBomberman().getY()));
        }

        int minDistance = Integer.MAX_VALUE;
        for (Pair<Integer, Integer> coordinate : playerCoordinate) {
            int distance = Math.abs(coordinate.getKey() - x) + Math.abs(coordinate.getValue() - y);
            if (distance < minDistance) {
                minDistance = distance;
                px = coordinate.getKey();
                py = coordinate.getValue();
            }
        }
    }
}
