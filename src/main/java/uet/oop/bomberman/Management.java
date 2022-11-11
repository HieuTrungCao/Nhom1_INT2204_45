package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.AI.AIFly;
import uet.oop.bomberman.AI.AILow;
import uet.oop.bomberman.AI.AIMedium;
import uet.oop.bomberman.GUI.UI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Spartan;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Ufo;
import uet.oop.bomberman.entities.animatedEntities.character.Enemy.Mushroom;
import uet.oop.bomberman.entities.destroyable.Brick;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosion;
import uet.oop.bomberman.entities.destroyable.items.BombItem;
import uet.oop.bomberman.entities.destroyable.items.FlameItem;
import uet.oop.bomberman.entities.destroyable.items.HeartItem;
import uet.oop.bomberman.entities.destroyable.items.SpeedItem;
import uet.oop.bomberman.entities.undestroyable.Grass;
import uet.oop.bomberman.entities.undestroyable.Portal;
import uet.oop.bomberman.entities.undestroyable.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.Sound;

import java.io.*;
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
    private static ImageView p1Avatar;
    private static ImageView p1Life;
    private static ImageView p1Bomb;
    private static Text p1numLife;
    private static Text p1numBomb;
    private static Text p1score;
    private static ImageView p2Avatar;
    private static ImageView p2Life;
    private static ImageView p2Bomb;
    private static Text p2numLife;
    private static Text p2numBomb;
    private static Text p2score;
    private static int numOfPlayer;
    private static boolean pause = false;
    private static boolean ingame = false;
    private static boolean isPVP = false;
    public static Group root;
    public static Scene scene;
    public static AnimationTimer timer;
    private static int currentLevel;
    public static int lv1HighScore;
    public static int lv2HighScore;
    public static int lv3HighScore;
    public static int pvpHighScore;
    public static int numberBomberMan;
    public static int numberBomberTheKid;
    public static void setNumOfPlayer(int n) {
        numOfPlayer = n;
    }

    public static Scene getScene() {
        return scene;
    }
    public static void init() throws FileNotFoundException {
        root = new Group();
        scene = new Scene(root);
        Scanner sc = new Scanner(new File("resources/levels/currentLevel.txt"));
        currentLevel = sc.nextInt();
        sc = new Scanner(new File("resources/statistic/statistic.txt"));
        lv1HighScore = sc.nextInt();
        lv2HighScore = sc.nextInt();
        lv3HighScore = sc.nextInt();
        pvpHighScore = sc.nextInt();
        numberBomberMan = sc.nextInt();
        numberBomberTheKid = sc.nextInt();
    }
    public static void createPlayerHud(int pos, int x, int y) throws FileNotFoundException {
        InputStream stream1 = null;
        InputStream stream2 = new FileInputStream("resources/textures/Lives.png");
        InputStream stream3 = new FileInputStream("resources/textures/GameIcon.png");
        if (pos == 0) {
            if (players.get(pos).getNumBomberman() == 0) {
                stream1 = new FileInputStream("resources/textures/BombermanAvatar1.png");
            } else {
                stream1 = new FileInputStream("resources/textures/BomberTheKidAvatar1.png");
            }
            p1Avatar = new ImageView(new Image(stream1));
            p1Avatar.setX(x);
            p1Avatar.setY(y);

            p1Life = new ImageView(new Image(stream2));
            p1Life.setX(p1Avatar.getX() + 2 * 48);
            p1Life.setY(p1Avatar.getY());

            p1Bomb = new ImageView(new Image(stream3));
            p1Bomb.setX(p1Life.getX() + 3 * 48);
            p1Bomb.setY(p1Life.getY());

            p1numLife = new Text(Integer.toString(players.get(0).getHeart()));
            p1numLife.setFont(UI.getHudFont());
            p1numLife.relocate(p1Life.getX() + 48 * 1.5, 630);

            p1numBomb = new Text(String.format("%02d", players.get(0).getBomb()));
            p1numBomb.setFont(UI.getHudFont());
            p1numBomb.relocate(p1Bomb.getX() + 48 * 1.5, 630);

            p1score = new Text(String.format("%06d", players.get(0).getMark()));
            p1score.setFont(UI.getHudFont());
            p1score.relocate(p1Bomb.getX() + 48 * 3, 630);
        } else {
            if (players.get(pos).getNumBomberman() == 0) {
                stream1 = new FileInputStream("resources/textures/BombermanAvatar2.png");
            } else {
                stream1 = new FileInputStream("resources/textures/BomberTheKidAvatar2.png");
            }
            p2Avatar = new ImageView(new Image(stream1));
            p2Avatar.setX(x);
            p2Avatar.setY(y);

            p2Life = new ImageView(new Image(stream2));
            p2Life.setX(p2Avatar.getX() - 2 * 48);
            p2Life.setY(p2Avatar.getY());

            p2Bomb = new ImageView(new Image(stream3));
            p2Bomb.setX(p2Life.getX() - 3 * 48);
            p2Bomb.setY(p1Life.getY());

            p2numLife = new Text(Integer.toString(players.get(1).getHeart()));
            p2numLife.setFont(UI.getHudFont());
            p2numLife.relocate(p2Life.getX() - 48 * 1.5, 630);

            p2numBomb = new Text(String.format("%02d", players.get(1).getBomb()));
            p2numBomb.setFont(UI.getHudFont());
            p2numBomb.relocate(p2Bomb.getX() - 48 * 1.5, 630);

            p2score = new Text(String.format("%06d", players.get(1).getMark()));
            p2score.setFont(UI.getHudFont());
            p2score.relocate(p2Bomb.getX() - 48 * 5.5, 630);
        }
    }

    public static void hudUpdate() {
        p1numLife.setText(Integer.toString(players.get(0).getHeart()));
        p1numBomb.setText(String.format("%02d", players.get(0).getBomb()));
        p1score.setText(String.format("%06d", players.get(0).getMark()));
        if (isPVP) {
            p2numLife.setText(Integer.toString(players.get(1).getHeart()));
            p2numBomb.setText(String.format("%02d", players.get(1).getBomb()));
            p2score.setText(String.format("%06d", players.get(1).getMark()));
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

    public static void gameOver() {
        pause = true;
        root.getChildren().add(UI.gameOver);
        Sound.gameOver.start();
        Sound.gameOver.restart();
    }

    public static void gameClear() throws IOException {
        pause = true;
        UI.initGameClear();
        root.getChildren().add(UI.gameClear);
        Sound.gameClear.start();
        Sound.gameClear.restart();
        updateStatistic();
    }

    public static void pvpClear(boolean isP1) throws IOException {
        pause = true;
        UI.initPVPWin(isP1);
        root.getChildren().add(UI.pvpWin);
        Sound.gameClear.start();
        Sound.gameClear.restart();
        updateStatistic();
    }

    public static void startGame() throws FileNotFoundException {
        init();
        root.getChildren().add(UI.main);
        Sound.soundTheme.start();
        Sound.soundTheme.loop();
    }
    public static void continueGame() throws IOException {
        pause = false;
        if (currentLevel < 3) {
            currentLevel++;
        } else {
            currentLevel = 1;
        }
        restart();
    }
    public static void restart() throws IOException {
        pause = false;
        timer.stop();
        entities.clear();
        characters.clear();
        bombs.clear();
        Player.setCount((short) 0);
        int id1 = players.get(0).getNumBomberman();
        int heart1 = players.get(0).getDefaultHeart();
        int bomb1 = players.get(0).getDefaultBomb();
        int id2 = 0;
        int heart2 = 0;
        int bomb2 = 0;
        if (isPVP) {
            id2 = players.get(1).getNumBomberman();
            heart2 = players.get(1).getDefaultHeart();
            bomb2 = players.get(1).getDefaultBomb();
        }
        players.clear();
        root.getChildren().remove(UI.gameOver);
        root.getChildren().remove(UI.pause);
        root.getChildren().remove(UI.gameClear);
        root.getChildren().remove(UI.pvpWin);
        if (isPVP) {
            players.add(new Player((short) id1, heart1, bomb1));
            startPVP(id2, heart2, bomb2);
        } else {
            startPVE(id1, heart1, bomb1);
        }
    }

    public static void backToMenu() throws IOException {
        root.getChildren().remove(UI.gameOver);
        root.getChildren().remove(UI.pause);
        root.getChildren().remove(UI.gameClear);
        root.getChildren().remove(UI.pvpWin);
        pause = false;
        ingame = false;
        isPVP = false;
        timer.stop();
        entities.clear();
        characters.clear();
        bombs.clear();
        players.clear();
        Player.setCount((short) 0);
        root.getChildren().removeAll(p1Avatar, p1Life, p1numLife, p1Bomb, p1numBomb, p1score, p2Avatar, p2Life, p2numLife, p2Bomb, p2numBomb, p2score);
        root.getChildren().add(UI.main);
        Sound.menuTheme.close();
        Sound.soundTheme.start();
        Sound.soundTheme.loop();
    }

    public static void menu() {
        root.getChildren().remove(UI.control);
        root.getChildren().remove(UI.statistic);
        root.getChildren().add(UI.main);
    }

    public static void control() {
        root.getChildren().remove(UI.main);
        root.getChildren().add(UI.control);
    }
    public static void statistic() throws FileNotFoundException {
        root.getChildren().remove(UI.main);
        UI.initSatistic();
        root.getChildren().add(UI.statistic);
    }
    public static void chooseCharacter() {
        root.getChildren().remove(UI.main);
        root.getChildren().add(UI.characters);
    }

    public static void player1chooseCharacter() {
        root.getChildren().remove(UI.main);
        root.getChildren().add(UI.player1Character);
    }

    public static void player2chooseCharacter(int id, int heart, int bomb) {
        root.getChildren().remove(UI.player1Character);
        players.add(new Player((short) id, heart, bomb));
        root.getChildren().add(UI.player2Character);
    }

    public static void startPVP(int id, int heart, int bomb) throws FileNotFoundException {
        setNumOfPlayer(2);
        ingame = true;
        isPVP = true;
        createMap();
        players.get(0).setMap(map);
        players.add(new Player(map, (short) id, heart, bomb));
        Sound.soundTheme.close();
        Sound.menuTheme.start();
        Sound.menuTheme.loop();
        createPlayerHud(0, 0, HEIGHT * Sprite.SCALED_SIZE);
        createPlayerHud(1, 1440, HEIGHT * Sprite.SCALED_SIZE);
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Create game hud
        Rectangle hud = new Rectangle();
        hud.setY(Sprite.SCALED_SIZE * HEIGHT);
        hud.setWidth(Sprite.SCALED_SIZE * WIDTH);
        hud.setHeight(Sprite.SCALED_SIZE);
        var stops = new Stop[] {new Stop(0, Color.web("#81c483")), new Stop(1, Color.web("#fcc200"))};
        hud.setFill(new LinearGradient(0, Sprite.SCALED_SIZE * HEIGHT, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE, false, CycleMethod.NO_CYCLE, stops));

        root.getChildren().remove(UI.player2Character);
        root.getChildren().addAll(canvas, hud, p1Avatar, p1Life, p1Bomb, p1numLife, p1numBomb, p1score, p2Avatar, p2Life, p2Bomb, p2numLife, p2numBomb, p2score);
        scene.setRoot(root);
        mainStage.setScene(scene);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!pause && ingame) {
                    update();
                    render();
                    if (players.get(0).checkVictory() || players.get(1).checkLose()) {
                        try {
                            pvpClear(true);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (players.get(0).checkLose() || players.get(1).checkVictory()) {
                        try {
                            pvpClear(false);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        };
        timer.start();
        handleEvent(scene);
    }

    public static void startPVE(int id, int heart, int bomb) throws IOException {
        setNumOfPlayer(1);
        ingame = true;
        isPVP = false;
        createMap();
        File out = new File("resources/levels/currentLevel.txt");
        Writer o = new FileWriter(out, false);
        o.write(Integer.toString(currentLevel));
        o.close();

        players.add(new Player(map, (short) id, heart, bomb));
        Sound.soundTheme.close();
        Sound.menuTheme.start();
        Sound.menuTheme.loop();
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
        root.getChildren().addAll(canvas, hud, p1Avatar, p1Life, p1Bomb, p1numLife, p1numBomb, p1score);
        scene.setRoot(root);
        mainStage.setScene(scene);

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (!pause && ingame) {
                    update();
                    render();
                    if (players.get(0).checkVictory()) {
                        try {
                            gameClear();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (players.get(0).checkLose()) {
                        gameOver();
                    }
                }
            }
        };
        timer.start();
        handleEvent(scene);
    }

    static void handleEvent(Scene scene) {
        Set<KeyCode> codes = new LinkedHashSet<>();
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                codes.add(keyEvent.getCode());
                if (codes.contains(KeyCode.ESCAPE)) {
                    if (!pause && ingame) {
                        Management.pauseGame();
                    } else if (pause && ingame) {
                        Management.resumeGame();
                    }
                }
                if (!pause && ingame) {
                    for (Player player : players) {
                        player.play(codes);
                    }
                }
            }
        };

        EventHandler<KeyEvent> releaseHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                System.out.println("code : " + keyEvent.getCode());
                codes.remove(keyEvent.getCode());
            }
        };

        scene.setOnKeyPressed(eventHandler);
        scene.setOnKeyReleased(releaseHandler);
    }

    public static void createMap() {

        File file = null;
        int lvl;
        if (!isPVP) {
            lvl = currentLevel;
        } else {
            Random generator = new Random();
            lvl = generator.nextInt(3) + 1;
        }
        switch (lvl) {
            case 1 -> {
                file = new File("resources/levels/Level1.txt");
            }
            case 2 -> {
                file = new File("resources/levels/Level2.txt");
            }
            case 3 -> {
                file = new File("resources/levels/Level3.txt");
            }
        }
        Scanner scn = null;
        try {
            scn = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        lvl = scn.nextInt();
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
                        obj = new Spartan(j, i, Sprite.spartan_right1, new AIMedium(map));
                        characters.add(obj);
                    }
                    case '2' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                        obj = new Mushroom(j, i, Sprite.mushroom_left1, new AILow(map));
                        characters.add(obj);
                    }
                    case '3' -> {
                        entities.add(new Grass(j, i, Sprite.grass));
                        obj = new Ufo(j, i, Sprite.ufo_right1, new AIFly(map));
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
                    case 'h' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new HeartItem(j, i, Sprite.powerup_life));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'B' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
                        ((LayeredEntity)obj).addEntity(new BombItem(j, i, Sprite.powerup_bombs));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'F' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
                        ((LayeredEntity)obj).addEntity(new FlameItem(j, i, Sprite.powerup_flames));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'S' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
                        ((LayeredEntity)obj).addEntity(new SpeedItem(j, i, Sprite.powerup_speed));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'H' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
                        ((LayeredEntity)obj).addEntity(new HeartItem(j, i, Sprite.powerup_life));
                        ((LayeredEntity)obj).addEntity(new Grass(j, i, Sprite.grass));
                        entities.add(obj);
                    }
                    case 'x' -> {
                        obj = new LayeredEntity(j, i);
                        ((LayeredEntity)obj).addEntity(new Brick(j, i, Sprite.brick));
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
        for (Player player : players) {
            player.update();
        }
        hudUpdate();
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

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void updateStatistic() throws IOException {
        File out = new File("resources/statistic/statistic.txt");
        PrintWriter o = new PrintWriter(new FileWriter("resources/statistic/statistic.txt"));
        o.println(lv1HighScore);
        o.println(lv2HighScore);
        o.println(lv3HighScore);
        o.println(pvpHighScore);
        o.println(numberBomberMan);
        o.println(numberBomberTheKid);
        o.close();
    }
}
