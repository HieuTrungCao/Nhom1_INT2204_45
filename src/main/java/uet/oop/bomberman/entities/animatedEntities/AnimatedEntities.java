package uet.oop.bomberman.entities.animatedEntities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class AnimatedEntities extends Entity {

    //bộ đếm thời gian để vẽ hoạt ảnh khi entity chuyển động
    protected int animate = 0;

    public AnimatedEntities(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }


    /**
     * Tránh tràn số
     */
    public void countTime() {
        if (animate < 300) animate++;
        else animate = 0;
    }


    @Override
    public void update() {

    }

}
