package uet.oop.bomberman.GUI;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class UI {

    public static Pane main;
    public static Pane pause;
    public static Pane gameOver;
    public static Pane gameClear;
    public static Pane characters;
    private static Font buttonFont;
    private static Font menuFont;

    public static void init() throws FileNotFoundException, URISyntaxException {
        InputStream stream = new FileInputStream("resources/font/Minecraft.ttf");
        buttonFont = Font.loadFont(stream, 18);
        menuFont = Font.loadFont(new FileInputStream("resources/font/Minecraft.ttf"), 30);
        initPauseMenu();
        initMainMenu();
        initChooseCharacter();
    }

    public static void initPauseMenu() throws URISyntaxException, FileNotFoundException {
        pause = new Pane();
        Canvas cv = new Canvas(BombermanGame.WIDTH  * Sprite.SCALED_SIZE, BombermanGame.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();

        InputStream stream = new FileInputStream("resources/ui/optionPanel.png");
        Image pauseMenu = new Image(stream);

        gc.drawImage(pauseMenu, (BombermanGame.WIDTH * Sprite.SCALED_SIZE - pauseMenu.getWidth())/2, (BombermanGame.HEIGHT * Sprite.SCALED_SIZE - pauseMenu.getHeight())/2);

        Button resume = new Button("Resume");
        resume.setFont(buttonFont);
        resume.setPrefSize(100, 30);
        resume.setLayoutX((BombermanGame.WIDTH * Sprite.SCALED_SIZE - pauseMenu.getWidth())/2 + (pauseMenu.getWidth() - 100)/2);
        resume.setLayoutY((BombermanGame.HEIGHT * Sprite.SCALED_SIZE - pauseMenu.getHeight())/2 + 100);
        resume.setOnAction(actionEvent -> Management.resumeGame());

        Button restart = new Button("Restart");
        restart.setFont(buttonFont);
        restart.setPrefSize(100, 30);
        restart.setLayoutX(resume.getLayoutX());
        restart.setLayoutY(resume.getLayoutY() + 50);

        Button menu = new Button("Menu");
        menu.setFont(buttonFont);
        menu.setPrefSize(100, 30);
        menu.setLayoutX(resume.getLayoutX());
        menu.setLayoutY(restart.getLayoutY() + 50);

        pause.getChildren().addAll(cv, resume, restart, menu);
    }

    public static void initMainMenu() throws FileNotFoundException {
        main = new Pane();
        Canvas cv = new Canvas(BombermanGame.WIDTH * Sprite.SCALED_SIZE, BombermanGame.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, cv.getWidth(), cv.getHeight());

        InputStream stream = new FileInputStream("resources/ui/mainMenu.jpg");
        Image mainMenu = new Image(stream);
        gc.drawImage(mainMenu, (cv.getWidth() - mainMenu.getWidth())/2, 0);

        Button PVE = new Button("PVE");
        PVE.setFont(menuFont);
        PVE.setPrefSize(150, 50);
        PVE.setLayoutX((cv.getWidth() - 150)/2);
        PVE.setLayoutY(mainMenu.getHeight() - 30);
        PVE.setOnAction(actionEvent -> Management.chooseCharacter());

        Button PVP = new Button("PVP");
        PVP.setFont(menuFont);
        PVP.setPrefSize(150, 50);
        PVP.setLayoutX(PVE.getLayoutX());
        PVP.setLayoutY(PVE.getLayoutY() + 70);

        Button Control = new Button("Control");
        Control.setFont(menuFont);
        Control.setPrefSize(150, 50);
        Control.setLayoutX(PVE.getLayoutX());
        Control.setLayoutY(PVP.getLayoutY() + 70);

        Button History = new Button("History");
        History.setFont(menuFont);
        History.setPrefSize(150, 50);
        History.setLayoutX(PVE.getLayoutX());
        History.setLayoutY(Control.getLayoutY() + 70);

        main.getChildren().addAll(cv, PVE, PVP, Control, History);
    }

    public static void initChooseCharacter() throws FileNotFoundException {
        characters = new Pane();
        Canvas cv = new Canvas(BombermanGame.WIDTH * Sprite.SCALED_SIZE, BombermanGame.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();
        var stops = new Stop[] {new Stop(0, Color.web("#81c483")), new Stop(1, Color.web("#fcc200"))};
        gc.setFill(new LinearGradient(0, 0, cv.getWidth(), cv.getHeight(), false, CycleMethod.NO_CYCLE, stops));
        gc.fillRect(0, 0, cv.getWidth(), cv.getHeight());

        Button Bomberman = new Button();
        Bomberman.setPrefSize(400, 400);
        Bomberman.setLayoutX(294);
        Bomberman.setLayoutY(100);
        Bomberman.setBackground(null);
        Bomberman.setGraphic(new ImageView(new Image(new FileInputStream("resources/textures/Bomber.png"))));

        Button BomberTheKid = new Button();
        BomberTheKid.setPrefSize(400, 400);
        BomberTheKid.setLayoutX(794);
        BomberTheKid.setLayoutY(100);
        BomberTheKid.setBackground(null);
        BomberTheKid.setGraphic(new ImageView(new Image(new FileInputStream("resources/textures/BomberTheKid.png"))));

        characters.getChildren().addAll(cv, Bomberman, BomberTheKid);
    }
}
