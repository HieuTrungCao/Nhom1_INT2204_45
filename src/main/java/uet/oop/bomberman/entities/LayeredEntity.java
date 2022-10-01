package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;

import java.util.LinkedList;

public class LayeredEntity extends Entity {
    LinkedList<Entity> layeredList;

    public LayeredEntity(int x, int y) {
        super(x, y);
        layeredList = new LinkedList<>();
    }

    public void addEntity(Entity entity){
        layeredList.add(entity);
    }

    public Entity getTopEntity(){
        return layeredList.getFirst();
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getTopEntity().getSprite().getFxImage() ,getTopEntity().x, getTopEntity().y);
    }

    @Override
    public void update() {

    }
}
