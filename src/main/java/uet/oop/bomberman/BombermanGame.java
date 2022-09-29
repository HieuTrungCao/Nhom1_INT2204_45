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
import uet.oop.bomberman.entities.animatedEntities.character.Balloom;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.animatedEntities.character.Oneal;
import uet.oop.bomberman.entities.destroyable.BombItem;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.FlameItem;
import uet.oop.bomberman.entities.destroyable.SpeedItem;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.entities.undestroyable.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {

    public static int WIDTH;
    public static int HEIGHT;
    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> characters = new ArrayList<>();
    private GraphicsContext gc;
    private Canvas canvas;
    private Entity bomberman;

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


        characters.add(bomberman);
    }

    private void handleEvent(Scene scene) {
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                int y = bomberman.getY();
                int x = bomberman.getX();
                switch (keyEvent.getCode()) {
                    case UP:
                        System.out.println("up" + bomberman.getY());
                        if (y > 0) bomberman.setY(y - 1);
                        break;
                    case DOWN:
                        bomberman.setY(y + 1);
                        break;
                    case LEFT:
                        bomberman.setX(x - 1);
                        break;
                    case RIGHT:
                        bomberman.setX(x + 1);
                        break;
                }
            }
        };
        scene.addEventHandler(KeyEvent.ANY, eventHandler);

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

        for (int i = 0; i < HEIGHT; ++i) {
            String str = scn.nextLine();
            for (int j = 0; j < WIDTH; ++j) {
                Entity obj;
                char c = str.charAt(j);
                switch (c) {
                    case '#' -> {
                        obj = new Wall(j, i, Sprite.wall.getFxImage());
                        entities.add(obj);
                    }
                    case '*' -> {
                        obj = new Brick(j, i, Sprite.brick.getFxImage());
                        entities.add(obj);
                    }
                    case 'x' -> {
                        entities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        obj = new Portal(j, i, Sprite.portal.getFxImage());
                        entities.add(obj);
                    }
                    case 'p' -> {
                        entities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        bomberman = new Bomber(j, i, Sprite.player_right.getFxImage());
                        characters.add(bomberman);
                    }
                    case '1' -> {
                        entities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        obj = new Balloom(j, i, Sprite.balloom_right1.getFxImage());
                        characters.add(obj);
                    }
                    case '2' -> {
                        entities.add(new Grass(j, i, Sprite.grass.getFxImage()));
                        obj = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        characters.add(obj);
                    }
                    case 'b' -> {
                        obj = new BombItem(j, i, Sprite.powerup_bombs.getFxImage());
                        entities.add(obj);
                    }
                    case 'f' -> {
                        obj = new FlameItem(j, i, Sprite.powerup_flames.getFxImage());
                        entities.add(obj);
                    }
                    case 's' -> {
                        obj = new SpeedItem(j, i, Sprite.powerup_speed.getFxImage());
                        entities.add(obj);
                    }
                    default -> {
                        obj = new Grass(j, i, Sprite.grass.getFxImage());
                        entities.add(obj);
                    }
                }

            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        characters.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        entities.forEach(g -> g.render(gc));
        characters.forEach(g -> g.render(gc));
    }
}
