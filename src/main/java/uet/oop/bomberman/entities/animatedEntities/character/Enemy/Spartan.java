package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Spartan extends Enemy {


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
    public Spartan(int xUnit, int yUnit, Sprite sprite, AI ai) {
        super(xUnit, yUnit, sprite, ai);
        deadSprite = Sprite.spartan_dead;
        this.ai.setCanChangeSpeed(true);
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
    public Spartan(int xUnit, int yUnit, Sprite sprite, AILow ai, Sprite deadSprite, int speed, int point) {
        super(xUnit, yUnit, sprite, ai, deadSprite, speed, point);
    }

    @Override
    public void setImg() {
        int direct = ai.getCurrentDirect();
        if (life == 0) {
            sprite = Sprite.spartan_dead;
        } else if (direct == 0) {
            sprite = Sprite.movingSprite(Sprite.spartan_left1, Sprite.spartan_left2, Sprite.spartan_left3, animate, 20);
        } else if (direct == 1) {
            sprite = Sprite.movingSprite(Sprite.spartan_up1, Sprite.spartan_up2, Sprite.spartan_up3, animate, 20);
        } else if (direct == 2) {
            sprite = Sprite.movingSprite(Sprite.spartan_right1, Sprite.spartan_right2, Sprite.spartan_right3, animate, 20);
        } else {
            sprite = Sprite.movingSprite(Sprite.spartan_down1, Sprite.spartan_down2, Sprite.spartan_down3, animate, 20);
        }
    }

}
