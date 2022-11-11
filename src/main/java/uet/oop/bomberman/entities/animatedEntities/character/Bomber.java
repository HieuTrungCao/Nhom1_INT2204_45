package uet.oop.bomberman.entities.animatedEntities.character;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
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

    private short num;

    private static final Sprite[] player_up = {Sprite.player1_up, Sprite.player2_up};
    private static final Sprite[] player_up_1 = {Sprite.player1_up_1, Sprite.player2_up_1};
    private static final Sprite[] player_up_2 = {Sprite.player1_up_2, Sprite.player2_up_2};

    private static final Sprite[] player_down = {Sprite.player1_down, Sprite.player2_down};
    private static final Sprite[] player_down_1 = {Sprite.player1_down_1, Sprite.player2_down_1};
    private static final Sprite[] player_down_2 = {Sprite.player1_down_2, Sprite.player2_down_2};

    public static final Sprite[] player_right = {Sprite.player1_right, Sprite.player2_right};
    private static final Sprite[] player_right_1 = {Sprite.player1_right_1, Sprite.player2_right_1};
    private static final Sprite[] player_right_2 = {Sprite.player1_right_2, Sprite.player2_right_2};

    private static final Sprite[] player_left = {Sprite.player1_left, Sprite.player2_left};
    private static final Sprite[] player_left_1 = {Sprite.player1_left_1, Sprite.player2_left_1};
    private static final Sprite[] player_left_2 = {Sprite.player1_left_2, Sprite.player2_left_2};

    private static final Sprite[] player_dead_1 = {Sprite.player1_dead1, Sprite.player2_dead1};
    private static final Sprite[] player_dead_2 = {Sprite.player1_dead2, Sprite.player2_dead2};
    private static final Sprite[] player_dead_3 = {Sprite.player1_dead3, Sprite.player2_dead3};

    public Bomber(int x, int y, Sprite sprite, short num) {
        super(x, y, sprite);
        speed = 12;
        isAlive = true;
        animate_die = 0;
        time_die = Bomb.time / 2;
        time_move = 3;
        setDirection(false, false, false, false);
        this.num = (num);
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
        sprite = Sprite.movingSprite(player_up[num], player_up_1[num]
                , player_up_2[num], animate, time_move);

        super.animate();

        if (!movingUp) {
            setDirection(true, false, false, false);
        }
    }

    public void moveDown() {
        y += speed;
        sprite = Sprite.movingSprite(player_down[num], player_down_1[num]
                , player_down_2[num], animate, time_move);
        super.animate();

        if (!movingDown) {
            setDirection(false, true, false, false);
        }
    }

    public void moveLeft() {
        x -= speed;
        sprite = Sprite.movingSprite(player_left[num], player_left_1[num]
                , player_left_2[num], animate, time_move);
        super.animate();

        if (!movingLeft) {
            setDirection(false, false, false, true);
        }
    }

    public void moveRight() {
        x += speed;
        sprite = Sprite.movingSprite(player_right[num], player_right_1[num]
                , player_right_2[num], animate, time_move);
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
        if (raise && speed < 48) speed *= 2;
        else if (!raise && speed > 12) speed /= 2;
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
            sprite = player_right[num];
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
        for (Entity entity : Management.characters) {
            if (entity.getY() == y) {
                if ((x + Sprite.DEFAULT_SIZE) > entity.getX() &&
                        entity.getX() > x) {
                    return true;
                }
                if ((entity.getX() + Sprite.DEFAULT_SIZE) > x &&
                        entity.getX() < x) {
                    return true;
                }
            }

            if (entity.getX() == x) {
                if ((y + Sprite.DEFAULT_SIZE) > entity.getY() &&
                        entity.getY() > y) {
                    return true;
                }
                if ((entity.getY() + Sprite.DEFAULT_SIZE) > y &&
                        entity.getY() < y) {
                    return true;
                }
            }
        }

        return false;
    }

    // Update khi bomber bi chet
    public void dead() {
        if(animate_die < time_die) {
            sprite = Sprite.movingSprite(player_dead_1[num], player_dead_2[num], player_dead_3[num], animate_die++, time_die);
        } else if(!isAlive){
            sprite = player_dead_3[num];
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