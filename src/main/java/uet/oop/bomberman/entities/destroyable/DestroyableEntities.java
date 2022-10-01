package uet.oop.bomberman.entities.destroyable;

import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.entities.Entity;

public abstract class DestroyableEntities extends Entity {
    public DestroyableEntities(int xUnit, int yUnit, Sprite sprite) {
        super(xUnit, yUnit, sprite);
    }

    public DestroyableEntities(int x, int y) {
        super(x, y);
    }
}
