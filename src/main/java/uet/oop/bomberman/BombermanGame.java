package uet.oop.bomberman;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import uet.oop.bomberman.GUI.UI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class BombermanGame extends Application {
    public static Stage mainStage;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException, URISyntaxException {
        mainStage = stage;
        UI.init();
        Management.startGame();
        // Them scene vao stage
        mainStage.setTitle("Bomberman");
        mainStage.setResizable(false);
        mainStage.getIcons().add(new Image(new FileInputStream("resources/textures/GameIcon.png")));
        mainStage.setScene(Management.getScene());
        mainStage.show();
    }
}
