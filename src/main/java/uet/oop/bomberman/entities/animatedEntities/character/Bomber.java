package uet.oop.bomberman.entities.animatedEntities.character;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends AnimatedEntities {

    // Tốc độ, độ dài của mỗi bước di chuyển
    private int speed;

    // Lưu giá trị để chuyển ảnh sau mỗi lần di chuyển
    private final int time_move;

    /**
     * movingUp, movingDown, movingLeft, movingRight
     * để lưu hướng đi của bomber
     * giá trị bằng true tương ứng với đi về hướng đó
     * ngược lại là false
     */
    private boolean movingUp;
    private boolean movingDown;
    private boolean movingRight;
    private boolean movingLeft;

    // Lưu giá trị để chuyển ảnh khi bị quái giết hoặc trong vùng bomb nổ
    private final int time_die;

    // Bộ đếm để chuyển ảnh khi chết
    private int animate_die;

    // Lưu trạng thái của bomber
    private boolean isAlive;

    public Bomber(int x, int y, Sprite sprite) {
        super(x, y, sprite);
        speed = 12;
        isAlive = true;
        animate_die = 0;
        time_die = Bomb.time / 2;
        time_move = 12;
        setDirection(false, false, false, false);
    }

    @Override
    public void update() {

        if(isAlive){
            isAlive = !checkDead();
        }

        if(!isAlive) {
            dead();
        }
    }

    public void moveUp() {
        y -= speed;
        sprite = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1
                , Sprite.player_up_2, animate, time_move);

        super.animate();

        if (!movingUp) {
            setDirection(true, false, false, false);
        }
    }

    public void moveDown() {
        y += speed;
        sprite = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1
                , Sprite.player_down_2, animate, time_move);
        super.animate();

        if (!movingDown) {
            setDirection(false, true, false, false);
        }
    }

    public void moveLeft() {
        x -= speed;
        sprite = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1
                , Sprite.player_left_2, animate, time_move);
        super.animate();

        if (!movingLeft) {
            setDirection(false, false, false, true);
        }
    }

    public void moveRight() {
        x += speed;
        sprite = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1
                , Sprite.player_right_2, animate, time_move);
        super.animate();

        if (!movingRight) {
            setDirection(false, false, true, false);
        }
    }

    /**
     * Lưu hướng đang đi của bomber.
     * Đi hướng nào tương ứng với giá trị true
     * Ngược lại tương ứng vói giá trị false
     * Khi thay đổi hướng đi phải set lại animate = 0;
     */
    private void setDirection(boolean _up, boolean _down, boolean _right, boolean _left) {
        super.animate = 0;
        this.movingUp = _up;
        this.movingDown = _down;
        this.movingRight = _right;
        this.movingLeft = _left;
    }
    /**
     * Setup tốc độ cho bomber
     * Tăng hoặc giảm tốc độ
     * Tốc độ tối thiểu là 4
     * và tối đa là 32
     * @param raise is true or false
     */
    public void setSpeed(boolean raise) {
        if (raise && speed < 32) speed *= 2;
        else if (!raise && speed > 8) speed /= 2;
    }

    // Trả về tốc độ hiện tại
    public int getSpeed() {
        return speed;
    }
    /**
     * Setter cho isAlive.
     * Cập nhật lại trạng thái của bomber
     *
     * @param alive là trạng thái mới
     * alive bằng true tương ứng với bomber sống lại và được chơi tiếp
     * alive bằng false tương ứng với bomber đã chết
     */
    public void setIsAlive(boolean alive) {
        if (alive) {
            animate_die = 0;
            sprite = Sprite.player_right;
        }
        isAlive = alive;
    }

    /**
     * Getter cho isAlive.
     * Trả về trạng thái của bomber
     * còn sống hay đã chết
     * @return true nếu còn sống
     * @return false nếu đã chết
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Kiểm tra bomber có bị quái giết không
     * nếu tọa độ của bomber trùng với tọa độ
     * của quái thì bomber sẽ bị giết
     * Nếu bomber bị giết thì trả vẻ true
     * Ngược lại bomber không bị giết thì trả vể false
     */
    private boolean checkDead() {
        for (Entity entity : BombermanGame.characters) {
            if (entity.getX() == x && entity.getY() == y) {
                return true;
            }
        }

        return false;
    }

    // Update khi bomber bi chet
    public void dead() {
        if(animate_die < time_die) {
            sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate_die++, time_die);
        } else if(!isAlive){
            sprite = Sprite.player_dead3;
            // todo some thing
        }
    }

    /**
     * Getter cho time_die.
     * Để sử lý khi bomber bị chết
     * Có còn mạng hay không
     * Để setup về trạng thái ban đầu
     * @return time_die
     */
    public int getTime_die() {
        return time_die;
    }

    /**
     * Getter cho animated_die.
     * Để sử lý khi bomber bị chết
     * Có còn mạng hay không
     * Để setup về trạng thái ban đầu
     * @return animate_die
     */
    public int getAnimate_die() {
        return animate_die;
    }
}

