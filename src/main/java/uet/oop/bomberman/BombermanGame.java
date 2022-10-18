package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Balloom;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Oneal;
import uet.oop.bomberman.entities.destroyable.BombItem;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.FlameItem;
import uet.oop.bomberman.entities.destroyable.SpeedItem;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosion;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.entities.undestroyable.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BombermanGame extends Application {

    public static int WIDTH;
    public static int HEIGHT;
    private final static List<Entity> entities = new LinkedList<>();
    public static final List<Entity> characters = new ArrayList<>();
    private GraphicsContext gc;
    private Canvas canvas;

    public static List<BombExplosion> bombs = new LinkedList<>();

    private Player player;

    private Character[][] map;
    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        //load map từ file txt
        createMap();

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        //vòng lặp của game
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        //xử lý sự kiện
        handleEvent(scene);

        player = new Player(map);
    }

    private void handleEvent(Scene scene) {
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                player.play(keyEvent.getCode());
            }
        };
        scene.setOnKeyPressed(eventHandler);

    }

    public void createMap() {
        File file = new File("resources/levels/Level1.txt");
        Scanner scn = null;
        try {
            scn = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int lvl = scn.nextInt();
        HEIGHT = scn.nextInt();
        WIDTH = scn.nextInt();
        String tmp = scn.nextLine();

        System.out.println(HEIGHT + ", " + WIDTH);

        map = new Character[HEIGHT][WIDTH];

        for (int i = 0; i < HEIGHT; ++i) {
            String str = scn.nextLine();
            for (int j = 0; j < WIDTH; ++j) {
                Entity obj;
                char c = str.charAt(j);
                map[i][j] = c;
                switch (c) {
                    case '#' -> {
                        obj = new Wall(j, i, Sprite.wall);
                        entities.add(obj);
                    }
                    case '*' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'p' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                    }
                    case '1' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                        obj = new Balloom(j, i, Sprite.balloom_right1);
                        characters.add(obj);
                    }
                    case '2' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                        obj = new Oneal(j, i, Sprite.oneal_left1);
                        characters.add(obj);
                    }
                    case 'b' -> {
                        obj = new BombItem(j, i, Sprite.powerup_bombs);
                        entities.add(obj);
                    }
                    case 'f' -> {
                        obj = new FlameItem(j, i, Sprite.powerup_flames);
                        entities.add(obj);
                    }
                    case 's' -> {
                        obj = new SpeedItem(j, i, Sprite.powerup_speed);
                        entities.add(obj);
                    }
                    case 'x' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity) obj).addEntity(new Portal(j, i, Sprite.portal));
                        ((LayeredEntity) obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    default -> {
                        obj = new Grass(j, i, Sprite.grass);
                        entities.add(obj);
                    }
                }

            }
        }
    }

    public void update() {
        //entities.forEach(Entity::update);
        characters.forEach(Entity::update);
        bombs.forEach(Entity::update);
        player.update();
    }

    public static LayeredEntity getBrick(LayeredEntity brick) {
        for (Entity e : entities) {
            if (brick.equals(e)) {
                return (LayeredEntity) e;
            }
        }
        return null;
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gc));
        characters.forEach(g -> g.render(gc));
        player.render(gc);
        bombs.forEach(b -> b.render(gc));
    }

    public static Entity getEntity(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x * Sprite.SCALED_SIZE && e.getY() == y * Sprite.SCALED_SIZE) {
                return e;
            }
        }
        return null;
    }
}
