package uet.oop.bomberman.entities.destroyable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class DestroyableEntities extends Entity {
    public DestroyableEntities(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public DestroyableEntities(int x, int y) {
        super(x, y);
    }
}
