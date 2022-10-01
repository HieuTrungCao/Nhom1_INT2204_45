package uet.oop.bomberman.entities.animatedEntities.character.Enemy;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;

public class Enemy extends AnimatedEntities {


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
     * @param x      tọa độ gốc trong canvas
     * @param y      tọa độ gốc trong canvas
     * @param sprite sprite để hiển thị hiện tại
     */
    public Enemy(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
}
