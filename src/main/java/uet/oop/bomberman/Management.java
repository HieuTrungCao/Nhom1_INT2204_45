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
import uet.oop.bomberman.GUI.UI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Balloom;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Oneal;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
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
import java.util.*;

public class Management {
    public static Stage mainStage = BombermanGame.mainStage;
    public static int WIDTH = 31;
    public static int HEIGHT = 14;
    public static List<Entity> entities = new LinkedList<>();
    public static List<Entity> characters = new ArrayList<>();
    public static GraphicsContext gc;
    public static Canvas canvas;
    public static List<BombExplosion> bombs = new LinkedList<>();
    public static List<Player> players = new ArrayList<>();
    public static Character[][] map;
    public static ImageView player1Life;
    public static int player1Bomb;
    public static ImageView player2Life;
    public static int player2Bomb;
    public static ImageView player1Avatar;
    public static ImageView player2Avatar;
    private static int numOfPlayer;
    public static boolean pause = false;
    public static Group root;

    public static Scene scene;

    public static AnimationTimer timer;

    public static void setNumOfPlayer(int n) {
        numOfPlayer = n;
    }

    public static Scene getScene() {
        return scene;
    }
    public static void init() {
        root = new Group();
        scene = new Scene(root);
    }
    public static void createPlayerHud(int pos, int x, int y) throws FileNotFoundException {
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

    public static void pauseGame() {
        pause = true;
        root.getChildren().add(UI.pause);
    }

    public static void resumeGame() {
        root.getChildren().remove(UI.pause);
        pause = false;
    }

    public static void startGame() {
        init();
        root.getChildren().add(UI.main);
    }

    public static void restart() {
        pause = false;
        timer.stop();
        entities.clear();
        characters.clear();
        bombs.clear();
        players.clear();
    }

    public static void chooseCharacter() {
        root.getChildren().remove(UI.main);
        root.getChildren().add(UI.characters);
    }

    public static void startPVE(int id) throws FileNotFoundException {
        setNumOfPlayer(1);
        createMap();
        players.add(new Player(map, (short) id));
        createPlayerHud(0, 0, HEIGHT * Sprite.SCALED_SIZE);
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create game hud
        Rectangle hud = new Rectangle();
        hud.setY(Sprite.SCALED_SIZE * HEIGHT);
        hud.setWidth(Sprite.SCALED_SIZE * WIDTH);
        hud.setHeight(Sprite.SCALED_SIZE);
        var stops = new Stop[] {new Stop(0, Color.web("#81c483")), new Stop(1, Color.web("#fcc200"))};
        hud.setFill(new LinearGradient(0, Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE, false, CycleMethod.NO_CYCLE, stops));

        root.getChildren().remove(UI.characters);
        root.getChildren().addAll(canvas, hud, player1Avatar, player1Life);
        scene.setRoot(root);
        mainStage.setScene(scene);
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!pause) {
                    update();
                    render();
                }
            }
        };
        timer.start();
        handleEvent(scene);
    }

    static void handleEvent(Scene scene) {
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

    public static void createMap() {
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

    public static void update() {
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

    public static void render() {
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
