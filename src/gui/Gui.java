package gui;
import javax.swing.SwingUtilities;

import com.jogamp.newt.event.MouseListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.FPSAnimator;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TabPane.TabDragPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import engine.EmptyObj;
import engine.SceneCanvas;

public class Gui extends Application {
    Color lineColor = new Color(0.4, 0.4, 0.4, 1);
    private double xOffset, yOffset;
    private boolean _darkTheme = true;
    private EmptyObj selectedObj = null;

    public TabPane settings; 
    public Tab objTab;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets/images/icons/atom.png"));

        StackPane root = new StackPane();
        root.setId("ROOTNODE");
        Rectangle rect = new Rectangle(1728, 972);
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        //#region opengl
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        caps.setDoubleBuffered(true);
        caps.setHardwareAccelerated(true);
        
        GLJPanel canvas = new GLJPanel(caps);
        SceneCanvas sc = new SceneCanvas();
        canvas.addGLEventListener(sc);
        canvas.addMouseMotionListener(sc);
        canvas.addMouseWheelListener(sc);
        canvas.addMouseListener(sc);
        canvas.addKeyListener(sc);
        Animator animator = new Animator(canvas);
        animator.start();

        SwingNode swingNode = new SwingNode();

        root.getChildren().add(swingNode);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swingNode.setContent(canvas);
            }
        });

        //#endregion


        //#region header
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

        Button logo = new Button();
        logo.getStyleClass().add("icon-button");
        logo.setId("LOGO");
        logo.getStyleClass().add("icon-button");
        logo.setMaxHeight(70);
        logo.setMaxWidth(70);
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

        ToggleButton toggleMenu = new ToggleButton();
        toggleMenu.getStyleClass().add("icon-button");
        toggleMenu.setId("TOGMENU");
        toggleMenu.setMaxHeight(70);
        toggleMenu.setMaxWidth(70);
        toggleMenu.setTranslateX(-290);
        header.getChildren().add(toggleMenu);

        Line vLine2 = new Line();
        vLine2.setStartY(0);
        vLine2.setEndY(100);
        vLine2.setTranslateX(-230);
        vLine2.setStroke(lineColor);
        header.getChildren().add(vLine2);

        Button addSphere = new Button();
        addSphere.getStyleClass().add("icon-button");
        addSphere.setId("ADDSPH");
        addSphere.setMaxHeight(70);
        addSphere.setMaxWidth(70);
        addSphere.setTranslateX(-170);
        header.getChildren().add(addSphere);

        Button addCube = new Button();
        addCube.getStyleClass().add("icon-button");
        addCube.setId("ADDCUBE");
        addCube.setMaxHeight(70);
        addCube.setMaxWidth(70);
        addCube.setTranslateX(-40);
        header.getChildren().add(addCube);

        Button importModel = new Button();
        importModel.getStyleClass().add("icon-button");
        importModel.setId("IMPORTMOD");
        importModel.setMaxHeight(70);
        importModel.setMaxWidth(70);
        importModel.setTranslateX(90);
        header.getChildren().add(importModel);

        Button addLight = new Button();
        addLight.getStyleClass().add("icon-button");
        addLight.setId("ADDLIGHT");
        addLight.setMaxHeight(70);
        addLight.setMaxWidth(70);
        addLight.setTranslateX(210);
        header.getChildren().add(addLight);

        Line vLine3 = new Line();
        vLine3.setStartY(0);
        vLine3.setEndY(100);
        vLine3.setTranslateX(270);
        vLine3.setStroke(lineColor);
        header.getChildren().add(vLine3);

        ToggleButton toggleWire = new ToggleButton();
        toggleWire.getStyleClass().add("icon-button");
        toggleWire.setId("TOGWIRE");
        toggleWire.setMaxHeight(70);
        toggleWire.setMaxWidth(70);
        toggleWire.setTranslateX(330);
        header.getChildren().add(toggleWire);

        ToggleButton toggleVisibility = new ToggleButton();
        toggleVisibility.getStyleClass().add("icon-button");
        toggleVisibility.setId("TOGVIS");
        toggleVisibility.setMaxHeight(70);
        toggleVisibility.setMaxWidth(70);
        toggleVisibility.setTranslateX(440);
        header.getChildren().add(toggleVisibility);

        Line vLine4 = new Line();
        vLine4.setStartY(0);
        vLine4.setEndY(100);
        vLine4.setTranslateX(500);
        vLine4.setStroke(lineColor);
        header.getChildren().add(vLine4);

        ToggleButton toggleTheme = new ToggleButton();
        toggleTheme.getStyleClass().add("icon-button");
        toggleTheme.setId("TOGTHEME");
        toggleTheme.setMaxHeight(70);
        toggleTheme.setMaxWidth(70);
        toggleTheme.setTranslateX(560);
        header.getChildren().add(toggleTheme);

        Button info = new Button();
        info.getStyleClass().add("icon-button");
        info.setId("INFO");
        info.setMaxHeight(70);
        info.setMaxWidth(70);
        info.setTranslateX(645);
        header.getChildren().add(info);

        Button bug = new Button();
        bug.getStyleClass().add("icon-button");
        bug.setId("BUG");
        bug.setMaxHeight(70);
        bug.setMaxWidth(70);
        bug.setTranslateX(725);
        header.getChildren().add(bug);

        Button close = new Button();
        close.setPickOnBounds(true);
        Region closeIcon = new Region();
        closeIcon.setId("CLOSEICON");
        close.getStyleClass().add("icon-button");
        close.setId("CLOSE");
        close.setMaxHeight(70);
        close.setMaxWidth(70);
        close.setTranslateX(810);
        close.setGraphic(closeIcon);
        header.getChildren().add(close);
        
        //#endregion

        //#region menu
        StackPane menu = new StackPane();
        menu.setId("MENU");
        menu.setMaxSize(365, 872);
        menu.setTranslateX(682);
        menu.setTranslateY(51);

        settings = new TabPane();
        settings.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        settings.setTabDragPolicy(TabDragPolicy.FIXED);
        settings.setSide(Side.LEFT);
        Tab camTab = new Tab("Camera");
        Tab worldTab = new Tab("World");
        settings.getTabs().add(worldTab);
        settings.getTabs().add(camTab);
        objTab = new Tab("Object");

        menu.getChildren().add(settings);

        //#endregion

        //#region buttonActions

        root.addEventFilter(MouseEvent.MOUSE_CLICKED,  new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selectedObj = sc.getSelection();

                if (selectedObj == null) {
                    settings.getTabs().remove(objTab);
                }

                else {
                    settings.getTabs().add(objTab);
                }
            }
        });
        
        header.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }});

        header.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().ordinal() == 1) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            }
        });

        logo.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sc.wipe();
            }
        });

        toggleMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (root.getChildren().contains(menu)) {
                    root.getChildren().remove(menu);
                }

                else {
                    root.getChildren().add(menu);
                }
            }
        });

        toggleWire.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (sc.isWire) {
                    sc.isWire = false;
                }

                else {
                    sc.isWire = true;
                }
            }
        });

        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/ap_physics_engine/wiki"));
                } catch (IOException | URISyntaxException e2) {
                    e2.printStackTrace();
                }
            }
        });

        bug.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(new URI("https://github.com/Christopher-Hosken/ap_physics_engine/issues"));
                } catch (IOException | URISyntaxException e2) {
                    e2.printStackTrace();
                }
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                animator.stop();
                stage.close();
            }
        });

        addCube.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sc.addCube();
            }
        });

        addSphere.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                sc.addIcoSphere();
            }
        });

        addLight.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("LIGHT ADDED");
            }
        });

        importModel.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("MODEL IMPORTED");
            }
        });

        //#endregion
        Scene scene = new Scene(root, 1728, 972);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);


        toggleTheme.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if (_darkTheme) {
                    stage.getScene().getStylesheets().setAll("assets/light.css");
                }

                else {
                    stage.getScene().getStylesheets().setAll("assets/dark.css");
                }

                _darkTheme = !_darkTheme;
            }
        });

        stage.getScene().getStylesheets().setAll("assets/dark.css");
        stage.show();
    }
}