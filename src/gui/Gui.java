package gui;

import engine.SceneCanvas;
import engine.EmptyObj;
import engine.vec3;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.*;

import javafx.application.Application;
import javax.swing.*;
import javafx.embed.swing.SwingNode;
import javafx.beans.value.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;

import com.jogamp.newt.event.MouseListener; 
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.*;

public class Gui extends Application {
    Color lineColor = new Color(0.4, 0.4, 0.4, 1);
    private double xOffset, yOffset;
    private boolean _darkTheme = true;
    private EmptyObj selectedObj = null;
    private EmptyObj lastObj = null;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //#region Root
        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets/images/icons/atom.png"));

        StackPane root = new StackPane();
        root.setId("ROOTNODE");
        Rectangle rect = new Rectangle(1728, 972);
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        //#endregion

        // #region Opengl
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

        // #endregion

        // #region Header
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

        // #endregion

        // #region Menu
        StackPane menu = new StackPane();
        menu.setId("MENU");
        menu.setMaxSize(365, 872);
        menu.setTranslateX(682);
        menu.setTranslateY(51);

        TabPane settings = new TabPane();
        settings.setId("SETTINGS");
        settings.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        settings.setTabDragPolicy(TabDragPolicy.FIXED);
        settings.setTabMinWidth(100);

        settings.setTabMinHeight(40);
        settings.setSide(Side.LEFT);

        // #region World Tab

        Tab worldTab = new Tab("World");

        StackPane worldTabRoot = new StackPane();
        worldTab.setContent(worldTabRoot);

        Label worldTitle = new Label();
        worldTitle.setText("World");
        worldTitle.getStyleClass().add("panel-title");
        worldTitle.setText("World");
        worldTitle.setTranslateX(0);
        worldTitle.setTranslateY(-375);
        worldTabRoot.getChildren().add(worldTitle);

        Line vLine5 = new Line();
        vLine5.setStartX(0);
        vLine5.setEndX(285);
        vLine5.setTranslateY(-350);
        vLine5.setStroke(Color.WHITE);
        worldTabRoot.getChildren().add(vLine5);

        //#region Camera Settings
        StackPane cameraPane = new StackPane();
        cameraPane.getStyleClass().add("first-panel");
        cameraPane.setMaxSize(300, 250);
        cameraPane.setTranslateY(-200);
        worldTabRoot.getChildren().add(cameraPane);

        //#endregion World Settings
        StackPane worldPane = new StackPane();
        worldPane.setId("OBJPHYSICS");
        worldPane.getStyleClass().add("second-panel");
        worldPane.setMaxSize(300, 485);
        worldPane.setTranslateY(180);
        worldTabRoot.getChildren().add(worldPane);

        //#region

        //#endregion

        //#endregion

        //#region Object Tab

        StackPane objTabRoot = new StackPane();

        TextField objectName = new TextField();
        objectName.getStyleClass().add("panel-title");
        objectName.setText("Object Name");
        objectName.setTranslateX(0);
        objectName.setTranslateY(-375);
        objTabRoot.getChildren().add(objectName);

        //#region ObjectName Events

