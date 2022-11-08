package uet.oop.bomberman.GUI;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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
    public static Pane control;
    public static Pane characters;
    private static Font buttonFont;
    private static Font menuFont;

    private static Font hudFont;

    public static void init() throws FileNotFoundException, URISyntaxException {
        InputStream stream = new FileInputStream("resources/font/Minecraft.ttf");
        buttonFont = Font.loadFont(stream, 18);
        menuFont = Font.loadFont(new FileInputStream("resources/font/Minecraft.ttf"), 30);
        hudFont = Font.loadFont(new FileInputStream("resources/font/Minecraft.ttf"), 50);
        initPauseMenu();
        initMainMenu();
        initChooseCharacter();
        initControl();
    }

    public static Font getHudFont() {
        return hudFont;
    }

    public static void initPauseMenu() throws URISyntaxException, FileNotFoundException {
        pause = new Pane();
        Canvas cv = new Canvas(Management.WIDTH  * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();

        InputStream stream = new FileInputStream("resources/ui/optionPanel.png");
        Image pauseMenu = new Image(stream);

        gc.drawImage(pauseMenu, (cv.getWidth() - pauseMenu.getWidth())/2, (cv.getHeight() - pauseMenu.getHeight())/2);

        Button resume = new Button("Resume");
        resume.setFont(buttonFont);
        resume.setPrefSize(100, 30);
        resume.setLayoutX((cv.getWidth() - pauseMenu.getWidth())/2 + (pauseMenu.getWidth() - 100)/2);
        resume.setLayoutY((cv.getHeight() - pauseMenu.getHeight())/2 + 100);
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
        menu.setOnAction(actionEvent -> Management.backToMenu());

        pause.getChildren().addAll(cv, resume, restart, menu);
    }

    public static void initMainMenu() throws FileNotFoundException {
        main = new Pane();
        Canvas cv = new Canvas(Management.WIDTH * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
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
        Control.setOnAction(actionEvent -> Management.control());

        Button History = new Button("History");
        History.setFont(menuFont);
        History.setPrefSize(150, 50);
        History.setLayoutX(PVE.getLayoutX());
        History.setLayoutY(Control.getLayoutY() + 70);

        main.getChildren().addAll(cv, PVE, PVP, Control, History);
    }

    public static void initChooseCharacter() throws FileNotFoundException {
        characters = new Pane();
        Canvas cv = new Canvas(Management.WIDTH * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
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
        Bomberman.setOnAction(actionEvent -> {
            try {
                Management.startPVE(0, 5, 10);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button BomberTheKid = new Button();
        BomberTheKid.setPrefSize(400, 400);
        BomberTheKid.setLayoutX(794);
        BomberTheKid.setLayoutY(100);
        BomberTheKid.setBackground(null);
        BomberTheKid.setGraphic(new ImageView(new Image(new FileInputStream("resources/textures/BomberTheKid.png"))));
        BomberTheKid.setOnAction(actionEvent -> {
            try {
                Management.startPVE(1, 3, 15);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        characters.getChildren().addAll(cv, Bomberman, BomberTheKid);
    }

    public static void initControl() throws FileNotFoundException {
        control = new Pane();
        Canvas cv = new Canvas(Management.WIDTH * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();
        var stops = new Stop[] {new Stop(0, Color.web("#81c483")), new Stop(1, Color.web("#fcc200"))};
        gc.setFill(new LinearGradient(0, 0, cv.getWidth(), cv.getHeight(), false, CycleMethod.NO_CYCLE, stops));
        gc.fillRect(0, 0, cv.getWidth(), cv.getHeight());

        InputStream stream = new FileInputStream("resources/ui/control.png");
        Image controlInstruc = new Image(stream);
        gc.drawImage(controlInstruc, (cv.getWidth() - controlInstruc.getWidth())/2, 0);

        Button back = new Button("Back");
        back.setFont(menuFont);
        back.setPrefSize(150, 50);
        back.setLayoutX((cv.getWidth() - 150)/2);
        back.setLayoutY(500);
        back.setOnAction(actionEvent -> Management.menu());

        control.getChildren().addAll(cv, back);
    }
}
