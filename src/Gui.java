import javax.swing.SwingUtilities;

import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Dimension2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Gui extends Application {
    Color lineColor = new Color(0.4, 0.4, 0.4, 1);

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
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        StackPane header = new StackPane();
        header.setTranslateY(-436);
        header.setMaxHeight(100);
        header.setId("HEADER");
        root.getChildren().add(header);

        Line hLine = new Line();
        hLine.setStartX(-900);
        hLine.setEndX(900);
        hLine.setTranslateY(50);
        hLine.setStroke(lineColor);
        header.getChildren().add(hLine);

        ImageView logo = new ImageView(new Image("assets/images/atom.png"));
        logo.setFitWidth(69);
        logo.setFitHeight(69);
        logo.setTranslateX(-800);
        header.getChildren().add(logo);

        Label title = new Label("AP Physics Engine");
        title.setTextFill(Color.WHITE);
        title.setTranslateX(-590);
        header.getChildren().add(title);

        Line vLine1 = new Line();
        vLine1.setStartY(0);
        vLine1.setEndY(100);
        vLine1.setTranslateX(-350);
        vLine1.setStroke(lineColor);
        header.getChildren().add(vLine1);

        Line vLine2 = new Line();
        vLine2.setStartY(0);
        vLine2.setEndY(100);
        vLine2.setTranslateX(-230);
        vLine2.setStroke(lineColor);
        header.getChildren().add(vLine2);

        Line vLine3 = new Line();
        vLine3.setStartY(0);
        vLine3.setEndY(100);
        vLine3.setTranslateX(270);
        vLine3.setStroke(lineColor);
        header.getChildren().add(vLine3);

        Line vLine4 = new Line();
        vLine4.setStartY(0);
        vLine4.setEndY(100);
        vLine4.setTranslateX(500);
        vLine4.setStroke(lineColor);
        header.getChildren().add(vLine4);


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