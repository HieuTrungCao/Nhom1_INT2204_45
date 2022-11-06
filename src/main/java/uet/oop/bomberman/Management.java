package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.GUI.UI;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.animatedEntities.character.Bomber;
import uet.oop.bomberman.entities.destroyable.bomb.Bomb;
import uet.oop.bomberman.entities.destroyable.bomb.BombExplosion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Management {
    public static void pauseGame() {
        BombermanGame.pause = true;
        BombermanGame.root.getChildren().add(UI.pause);
    }

    public static void resumeGame() {
        BombermanGame.root.getChildren().remove(UI.pause);
        BombermanGame.pause = false;
    }

    public static void startGame() {
        BombermanGame.pause = false;
        BombermanGame.timer.stop();
        BombermanGame.entities.clear();
        BombermanGame.characters.clear();
        BombermanGame.bombs.clear();
        BombermanGame.players.clear();
        BombermanGame.root.getChildren().add(UI.main);
    }

    public static void restart() {

    }

    public static void chooseCharacter() {
        BombermanGame.root.getChildren().remove(UI.main);
        BombermanGame.root.getChildren().add(UI.characters);
    }
}
