import javafx.application.Application;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets\\atom.png"));

        StackPane root = new StackPane();
        
        stage.setScene(new Scene(root, 1728, 972));
        stage.show();
    }

}
