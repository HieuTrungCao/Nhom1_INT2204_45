package uet.oop.bomberman.entities.animatedEntities;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public abstract class AnimatedEntities extends Entity {

    //bộ đếm thời gian để vẽ hoạt ảnh khi entity chuyển động
    protected int animate = 0;

    protected final static int MAX_ANIMATE = 3000;

    /**
     * khởi tạo chuyển tọa độ hàng cột(Unit) sang tọa độ gốc trong canvas
     *
     * @param xUnit cột thứ xUnit
     * @param yUnit hàng thứ yUnit
     */
//    public AnimatedEntities(int xUnit, int yUnit) {
//        super(xUnit, yUnit);
//        animate = 0;
//    }

    /**
     * full constructor.
     *
     * @param x      tọa độ gốc trong canvas
     * @param y      tọa độ gốc trong canvas
     * @param sprite sprite để hiển thị hiện tại
     */
    public AnimatedEntities(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        animate = 0;
    }


    /**
     * Bắt đầu thể hiện hoạt ảnh
     */
    public void animate() {
        if (animate < MAX_ANIMATE) animate++;
        else animate = 0;
    }


    @Override
    public void update() {

    }

    public void setAnimate(int animate) {
        this.animate = animate;
    }

    public int getAnimate() {
        return animate;
    }
}
