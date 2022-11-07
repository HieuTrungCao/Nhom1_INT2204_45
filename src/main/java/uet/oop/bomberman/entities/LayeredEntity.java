package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;
import java.util.List;

public class LayeredEntity extends Entity {
    List<Entity> layeredList;

    public LayeredEntity(int x, int y) {
        super(x, y);
        layeredList = new LinkedList<>();
    }

    public void addEntity(Entity entity){
        layeredList.add(entity);
    }

    public Entity getTopEntity(){
        return layeredList.get(0);
    }

    public void removeTop() {
        if (layeredList.size() > 1) {
            layeredList.remove(0);
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        for (int i = layeredList.size() - 1; i >= 0; i--)
            layeredList.get(i).render(gc);
    }

    @Override
    public void update() {
        layeredList.get(0).update();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof LayeredEntity) {
            return x == ((LayeredEntity) obj).x && y == ((LayeredEntity) obj).y;
        }

        return false;
    }
}
