package uet.oop.bomberman.GUI;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.AnimatedEntities;
import uet.oop.bomberman.graphics.Sprite;

public class Game {
    public static Stage mainStage = BombermanGame.mainStage;
    public static Scene sc;
    public static Group root;
    public static Canvas canvas;
    public static GraphicsContext gc;
    public static int WIDTH = 31 * Sprite.SCALED_SIZE;
    public static int HEIGHT = 14 * Sprite.SCALED_SIZE;
    public static final Image menu = new Image("./ui/menu.jpg");
    public static Scene getSc() {
        return sc;
    }
    public static void init() {
        root = new Group();
        sc = new Scene(root, WIDTH, HEIGHT);
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.drawImage(menu, (WIDTH - menu.getWidth())/2, (HEIGHT - menu.getHeight())/2);
        sc.getStylesheets().add("./format/format.css");
        Button PVE = new Button("PVE");
        PVE.setLayoutX((WIDTH - PVE.getWidth())/2);
        PVE.setLayoutY(300);
        Button PVP = new Button("PVP");
        PVP.setLayoutX((WIDTH - PVP.getWidth())/2);
        PVP.setLayoutY(400);
        Button Control = new Button("Control");
        Control.setLayoutX((WIDTH - Control.getWidth())/2);
        Control.setLayoutY(500);
        Button Leadboard = new Button("Leaderboard");
        Leadboard.setLayoutX((WIDTH - Leadboard.getWidth())/2);
        Leadboard.setLayoutY(600);
        root.getChildren().addAll(canvas, PVE, PVP, Control, Leadboard);
        mainStage.setScene(sc);
    }
}
