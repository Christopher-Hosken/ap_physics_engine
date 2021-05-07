import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Gui extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets\\atom.png"));

        StackPane root = new StackPane();

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

        
        stage.setScene(new Scene(root, 1728, 972));
        stage.show();
    }

}
