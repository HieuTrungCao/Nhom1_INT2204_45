package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên (tọa độ hàng) trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    //Current Sprite
    protected Sprite sprite;

    /**
     * khởi tạo chuyển tọa độ hàng cột(Unit) sang tọa độ gốc trong canvas
     *
     * @param xUnit cột thứ xUnit
     * @param yUnit hàng thứ yUnit
     */
    public Entity(int xUnit, int yUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
    }

    /**
     * full constructor.
     *
     * @param x      tọa độ gốc trong canvas
     * @param y      tọa độ gốc trong canvas
     * @param sprite sprite để hiển thị hiện tại
     */
    public Entity(int x, int y, Sprite sprite) {
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(sprite.getImage(), x, y);
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
