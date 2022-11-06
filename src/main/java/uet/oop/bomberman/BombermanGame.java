package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.AI.AIFly;
import uet.oop.bomberman.AI.AIMedium;
import uet.oop.bomberman.GUI.Game;
import uet.oop.bomberman.GUI.UI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Balloom;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Oneal;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosion;
import uet.oop.bomberman.entities.destroyable.items.BombItem;
import uet.oop.bomberman.entities.destroyable.items.FlameItem;
import uet.oop.bomberman.entities.destroyable.items.SpeedItem;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.entities.undestroyable.Wall;
import uet.oop.bomberman.graphics.Sprite;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;

public class BombermanGame extends Application {
    public static Stage mainStage;
    public static int WIDTH = 31;
    public static int HEIGHT = 13;
    public static List<Entity> entities = new LinkedList<>();
    public static List<Entity> characters = new ArrayList<>();
    private GraphicsContext gc;
    public static Canvas canvas;
    public static List<BombExplosion> bombs = new LinkedList<>();
    public static List<Player> players;
    public Character[][] map;
    private int numOfPlayer = 2;
    public static ImageView player1Life;
    public static int player1Bomb;
    public static ImageView player2Life;
    public static int player2Bomb;
    public static ImageView player1Avatar;
    public static ImageView player2Avatar;

    public static boolean pause = false;
    public static Group root;

    public static AnimationTimer timer;

    public void setNumOfPlayer(int n) {
        numOfPlayer = n;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void createPlayerHud(int pos, int x, int y) throws FileNotFoundException {
        InputStream stream = null;
        if (pos == 0) {
            if (players.get(pos).getNumBomberman() == 0) {
                stream = new FileInputStream("resources/textures/BombermanAvatar1.png");
            } else {
                stream = new FileInputStream("resources/textures/BomberTheKidAvatar1.png");
            }
            player1Avatar = new ImageView(new Image(stream));
            player1Avatar.setX(x);
            player1Avatar.setY(y);
            stream = new FileInputStream("resources/textures/Lives.png");
            player1Life = new ImageView(new Image(stream));
            player1Life.setX(player1Avatar.getX() + 2 * 48);
            player1Life.setY(player1Avatar.getY());
        } else {
            if (players.get(pos).getNumBomberman() == 0) {
                stream = new FileInputStream("resources/textures/BombermanAvatar2.png");
            } else {
                stream = new FileInputStream("resources/textures/BomberTheKidAvatar2.png");
            }
            player2Avatar = new ImageView(new Image(stream));
            player2Avatar.setX(x);
            player2Avatar.setY(y);
            stream = new FileInputStream("resources/textures/Lives.png");
            player2Life = new ImageView(new Image(stream));
            player2Life.setX(player2Avatar.getX() - 2 * 48);
            player2Life.setY(player2Avatar.getY());
        }
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException, URISyntaxException {
        mainStage = stage;
        UI.init();
        //load map từ file txt
        //createMap();
        /**
         * Muốn chọn sprite thì thay giá trị của x1 và x2
         * nhớ chỉ đc thay giá trị 0 hoặc 1
         */
//        players = new ArrayList<>();
//        players.add(new Player(map, 1));
//        createPlayerHud(0, 0, Sprite.SCALED_SIZE * HEIGHT);
//        if (numOfPlayer == 2) {
//            players.add(new Player(map, 1));
//            createPlayerHud(1, Sprite.SCALED_SIZE * WIDTH - 48, Sprite.SCALED_SIZE * HEIGHT);
//        }
//        // Tao Canvas
//        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
//        gc = canvas.getGraphicsContext2D();
//
//        // Create game hud
//        Rectangle hud = new Rectangle();
//        hud.setY(Sprite.SCALED_SIZE * HEIGHT);
//        hud.setWidth(Sprite.SCALED_SIZE * WIDTH);
//        hud.setHeight(Sprite.SCALED_SIZE);
//        var stops = new Stop[] {new Stop(0, Color.web("#81c483")), new Stop(1, Color.web("#fcc200"))};
//        hud.setFill(new LinearGradient(0, Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE, false, CycleMethod.NO_CYCLE, stops));

        // Tao root container
        root = new Group();
        //root.getChildren().add(canvas);
        root.getChildren().add(UI.main);
//        root.getChildren().add(hud);
//        root.getChildren().add(player1Avatar);
//        root.getChildren().add(player1Life);
//        if (numOfPlayer == 2)  {
//            root.getChildren().add(player2Avatar);
//            root.getChildren().add(player2Life);
//        }
        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        mainStage.setTitle("Bomberman");
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(new FileInputStream("resources/textures/GameIcon.png")));
        mainStage.setScene(scene);
        mainStage.show();

//        //vòng lặp của game
//        timer = new AnimationTimer() {
//            @Override
//            public void handle(long l) {
//                if (!pause) {
//                    render();
//                    update();
//                }
//            }
//        };
//        timer.start();
//
//        //xử lý sự kiện
//        handleEvent(scene);
    }

    private void handleEvent(Scene scene) {
        Set<KeyCode> codes = new HashSet<>();
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                codes.add(keyEvent.getCode());
                if (codes.contains(KeyCode.ESCAPE)) {
                    if (!pause) {
                        Management.pauseGame();
                    } else {
                        Management.resumeGame();
                    }
                }
                if (!pause) {
                    for (Player player : players) {
                        player.play(codes);
                    }
                }
            }
        };
        scene.setOnKeyPressed(eventHandler);
        scene.setOnKeyReleased(e -> codes.clear());

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
                        obj = new Balloom(j, i, Sprite.balloom_right1, new AIFly(map));
                        characters.add(obj);
                    }
                    case '2' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                        obj = new Oneal(j, i, Sprite.oneal_left1, new AIMedium(map));
                        characters.add(obj);
                    }
                    case 'b' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new BombItem(j, i, Sprite.powerup_bombs));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'f' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new FlameItem(j, i, Sprite.powerup_flames));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 's' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new SpeedItem(j, i, Sprite.powerup_speed));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
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
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).update();
        }
        for (int i = 0; i < bombs.size(); i++) {
            bombs.get(i).update();
        }
        //characters.forEach(Entity::update);
        //bombs.forEach(Entity::update);
        for (Player player : players) {
            player.update();
        }
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
        for (int i = 0; i < characters.size(); i++) {
            Entity g = characters.get(i);
            g.render(gc);
        }
        for (Player player : players) {
            player.render(gc);
        }
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