        objectName.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(objectName.getText().length() == 0)) {
                    objectName.setText(objectName.getText());
                    selectedObj.setName(objectName.getText());
                }

                objectName.setText(selectedObj.name());
            }
        });

        objectName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (!(objectName.getText().length() == 0)) {
                    objectName.setText(objectName.getText());
                    selectedObj.setName(objectName.getText());
                }

                objectName.setText(selectedObj.name());
            }
        });

        //#endregion

        Line vLine6 = new Line();
        vLine6.setStartX(0);
        vLine6.setEndX(285);
        vLine6.setTranslateY(-350);
        vLine6.setStroke(Color.WHITE);
        objTabRoot.getChildren().add(vLine6);

        // #region Object Panel

        StackPane objTransforms = new StackPane();
        objTransforms.getStyleClass().add("first-panel");
        objTransforms.setMaxSize(300, 250);
        objTransforms.setTranslateY(-200);
        objTabRoot.getChildren().add(objTransforms);

        // #region Locations

        Label locationLabel = new Label();
        locationLabel.getStyleClass().add("small-label");
        locationLabel.setText("Location");
        locationLabel.setTranslateY(-90);
        locationLabel.setTranslateX(-100);
        objTransforms.getChildren().add(locationLabel);

        TextField locationX = new TextField();
        locationX.getStyleClass().add("x-text");
        locationX.setMaxSize(60, 15);
        locationX.setTranslateX(-30);
        locationX.setTranslateY(-90);
        objTransforms.getChildren().add(locationX);

        // #region locationX Events

        locationX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    locationX.setText(oldValue);
                }
            }
        });

        locationX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (locationX.getText().length() == 1 && locationX.getText().contains("-")) {
                    locationX.setText(String.valueOf(-selectedObj.center().x));
                }

                if (!(locationX.getText().length() == 0)) {
                    float x = Float.valueOf(locationX.getText());
                    float y = selectedObj.center().y;
                    float z = selectedObj.center().z;

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationX.setText(String.valueOf(selectedObj.center().x));
            }
        });

        locationX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (locationX.getText().length() == 1 && locationX.getText().contains("-")) {
                    locationX.setText(String.valueOf(-selectedObj.center().x));
                }

                if (!(locationX.getText().length() == 0)) {
                    float x = Float.valueOf(locationX.getText());
                    float y = selectedObj.center().y;
                    float z = selectedObj.center().z;

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationX.setText(String.valueOf(selectedObj.center().x));
            }
        });

        // #endregion

        TextField locationY = new TextField();
        locationY.getStyleClass().add("y-text");
        locationY.setMaxSize(60, 15);
        locationY.setTranslateX(35);
        locationY.setTranslateY(-90);
        objTransforms.getChildren().add(locationY);

        // #region locationY Events

        locationY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    locationY.setText(oldValue);
                }
            }
        });

        locationY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (locationY.getText().length() == 1 && locationY.getText().contains("-")) {
                    locationY.setText(String.valueOf(-selectedObj.center().y));
                }

                if (!(locationY.getText().length() == 0)) {
                    float x = selectedObj.center().x;
                    float y = Float.valueOf(locationY.getText());
                    float z = selectedObj.center().z;

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationY.setText(String.valueOf(selectedObj.center().y));
            }
        });

        locationY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (locationY.getText().length() == 1 && locationY.getText().contains("-")) {
                    locationY.setText(String.valueOf(-selectedObj.center().y));
                }

                if (!(locationY.getText().length() == 0)) {
                    float x = selectedObj.center().x;
                    float y = Float.valueOf(locationY.getText());
                    float z = selectedObj.center().z;

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationY.setText(String.valueOf(selectedObj.center().y));
            }
        });

        // #endregion

        TextField locationZ = new TextField();
        locationZ.getStyleClass().add("z-text");
        locationZ.setMaxSize(60, 15);
        locationZ.setTranslateX(100);
        locationZ.setTranslateY(-90);
        objTransforms.getChildren().add(locationZ);

        // #region locationZ Events

        locationZ.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    locationZ.setText(oldValue);
                }
            }
        });

        locationZ.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (locationZ.getText().length() == 1 && locationZ.getText().contains("-")) {
                    locationZ.setText(String.valueOf(-selectedObj.center().z));
                }

                if (!(locationZ.getText().length() == 0)) {
                    float x = selectedObj.center().x;
                    float y = selectedObj.center().y;
                    float z = Float.valueOf(locationZ.getText());

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationZ.setText(String.valueOf(selectedObj.center().z));
            }
        });

        locationZ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (locationZ.getText().length() == 1 && locationZ.getText().contains("-")) {
                    locationZ.setText(String.valueOf(-selectedObj.center().z));
                }

                if (!(locationZ.getText().length() == 0)) {
                    float x = selectedObj.center().x;
                    float y = selectedObj.center().y;
                    float z = Float.valueOf(locationZ.getText());

                    selectedObj.setLocation(selectedObj.center(), new vec3(x, y, z));
                }

                locationZ.setText(String.valueOf(selectedObj.center().z));
            }
        });

        // #endregion

        // #endregion

        // #region Rotations

        Label rotationLabel = new Label();
        rotationLabel.getStyleClass().add("small-label");
        rotationLabel.setText("Rotation");
        rotationLabel.setTranslateY(-60);
        rotationLabel.setTranslateX(-100);
        objTransforms.getChildren().add(rotationLabel);

        TextField rotationX = new TextField();
        rotationX.getStyleClass().add("x-text");
        rotationX.setMaxSize(60, 15);
        rotationX.setTranslateX(-30);
        rotationX.setTranslateY(-60);
        objTransforms.getChildren().add(rotationX);

        // #region rotationX Events

        rotationX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    rotationX.setText(oldValue);
                }
            }
        });

        rotationX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (rotationX.getText().length() == 1 && rotationX.getText().contains("-")) {
                    rotationX.setText(String.valueOf(-selectedObj.rotation().x));
                }

                if (!(rotationX.getText().length() == 0)) {
                    float x = Float.valueOf(rotationX.getText());
                    float y = selectedObj.rotation().y;
                    float z = selectedObj.rotation().z;

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationX.setText(String.valueOf(selectedObj.rotation().x));
            }
        });

        rotationX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (rotationX.getText().length() == 1 && rotationX.getText().contains("-")) {
                    rotationX.setText(String.valueOf(-selectedObj.rotation().x));
                }

                if (!(rotationX.getText().length() == 0)) {
                    float x = Float.valueOf(rotationX.getText());
                    float y = selectedObj.rotation().y;
                    float z = selectedObj.rotation().z;

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationX.setText(String.valueOf(selectedObj.rotation().x));
            }
        });

        // #endregion

        TextField rotationY = new TextField();
        rotationY.getStyleClass().add("y-text");
        rotationY.setMaxSize(60, 15);
        rotationY.setTranslateX(35);
        rotationY.setTranslateY(-60);
        objTransforms.getChildren().add(rotationY);

        // #region rotationY Events

        rotationY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    rotationY.setText(oldValue);
                }
            }
        });

        rotationY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (rotationY.getText().length() == 1 && rotationY.getText().contains("-")) {
                    rotationY.setText(String.valueOf(-selectedObj.rotation().y));
                }

                if (!(rotationY.getText().length() == 0)) {
                    float x = selectedObj.rotation().x;
                    float y = Float.valueOf(rotationY.getText());
                    float z = selectedObj.rotation().z;

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationY.setText(String.valueOf(selectedObj.rotation().y));
            }
        });

        rotationY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (rotationY.getText().length() == 1 && rotationY.getText().contains("-")) {
                    rotationY.setText(String.valueOf(-selectedObj.rotation().y));
                }

                if (!(rotationY.getText().length() == 0)) {
                    float x = selectedObj.rotation().x;
                    float y = Float.valueOf(rotationY.getText());
                    float z = selectedObj.rotation().z;

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationY.setText(String.valueOf(selectedObj.rotation().y));
            }
        });

        // #endregion

        TextField rotationZ = new TextField();
        rotationZ.getStyleClass().add("z-text");
        rotationZ.setMaxSize(60, 15);
        rotationZ.setTranslateX(100);
        rotationZ.setTranslateY(-60);
        objTransforms.getChildren().add(rotationZ);

        // #region rotationZ Events

        rotationZ.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    rotationZ.setText(oldValue);
                }
            }
        });

        rotationZ.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (rotationZ.getText().length() == 1 && rotationZ.getText().contains("-")) {
                    rotationZ.setText(String.valueOf(-selectedObj.rotation().z));
                }

                if (!(rotationZ.getText().length() == 0)) {
                    float x = selectedObj.rotation().x;
                    float y = selectedObj.rotation().y;
                    float z = Float.valueOf(rotationZ.getText());

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationZ.setText(String.valueOf(selectedObj.rotation().z));
            }
        });

        rotationZ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (rotationZ.getText().length() == 1 && rotationZ.getText().contains("-")) {
                    rotationZ.setText(String.valueOf(-selectedObj.rotation().z));
                }

                if (!(rotationZ.getText().length() == 0)) {
                    float x = selectedObj.rotation().x;
                    float y = selectedObj.rotation().y;
                    float z = Float.valueOf(rotationZ.getText());

                    selectedObj.setRotation(new vec3(x, y, z));
                }

                rotationZ.setText(String.valueOf(selectedObj.rotation().z));
            }
        });

        // #endregion

        // #endregion

        // #region Scales

        Label scaleLabel = new Label();
        scaleLabel.getStyleClass().add("small-label");
        scaleLabel.setText("Scale");
        scaleLabel.setTranslateY(-30);
        scaleLabel.setTranslateX(-100);
        objTransforms.getChildren().add(scaleLabel);

        TextField scaleX = new TextField();
        scaleX.getStyleClass().add("x-text");
        scaleX.setMaxSize(60, 15);
        scaleX.setTranslateX(-30);
        scaleX.setTranslateY(-30);
        objTransforms.getChildren().add(scaleX);

        // #region scaleX Events

        scaleX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    scaleX.setText(oldValue);
                }
            }
        });

        scaleX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(scaleX.getText().length() == 0)) {
                    float x = Float.valueOf(scaleX.getText());
                    float y = selectedObj.scale().y;
                    float z = selectedObj.scale().z;

                    if (x <= 0.0f) {
                        x = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleX.setText(String.valueOf(selectedObj.scale().x));
            }
        });

        scaleX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(scaleX.getText().length() == 0)) {
                    float x = Float.valueOf(scaleX.getText());
                    float y = selectedObj.scale().y;
                    float z = selectedObj.scale().z;

                    if (x <= 0.0f) {
                        x = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleX.setText(String.valueOf(selectedObj.scale().x));
            }
        });

        // #endregion

        TextField scaleY = new TextField();
        scaleY.getStyleClass().add("y-text");
        scaleY.setMaxSize(60, 15);
        scaleY.setTranslateX(35);
        scaleY.setTranslateY(-30);
        objTransforms.getChildren().add(scaleY);

        // #region scaleY Events

        scaleY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    scaleY.setText(oldValue);
                }
            }
        });

        scaleY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(scaleY.getText().length() == 0)) {
                    float x = selectedObj.scale().x;
                    float y = Float.valueOf(scaleY.getText());
                    float z = selectedObj.scale().z;

                    if (y <= 0.0f) {
                        y = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleY.setText(String.valueOf(selectedObj.scale().y));
            }
        });

        scaleY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(scaleX.getText().length() == 0)) {
                    float x = selectedObj.scale().x;
                    float y = Float.valueOf(scaleY.getText());
                    float z = selectedObj.scale().z;

                    if (y <= 0.0f) {
                        y = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleY.setText(String.valueOf(selectedObj.scale().y));
            }
        });

        // #endregion

        TextField scaleZ = new TextField();
        scaleZ.getStyleClass().add("z-text");
        scaleZ.setMaxSize(60, 15);
        scaleZ.setTranslateX(100);
        scaleZ.setTranslateY(-30);
        objTransforms.getChildren().add(scaleZ);

        // #region scaleZ Events

        scaleZ.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    scaleZ.setText(oldValue);
                }
            }
        });

        scaleZ.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(scaleZ.getText().length() == 0)) {
                    float x = selectedObj.scale().x;
                    float y = selectedObj.scale().y;
                    float z = Float.valueOf(scaleZ.getText());

                    if (z <= 0.0f) {
                        z = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleZ.setText(String.valueOf(selectedObj.scale().z));
            }
        });

        scaleZ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(scaleZ.getText().length() == 0)) {
                    float x = selectedObj.scale().x;
                    float y = selectedObj.scale().y;
                    float z = Float.valueOf(scaleZ.getText());

                    if (z <= 0.0f) {
                        z = 0.001f;
                    }
                    selectedObj.setScale(new vec3(x, y, z));
                }

                scaleZ.setText(String.valueOf(selectedObj.scale().z));
            }
        });

        // #endregion

        // #endregion

        // #region Color

        // #endregion

        Label colorLabel = new Label();
        colorLabel.getStyleClass().add("small-label");
        colorLabel.setText("Color");
        colorLabel.setTranslateY(35);
        colorLabel.setTranslateX(-100);
        objTransforms.getChildren().add(colorLabel);

        ColorPicker rgbPick = new ColorPicker();
        rgbPick.setId("RGBPICK");
        rgbPick.setMaxSize(200, 35);
        rgbPick.setTranslateX(35);
        rgbPick.setTranslateY(35);
        objTransforms.getChildren().add(rgbPick);

        Label specLabel = new Label();
        specLabel.getStyleClass().add("small-label");
        specLabel.setText("Specular");
        specLabel.setTranslateY(85);
        specLabel.setTranslateX(-100);
        objTransforms.getChildren().add(specLabel);

        Slider specularPick = new Slider();
        specularPick.setMaxSize(180, 35);
        specularPick.setTranslateX(35);
        specularPick.setTranslateY(85);
        objTransforms.getChildren().add(specularPick);

        // #endregion

        // #region Physics Panel

        StackPane objPhys = new StackPane();
        objPhys.getStyleClass().add("second-panel");
        objPhys.setMaxSize(300, 485);
        objPhys.setTranslateY(180);
        objTabRoot.getChildren().add(objPhys);

        // #region Velocity

        Label velocityLabel = new Label();
        velocityLabel.getStyleClass().add("small-label");
        velocityLabel.setText("Velocity");
        velocityLabel.setTranslateY(-200);
        velocityLabel.setTranslateX(-100);
        objPhys.getChildren().add(velocityLabel);

        TextField velocityX = new TextField();
        velocityX.getStyleClass().add("x-text");
        velocityX.setMaxSize(60, 15);
        velocityX.setTranslateX(-30);
        velocityX.setTranslateY(-200);
        objPhys.getChildren().add(velocityX);

        TextField velocityY = new TextField();
        velocityY.getStyleClass().add("y-text");
        velocityY.setMaxSize(60, 15);
        velocityY.setTranslateX(35);
        velocityY.setTranslateY(-200);
        objPhys.getChildren().add(velocityY);

        TextField velocityZ = new TextField();
        velocityZ.getStyleClass().add("z-text");
        velocityZ.setMaxSize(60, 15);
        velocityZ.setTranslateX(100);
        velocityZ.setTranslateY(-200);
        objPhys.getChildren().add(velocityZ);

        // #endregion

        // #region Angular Velocity

        Label angularLabel = new Label();
        angularLabel.getStyleClass().add("small-label");
        angularLabel.setText("Angular");
        angularLabel.setTranslateX(-100);
        angularLabel.setTranslateY(-170);
        objPhys.getChildren().add(angularLabel);

        TextField angularX = new TextField();
        angularX.getStyleClass().add("x-text");
        angularX.setMaxSize(60, 15);
        angularX.setTranslateX(-30);
        angularX.setTranslateY(-170);
        objPhys.getChildren().add(angularX);

        TextField angularY = new TextField();
        angularY.getStyleClass().add("y-text");
        angularY.setMaxSize(60, 15);
        angularY.setTranslateX(35);
        angularY.setTranslateY(-170);
        objPhys.getChildren().add(angularY);

        TextField angularZ = new TextField();
        angularZ.getStyleClass().add("z-text");
        angularZ.setMaxSize(60, 15);
        angularZ.setTranslateX(100);
        angularZ.setTranslateY(-170);
        objPhys.getChildren().add(angularZ);

        // #endregion

        Tab objTab = new Tab("Object");

        objTab.setContent(objTabRoot);

        settings.getTabs().add(worldTab);
        // #endregion

        menu.getChildren().add(settings);

        // #endregion

        //#endregion

        //#region Button Actions

        root.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                selectedObj = sc.getSelection();

                if (selectedObj == null) {
                    if (settings.getTabs().contains(objTab)) {
                        settings.getTabs().remove(objTab);
                    }
                }

                else {
                    if (!settings.getTabs().contains(objTab)) {
                        settings.getTabs().add(objTab);
                    }

                    if (lastObj != selectedObj || selectedObj.changed()) {
                        objectName.setText(selectedObj.name());
                        vec3 loc = selectedObj.center();
                        vec3 rot = selectedObj.rotation();
                        vec3 scl = selectedObj.scale();
                        float[] col = selectedObj.color();
                        locationX.setText(String.valueOf(loc.x));
                        locationY.setText(String.valueOf(loc.y));
                        locationZ.setText(String.valueOf(loc.z));

                        rotationX.setText(String.valueOf(rot.x));
                        rotationY.setText(String.valueOf(rot.y));
                        rotationZ.setText(String.valueOf(rot.z));

                        scaleX.setText(String.valueOf(scl.x));
                        scaleY.setText(String.valueOf(scl.y));
                        scaleZ.setText(String.valueOf(scl.z));

                        rgbPick.setValue(Color.rgb((int) (col[0] * 255), (int) (col[0] * 255), (int) (col[0] * 255)));
                        lastObj = selectedObj;
                        selectedObj.setChanged(false);
                    }
                }
            }
        });

        header.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        header.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton().ordinal() == 1) {
                    stage.setX(event.getScreenX() + xOffset);
                    stage.setY(event.getScreenY() + yOffset);
                }
            }
        });

        logo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sc.clear();
            }
        });

        toggleMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (root.getChildren().contains(menu)) {
                    root.getChildren().remove(menu);
                }

                else {
                    root.getChildren().add(menu);
                }
            }
        });

        toggleWire.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (sc.isWire()) {
                    sc.setIsWire(false);
                }

                else {
                    sc.setIsWire(true);
                }
            }
        });

        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop()
                            .browse(new URI("https://github.com/Christopher-Hosken/ap_physics_engine/wiki"));
                } catch (IOException | URISyntaxException e2) {
                    e2.printStackTrace();
                }
            }
        });

        bug.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop()
                            .browse(new URI("https://github.com/Christopher-Hosken/ap_physics_engine/issues"));
                } catch (IOException | URISyntaxException e2) {
                    e2.printStackTrace();
                }
            }
        });

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                animator.stop();
                stage.close();
            }
        });

        addCube.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sc.addCube();
            }
        });

        addSphere.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                sc.addIcoSphere();
            }
        });

        addLight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("LIGHT ADDED");
            }
        });

        importModel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("MODEL IMPORTED");
            }
        });

        toggleTheme.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (_darkTheme) {
                    stage.getScene().getStylesheets().setAll("assets/light.css");
                }

                else {
                    stage.getScene().getStylesheets().setAll("assets/dark.css");
                }

                _darkTheme = !_darkTheme;
            }
        });
    //#endregion

        Scene scene = new Scene(root, 1728, 972);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.getScene().getStylesheets().setAll("assets/dark.css");
        stage.show();
    }

}