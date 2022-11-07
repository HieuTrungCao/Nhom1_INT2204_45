package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;


public abstract class Enemy extends AnimatedEntities {
    protected boolean isAlive = true;
    protected Sprite deadSprite;
    protected AI ai;
    protected int point = 100;

    protected int life = 1;

    protected int timeToRemove = 30;

    /**
     * full constructor.
     *
     * @param xUnit  tọa độ hàng x
     * @param yUnit  tọa độ cột y
     * @param sprite sprite để hiển thị hiện tại
     */
    public Enemy(int xUnit, int yUnit, Sprite sprite, AI ai) {
        super(xUnit, yUnit, sprite);
        this.ai = ai;
        this.x = xUnit * Sprite.SCALED_SIZE + 1;
        this.y = yUnit * Sprite.SCALED_SIZE + 1;
        this.ai.setX(xUnit * Sprite.SCALED_SIZE);
        this.ai.setY(yUnit * Sprite.SCALED_SIZE);
    }


    //    public Enemy(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//    }

    /**
     * full constructor.
     *
     * @param xUnit      tọa độ hàng x
     * @param yUnit      tọa độ cột y
     * @param sprite     sprite để hiển thị hiện tại
     * @param deadSprite sprite khi chết
     * @param speed      tốc độ di chuyển
     * @param point      điểm
     */
    public Enemy(int xUnit, int yUnit, Sprite sprite, AI ai, Sprite deadSprite, int speed, int point) {
        super(xUnit, yUnit, sprite);
        this.deadSprite = deadSprite;
        this.ai = ai;
        this.point = point;
        ai.setSpeed(speed);
    }

    public int getPoint() {
        return point;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    public Sprite getDeadSprite() {
        return deadSprite;
    }

    public void setDeadSprite(Sprite deadSprite) {
        this.deadSprite = deadSprite;
    }

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
        ai.setX(x);
        ai.setY(y);
    }

    public abstract void setImg();

    public void dead() {
        setImg();
        if (timeToRemove == 0) {
            Management.characters.removeIf(character -> character.equals(this));
        } else {
            timeToRemove--;
        }
    }

    public void minusLife() {
        life--;
    }

    @Override
    public void update() {
        if (life > 0) {
            animate();
            ai.update();
            move();
            setImg();
        } else {
            isAlive = false;
            dead();
        }
    }


    public void move() {
        ai.move();
        x = ai.getX();
        y = ai.getY();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

}
