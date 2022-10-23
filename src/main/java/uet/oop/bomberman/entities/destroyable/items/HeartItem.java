package uet.oop.bomberman.entities.destroyable.items;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class HeartItem extends Entity {
    /**
     * full constructor.
     *
     * @param x      tọa độ gốc trong canvas
     * @param y      tọa độ gốc trong canvas
     * @param sprite sprite để hiển thị hiện tại
     */
    public HeartItem(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {

    }
}
