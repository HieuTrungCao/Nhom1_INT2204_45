package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {


    //    public Oneal(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//    }

    /**
     * full constructor.
     *
     * @param xUnit  tọa độ hàng x
     * @param yUnit  tọa độ cột y
     * @param sprite sprite để hiển thị hiện tại
     */
    public Oneal(int xUnit, int yUnit, Sprite sprite, AI ai) {
        super(xUnit, yUnit, sprite, ai);
        deadSprite = Sprite.oneal_dead;
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
    public Oneal(int xUnit, int yUnit, Sprite sprite, AILow ai, Sprite deadSprite, int speed, int point) {
        super(xUnit, yUnit, sprite, ai, deadSprite, speed, point);
    }


    @Override
    public void setImg() {
        int direct = ai.getCurrentDirect();
        if (life == 0) {
            sprite = Sprite.oneal_dead;
        } else if (direct == 0) {
            sprite = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 20);
        } else if (direct == 1) {
            sprite = Sprite.movingSprite(Sprite.oneal_up1, Sprite.oneal_up2, Sprite.oneal_up3, animate, 20);
        } else if (direct == 2) {
            sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 20);
        } else {
            sprite = Sprite.movingSprite(Sprite.oneal_down1, Sprite.oneal_down2, Sprite.oneal_down3, animate, 20);
        }
    }
}


