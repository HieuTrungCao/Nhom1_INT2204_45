package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {


    //    public Balloom(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//    }

    /**
     * full constructor.
     *
     * @param xUnit  tọa độ hàng x
     * @param yUnit  tọa độ cột y
     * @param sprite sprite để hiển thị hiện tại
     */
    public Balloom(int xUnit, int yUnit, Sprite sprite, AI ai) {
        super(xUnit, yUnit, sprite, ai);
        deadSprite = Sprite.balloom_dead;
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
    public Balloom(int xUnit, int yUnit, Sprite sprite, AILow ai, Sprite deadSprite, int speed, int point) {
        super(xUnit, yUnit, sprite, ai, deadSprite, speed, point);
    }

    @Override
    public void update() {
        if (isAlive) {
            animate();
            ai.update();
            move();
            setImg();
        } else dead();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }

    @Override
    public void move() {
        ai.move();
        x = ai.getX();
        y = ai.getY();
    }

    @Override
    public void setImg() {
        int direct = ai.getCurrentDirect();
        if (!isAlive) {
            sprite = Sprite.balloom_dead;
        } else if (direct == 2) {
            sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 30);
        } else {
            sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 30);
        }
    }

}
