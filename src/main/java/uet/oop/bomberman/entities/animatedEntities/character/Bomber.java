package uet.oop.bomberman.entities.animatedEntities.character;

import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends AnimatedEntities {

    private int speed = 1;
    private final int time_move = 6;
    private final int time_die = 100;
    private int animate_die = 0;
    private boolean isAlive = true;

    public Bomber(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if(!isAlive) {
            dead();
        }
    }

    public void moveUp() {
        y -= 4*speed;
        sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1
                , Sprite.player_up_2, animate, time_move);
        animate();
    }

    public void moveDown() {
        y += 4*speed;
        sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1
                , Sprite.player_down_2, animate, time_move);
        animate();
    }

    public void moveLeft() {
        x -= 4*speed;
        sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1
                , Sprite.player_left_2, animate, time_move);
        animate();
    }

    public void moveRight() {
        x += 4*speed;
        sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1
                , Sprite.player_right_2, animate, time_move);
        animate();
    }

    /**
     *
     * @param raise is true or false
     */
    private void setSpeed(boolean raise) {

    }

    public void setIsAlive() {
        isAlive = false;
    }

    public void dead() {
        if(animate_die < time_die) {
            sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate_die++, time_die);
        } else {
            sprite = null;
            // todo some thing
        }
    }
}

