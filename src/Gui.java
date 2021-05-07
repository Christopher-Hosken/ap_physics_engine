import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gui extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets\\images\\atom.png"));

        StackPane root = new StackPane();
        root.setId("ROOTNODE");
        Rectangle rect = new Rectangle(1728, 972);
        rect.setArcHeight(35);
        rect.setArcWidth(35);
        root.setClip(rect);

        StackPane header = new StackPane();
        header.setTranslateY(-426);
        header.setMaxHeight(120);
        header.setId("HEADER");
        root.getChildren().add(header);

        ImageView icon = new ImageView(new Image("assets/images/atom.png"));
        icon.setFitHeight(90);
        icon.setFitWidth(90);
        icon.setTranslateX(-780);
        header.getChildren().add(icon);

        Button min = new Button();
        min.setMaxSize(60, 60);
        min.setTranslateX(767);
        min.setTranslateY(-30);
        min.setGraphic(new ImageView(new Image("assets/images/min.png")));
        header.getChildren().add(min);

        Button close = new Button();
        close.setMaxSize(60, 60);
        close.setTranslateX(832);
        close.setTranslateY(-30);
        close.setGraphic(new ImageView(new Image("assets/images/close.png")));
        close.setId("CLOSE");
        header.getChildren().add(close);

        Rectangle hLine = new Rectangle(0, 0, 168, 2);
        hLine.setTranslateX(780);
        hLine.setTranslateY(2);
        hLine.setFill(Color.WHITE);
        header.getChildren().add(hLine);

        /*
        final GLProfile profile = GLProfile.getDefault();
        final GLCapabilities capabilities = new GLCapabilities(profile);
        
        GLJPanel canvas = new GLJPanel(capabilities);

        SwingNode swingNode = new SwingNode();

        root.getChildren().add(swingNode);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingNode.setContent(canvas);
            }
        });
        */

        Scene scene = new Scene(root, 1728, 972);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.getScene().getStylesheets().setAll("main.css");
        stage.show();
    }
}