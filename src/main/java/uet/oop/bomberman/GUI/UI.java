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
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Management;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.graphics.Sprite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class UI {

    public static Pane main;
    public static Pane pause;
    public static Pane gameOver;
    public static Pane gameClear;
    public static Pane control;
    public static Pane characters;
    public static Pane player1Character;
    public static Pane player2Character;
    public static Pane pvpWin;
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
        initGameOver();
        initPlayer1Character();
        initPlayer2Character();
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
        restart.setOnAction(actionEvent -> {
            try {
                Management.restart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

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
        PVP.setOnAction(actionEvent -> Management.player1chooseCharacter());

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
                Management.startPVE(0, 5, 20);
            } catch (IOException e) {
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
                Management.startPVE(1, 3, 25);
            } catch (IOException e) {
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

    public static void initGameOver() throws FileNotFoundException {
        gameOver = new Pane();
        Canvas cv = new Canvas(Management.WIDTH  * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();

        InputStream stream = new FileInputStream("resources/ui/GameOver.png");
        Image GameOver = new Image(stream);

        gc.drawImage(GameOver, (cv.getWidth() - GameOver.getWidth())/2, (cv.getHeight() - GameOver.getHeight())/2);

        Button restart = new Button("Restart");
        restart.setFont(buttonFont);
        restart.setPrefSize(100, 30);
        restart.setLayoutX((cv.getWidth() - GameOver.getWidth())/2 + (GameOver.getWidth() - 100)/2);
        restart.setLayoutY((cv.getHeight() - GameOver.getHeight())/2 + 100);
        restart.setOnAction(actionEvent -> {
            try {
                Management.restart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button menu = new Button("Menu");
        menu.setFont(buttonFont);
        menu.setPrefSize(100, 30);
        menu.setLayoutX(restart.getLayoutX());
        menu.setLayoutY(restart.getLayoutY() + 50);
        menu.setOnAction(actionEvent -> Management.backToMenu());

        gameOver.getChildren().addAll(cv, restart, menu);
    }

    public static void initGameClear() throws FileNotFoundException {
        gameClear = new Pane();
        Canvas cv = new Canvas(Management.WIDTH * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();

        InputStream stream = new FileInputStream("resources/ui/GameClear.png");
        Image GameClear = new Image(stream);

        gc.drawImage(GameClear, (cv.getWidth() - GameClear.getWidth())/2, (cv.getHeight() - GameClear.getHeight())/2);

        Text baseScore = new Text("Base score: " + Integer.toString(Management.players.get(0).getMark()));
        baseScore.setFont(buttonFont);
        baseScore.setFill(Color.WHITE);
        baseScore.relocate((cv.getWidth() - GameClear.getWidth())/2 + 20, (cv.getHeight() - GameClear.getHeight())/2 + 70);

        Text bonusScore = new Text("Bonus score: " + Integer.toString(Management.players.get(0).getBonusScore()));
        bonusScore.setFont(buttonFont);
        bonusScore.setFill(Color.WHITE);
        bonusScore.relocate((cv.getWidth() - GameClear.getWidth())/2 + 20, (cv.getHeight() - GameClear.getHeight())/2 + 90);

        Text totalScore = new Text("Total score: " + Integer.toString(Management.players.get(0).getMark() + Management.players.get(0).getBonusScore()));
        totalScore.setFont(buttonFont);
        totalScore.setFill(Color.WHITE);
        totalScore.relocate((cv.getWidth() - GameClear.getWidth())/2 + 20, (cv.getHeight() - GameClear.getHeight())/2 + 110);

        Button Continue = null;
        if (Management.getCurrentLevel() < 3) {
            Continue = new Button("Continue");
        } else {
            Continue = new Button("Reset");
        }
        Continue.setFont(buttonFont);
        Continue.setPrefSize(110, 30);
        Continue.setLayoutX((cv.getWidth() - GameClear.getWidth()) / 2 + (GameClear.getWidth() - 110) / 2);
        Continue.setLayoutY((cv.getHeight() - GameClear.getHeight()) / 2 + 150);
        Continue.setOnAction(actionEvent -> {
            try {
                Management.continueGame();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button restart = new Button("Restart");
        restart.setFont(buttonFont);
        restart.setPrefSize(110, 30);
        restart.setLayoutX((cv.getWidth() - GameClear.getWidth())/2 + (GameClear.getWidth() - 110)/2);
        restart.setLayoutY((cv.getHeight() - GameClear.getHeight())/2 + 200);
        restart.setOnAction(actionEvent -> {
            try {
                Management.restart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button menu = new Button("Menu");
        menu.setFont(buttonFont);
        menu.setPrefSize(110, 30);
        menu.setLayoutX(restart.getLayoutX());
        menu.setLayoutY(restart.getLayoutY() + 50);
        menu.setOnAction(actionEvent -> Management.backToMenu());
        gameClear.getChildren().addAll(cv, baseScore, bonusScore, totalScore, Continue, restart, menu);
    }

    public static void initPlayer1Character() throws FileNotFoundException {
        player1Character = new Pane();
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
            Management.player2chooseCharacter(0, 5, 20);
        });

        Button BomberTheKid = new Button();
        BomberTheKid.setPrefSize(400, 400);
        BomberTheKid.setLayoutX(794);
        BomberTheKid.setLayoutY(100);
        BomberTheKid.setBackground(null);
        BomberTheKid.setGraphic(new ImageView(new Image(new FileInputStream("resources/textures/BomberTheKid.png"))));
        BomberTheKid.setOnAction(actionEvent -> {
            Management.player2chooseCharacter(1, 3, 25);
        });

        Text turn = new Text("P1's turn to choose!");
        turn.setFont(hudFont);
        turn.setFill(Color.BLACK);
        turn.relocate((cv.getWidth() - turn.getBoundsInLocal().getWidth())/2, 50);

        player1Character.getChildren().addAll(cv, turn, Bomberman, BomberTheKid);
    }

    public static void initPlayer2Character() throws FileNotFoundException {
        player2Character = new Pane();
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
                Management.startPVP(0, 5, 20);
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
                Management.startPVP(1, 3, 25);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Text turn = new Text("P2's turn to choose!");
        turn.setFont(hudFont);
        turn.setFill(Color.BLACK);
        turn.relocate((cv.getWidth() - turn.getBoundsInLocal().getWidth())/2, 50);

        player2Character.getChildren().addAll(cv, turn, Bomberman, BomberTheKid);
    }

    public static void initPVPWin(boolean isPlayer1) throws FileNotFoundException {
        pvpWin = new Pane();
        Canvas cv = new Canvas(Management.WIDTH * Sprite.SCALED_SIZE, Management.HEIGHT * Sprite.SCALED_SIZE);
        GraphicsContext gc = cv.getGraphicsContext2D();

        InputStream stream = null;
        if (isPlayer1) {
            stream = new FileInputStream("resources/ui/Player1Win.png");
        } else {
            stream = new FileInputStream("resources/ui/Player2Win.png");
        }
        Image win = new Image(stream);

        gc.drawImage(win, (cv.getWidth() - win.getWidth())/2, (cv.getHeight() - win.getHeight())/2);

        Text baseScore1 = new Text("P1's base score: " + Integer.toString(Management.players.get(0).getMark()));
        baseScore1.setFont(buttonFont);
        baseScore1.setFill(Color.WHITE);
        baseScore1.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 70);

        Text bonusScore1 = new Text("P1's bonus score: " + Integer.toString(Management.players.get(0).getBonusScore()));
        bonusScore1.setFont(buttonFont);
        bonusScore1.setFill(Color.WHITE);
        bonusScore1.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 90);

        Text totalScore1 = new Text("P1's total score: " + Integer.toString(Management.players.get(0).getMark() + Management.players.get(0).getBonusScore()));
        totalScore1.setFont(buttonFont);
        totalScore1.setFill(Color.WHITE);
        totalScore1.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 110);

        Text baseScore2 = new Text("P2's base score: " + Integer.toString(Management.players.get(1).getMark()));
        baseScore2.setFont(buttonFont);
        baseScore2.setFill(Color.WHITE);
        baseScore2.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 130);

        Text bonusScore2 = new Text("P2's bonus score: " + Integer.toString(Management.players.get(1).getBonusScore()));
        bonusScore2.setFont(buttonFont);
        bonusScore2.setFill(Color.WHITE);
        bonusScore2.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 150);

        Text totalScore2 = new Text("P2's total score: " + Integer.toString(Management.players.get(1).getMark() + Management.players.get(1).getBonusScore()));
        totalScore2.setFont(buttonFont);
        totalScore2.setFill(Color.WHITE);
        totalScore2.relocate((cv.getWidth() - win.getWidth())/2 + 20, (cv.getHeight() - win.getHeight())/2 + 170);

        Button restart = new Button("Restart");
        restart.setFont(buttonFont);
        restart.setPrefSize(110, 30);
        restart.setLayoutX((cv.getWidth() - win.getWidth())/2 + (win.getWidth() - 110)/2);
        restart.setLayoutY((cv.getHeight() - win.getHeight())/2 + 200);
        restart.setOnAction(actionEvent -> {
            try {
                Management.restart();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button menu = new Button("Menu");
        menu.setFont(buttonFont);
        menu.setPrefSize(110, 30);
        menu.setLayoutX(restart.getLayoutX());
        menu.setLayoutY(restart.getLayoutY() + 50);
        menu.setOnAction(actionEvent -> Management.backToMenu());

        pvpWin.getChildren().addAll(cv, baseScore1, bonusScore1, totalScore1, baseScore2, bonusScore2, totalScore2, restart, menu);
    }
}
