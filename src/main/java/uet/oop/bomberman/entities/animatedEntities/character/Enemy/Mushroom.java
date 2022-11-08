package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import uet.oop.bomberman.AI.AI;
import uet.oop.bomberman.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Mushroom extends Enemy {


    //    public Mushroom(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//    }

    /**
     * full constructor.
     *
     * @param xUnit  tọa độ hàng x
     * @param yUnit  tọa độ cột y
     * @param sprite sprite để hiển thị hiện tại
     */
    public Mushroom(int xUnit, int yUnit, Sprite sprite, AI ai) {
        super(xUnit, yUnit, sprite, ai);
        deadSprite = Sprite.mushroom_dead;
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
    public Mushroom(int xUnit, int yUnit, Sprite sprite, AILow ai, Sprite deadSprite, int speed, int point) {
        super(xUnit, yUnit, sprite, ai, deadSprite, speed, point);
    }


    @Override
    public void setImg() {
        int direct = ai.getCurrentDirect();
        if (life == 0) {
            sprite = Sprite.mushroom_dead;
        } else if (direct == 0) {
            sprite = Sprite.movingSprite(Sprite.mushroom_left1, Sprite.mushroom_left2, Sprite.mushroom_left3, animate, 20);
        } else if (direct == 1) {
            sprite = Sprite.movingSprite(Sprite.mushroom_up1, Sprite.mushroom_up2, Sprite.mushroom_up3, animate, 20);
        } else if (direct == 2) {
            sprite = Sprite.movingSprite(Sprite.mushroom_right1, Sprite.mushroom_right2, Sprite.mushroom_right3, animate, 20);
        } else {
            sprite = Sprite.movingSprite(Sprite.mushroom_down1, Sprite.mushroom_down2, Sprite.mushroom_down3, animate, 20);
        }
    }
}


