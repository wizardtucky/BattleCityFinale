import Game.Game;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.stage.Stage;

public class Main extends Application {
    public static final double W = 800; // canvas dimensions.
    public static final double H = 800;

    final Canvas canvas = new Canvas(W, H);

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(canvas));
        stage.setScene(scene);
        stage.show();
        Game game = new Game(canvas.getGraphicsContext2D(), W, H);
        scene.setOnKeyPressed(e->{
            game.keyboardInput(e.getCode());
        });
        game.startGame();
    }

    public static void main(String[] args) { launch(args); }
}
