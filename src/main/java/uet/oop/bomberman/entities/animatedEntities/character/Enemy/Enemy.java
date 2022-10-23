package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends AnimatedEntities {
    protected boolean isAlive = true;
    protected boolean isMoving = false;
    protected Sprite deadSprite;
    protected AI ai;
    protected int point = 100;


    /**
     * khởi tạo chuyển tọa độ hàng cột(Unit) sang tọa độ gốc trong canvas
     *
     * @param xUnit cột thứ xUnit
     * @param yUnit hàng thứ yUnit
     */
//    public Enemy(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//    }

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
        this.ai.setX(xUnit * Sprite.SCALED_SIZE);
        this.ai.setY(yUnit * Sprite.SCALED_SIZE);
    }

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
        ai.setSpeed(speed);
        ai.setPoint(point);
    }

    public abstract void move();

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
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

    public AI getAi() {
        return ai;
    }

    public void setAi(AI ai) {
        this.ai = ai;
        ai.setX(x);
        ai.setY(y);
    }

    public abstract void setImg();

}
