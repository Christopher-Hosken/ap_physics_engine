package gui;

import engine.SceneCanvas;
import engine.EmptyObj;
import engine.vec3;

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

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.*;

public class Gui extends Application {
    Color lineColor = new Color(0.4, 0.4, 0.4, 1);
    private double xOffset, yOffset;
    private EmptyObj selectedObj = null;
    private EmptyObj lastObj = null;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        //#region Root Panel

        stage.setTitle("AP Physics Engine");
        stage.getIcons().add(new Image("assets/images/icons/atom.png"));

        StackPane root = new StackPane();
        root.setId("ROOTNODE");
        Rectangle rect = new Rectangle(1728, 972);
        rect.setArcHeight(25);
        rect.setArcWidth(25);
        root.setClip(rect);

        //#endregion

        //#region Opengl Window

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

        // #region Header Bar

        StackPane header = new StackPane();
        header.setId("HEADER");
        header.setTranslateY(-436);
        header.setMaxHeight(100);
        root.getChildren().add(header);

        Line hLine = new Line();
        hLine.setStartX(-900);
        hLine.setEndX(900);
        hLine.setTranslateY(50);
        hLine.setStroke(lineColor);
        header.getChildren().add(hLine);

        Button logo = new Button();
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

        Button addCube = new Button();
        addCube.getStyleClass().add("icon-button");
        addCube.setId("ADDCUBE");
        addCube.setMaxHeight(70);
        addCube.setMaxWidth(70);
        addCube.setTranslateX(-160);
        header.getChildren().add(addCube);

        ToggleButton toggleWire = new ToggleButton();
        toggleWire.getStyleClass().add("icon-button");
        toggleWire.setId("TOGWIRE");
        toggleWire.setMaxHeight(70);
        toggleWire.setMaxWidth(70);
        toggleWire.setTranslateX(-50);
        header.getChildren().add(toggleWire);
        
        Line vLine3 = new Line();
        vLine3.setStartY(0);
        vLine3.setEndY(100);
        vLine3.setTranslateX(10);
        vLine3.setStroke(lineColor);
        header.getChildren().add(vLine3);

        Line vLine4 = new Line();
        vLine4.setStartY(0);
        vLine4.setEndY(100);
        vLine4.setTranslateX(600);
        vLine4.setStroke(lineColor);
        header.getChildren().add(vLine4);

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

        Tab worldTab = new Tab("Scene");

        StackPane worldTabRoot = new StackPane();
        worldTab.setContent(worldTabRoot);

        Label worldTitle = new Label();
        worldTitle.setText("Scene");
        worldTitle.getStyleClass().add("panel-title");
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

        Label focalLabel = new Label();
        focalLabel.getStyleClass().add("small-label");
        focalLabel.setText("Focal Length");
        focalLabel.setTranslateX(-90);
        focalLabel.setTranslateY(-90);
        cameraPane.getChildren().add(focalLabel);

        TextField focalField = new TextField();
        focalField.getStyleClass().add("s-text");
        focalField.setMaxSize(60, 15);
        focalField.setText("45.0");
        focalField.setTranslateX(0);
        focalField.setTranslateY(-90);
        cameraPane.getChildren().add(focalField);

        // #region focal field Events

        focalField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    focalField.setText(oldValue);
                }
            }
        });

        focalField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(focalField.getText().length() == 0)) {
                    float f = Float.valueOf(focalField.getText());

                    sc.setFov(f);
                }

                focalField.setText(String.valueOf(sc.getFov()));
            }
        });

        focalField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(focalField.getText().length() == 0)) {
                    float f = Float.valueOf(focalField.getText());

                    sc.setFov(f);
                }

                focalField.setText(String.valueOf(sc.getFov()));
            }
        });

        // #endregion

        Label zfieldLabel = new Label();
        zfieldLabel.setText("Starting Z");
        zfieldLabel.getStyleClass().add("small-label");
        zfieldLabel.setTranslateX(-90);
        zfieldLabel.setTranslateY(-30);
        cameraPane.getChildren().add(zfieldLabel);

        TextField zField = new TextField();
        zField.getStyleClass().add("s-text");
        zField.setText("-5.0");
        zField.setMaxSize(60, 15);
        zField.setTranslateX(0);
        zField.setTranslateY(-30);
        cameraPane.getChildren().add(zField);

        //#region zField events

        zField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    zField.setText(oldValue);
                }
            }
        });

        zField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(zField.getText().length() == 0)) {
                    float z = Float.valueOf(zField.getText());

                    sc.setStartZ(z);
                }

                zField.setText(String.valueOf(sc.getStartZ()));
            }
        });

        zField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(zField.getText().length() == 0)) {
                    float z = Float.valueOf(zField.getText());

                    sc.setStartZ(z);
                }

                zField.setText(String.valueOf(sc.getStartZ()));
            }
        });

        //#endregion

        Label clipLabel = new Label();
        clipLabel.setText("Clipping");
        clipLabel.getStyleClass().add("small-label");
        clipLabel.setTranslateX(-95);
        clipLabel.setTranslateY(30);
        cameraPane.getChildren().add(clipLabel);

        TextField clipNear = new TextField();
        clipNear.getStyleClass().add("s-text");
        clipNear.setMaxSize(60, 15);
        clipNear.setText("1.0");
        clipNear.setTranslateX(0);
        clipNear.setTranslateY(30);
        cameraPane.getChildren().add(clipNear);

        clipNear.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    clipNear.setText(oldValue);
                }
            }
        });

        clipNear.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(clipNear.getText().length() == 0)) {
                    float cn = Float.valueOf(clipNear.getText());

                    sc.setNearClipping(cn);
                }

                clipNear.setText(String.valueOf(sc.getNearClipping()));
            }
        });

        clipNear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(clipNear.getText().length() == 0)) {
                    float cn = Float.valueOf(clipNear.getText());

                    sc.setNearClipping(cn);
                }

                clipNear.setText(String.valueOf(sc.getNearClipping()));
            }
        });

        TextField clipFar = new TextField();
        clipFar.getStyleClass().add("s-text");
        clipFar.setText("100.0");
        clipFar.setMaxSize(60, 15);
        clipFar.setTranslateX(65);
        clipFar.setTranslateY(30);
        cameraPane.getChildren().add(clipFar);

        clipFar.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    clipFar.setText(oldValue);
                }
            }
        });

        clipFar.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(clipFar.getText().length() == 0)) {
                    float cf = Float.valueOf(clipFar.getText());

                    sc.setFarClipping(cf);
                }

                clipFar.setText(String.valueOf(sc.getFarClipping()));
            }
        });

        clipFar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(clipFar.getText().length() == 0)) {
                    float cf = Float.valueOf(clipFar.getText());

                    sc.setFarClipping(cf);
                }

                clipFar.setText(String.valueOf(sc.getFarClipping()));
            }
        });

        Label backColorLabel = new Label();
        backColorLabel.setText("Background");
        backColorLabel.getStyleClass().add("small-label");
        backColorLabel.setTranslateX(-90);
        backColorLabel.setTranslateY(90);
        cameraPane.getChildren().add(backColorLabel);

        ColorPicker backColor = new ColorPicker();
        backColor.setMaxSize(150, 30);
        backColor.setTranslateX(50);
        backColor.setTranslateY(90);
        backColor.setValue(Color.BLACK);
        cameraPane.getChildren().add(backColor);

        backColor.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                    sc.setWorldColor(backColor.getValue());      
            }
        });

        //#endregion

        //#region World Settings
        StackPane worldPane = new StackPane();
        worldPane.setId("OBJPHYSICS");
        worldPane.getStyleClass().add("second-panel");
        worldPane.setMaxSize(300, 485);
        worldPane.setTranslateY(180);
        worldTabRoot.getChildren().add(worldPane);

        Label frameRange = new Label();
        frameRange.setText("Frame Range");
        frameRange.getStyleClass().add("small-label");
        frameRange.setTranslateX(-90);
        frameRange.setTranslateY(-210);
        worldPane.getChildren().add(frameRange);

        TextField frameStart = new TextField();
        frameStart.getStyleClass().add("s-text");
        frameStart.setMaxSize(60, 15);
        frameStart.setText("0");
        frameStart.setTranslateX(0);
        frameStart.setTranslateY(-210);
        worldPane.getChildren().add(frameStart);

        frameStart.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    frameStart.setText(oldValue);
                }
            }
        });

        frameStart.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(frameStart.getText().length() == 0)) {
                    int fs = Integer.valueOf(frameStart.getText());

                    sc.setFrameStart(fs);
                }

                frameStart.setText(String.valueOf(sc.getFrameStart()));
            }
        });

        frameStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(frameStart.getText().length() == 0)) {
                    int fs = Integer.valueOf(frameStart.getText());

                    sc.setFrameStart(fs);
                }

                frameStart.setText(String.valueOf(sc.getFrameStart()));
            }
        });

        TextField frameEnd = new TextField();
        frameEnd.getStyleClass().add("s-text");
        frameEnd.setText("250");
        frameEnd.setMaxSize(60, 15);
        frameEnd.setTranslateX(65);
        frameEnd.setTranslateY(-210);
        worldPane.getChildren().add(frameEnd);

        frameEnd.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*")) {
                    frameEnd.setText(oldValue);
                }
            }
        });

        frameEnd.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {
                if (!(frameEnd.getText().length() == 0)) {
                    int fe = Integer.valueOf(frameEnd.getText());

                    sc.setFrameEnd(fe);
                }

                frameEnd.setText(String.valueOf(sc.getFrameEnd()));
            }
        });

        frameEnd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(frameEnd.getText().length() == 0)) {
                    int fe = Integer.valueOf(frameEnd.getText());

                    sc.setFrameEnd(fe);
                }

                frameEnd.setText(String.valueOf(sc.getFrameEnd()));
            }
        });

        Label timeScaleLabel = new Label();
        timeScaleLabel.getStyleClass().add("small-label");
        timeScaleLabel.setText("Time Scale");
        timeScaleLabel.setTranslateX(-100);
        timeScaleLabel.setTranslateY(-160);
        worldPane.getChildren().add(timeScaleLabel);

        TextField timeScale = new TextField();
        timeScale.getStyleClass().add("s-text");
        timeScale.setMaxSize(60, 15);
        timeScale.setTranslateX(0);
        timeScale.setTranslateY(-160);
        timeScale.setText("1.0");
        worldPane.getChildren().add(timeScale);

        timeScale.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    timeScale.setText(oldValue);
                }
            }
        });

        timeScale.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(timeScale.getText().length() == 0)) {
                    float t = Float.valueOf(timeScale.getText());

                    sc.engine().setTimeScale(t);;
                }

                timeScale.setText(String.valueOf(sc.engine().getTimeScale()));
            }
        });

        timeScale.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (!(timeScale.getText().length() == 0)) {
                    float t = Float.valueOf(timeScale.getText());

                    sc.engine().setTimeScale(t);
                }

                timeScale.setText(String.valueOf(sc.engine().getTimeScale()));
            }
        });

        Label gravityLabel = new Label();
        gravityLabel.setText("Gravity");
        gravityLabel.getStyleClass().add("small-label");
        gravityLabel.setTranslateX(-110);
        gravityLabel.setTranslateY(-110);
        worldPane.getChildren().add(gravityLabel);

        TextField gravity = new TextField();
        gravity.getStyleClass().add("s-text");
        gravity.setMaxSize(60, 15);
        gravity.setTranslateX(0);
        gravity.setText("-9.8");
        gravity.setTranslateY(-110);
        worldPane.getChildren().add(gravity);

        gravity.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    gravity.setText(oldValue);
                }
            }
        });

        gravity.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (gravity.getText().length() == 1 && gravity.getText().contains("-")) {
                    gravity.setText(String.valueOf(-sc.engine().gravity()));
                }

                if (!(gravity.getText().length() == 0)) {
                    float g = Float.valueOf(gravity.getText());

                    sc.engine().setGravity(g);
                }

                gravity.setText(String.valueOf(sc.engine().gravity()));
            }
        });

        gravity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (gravity.getText().length() == 1 && gravity.getText().contains("-")) {
                    gravity.setText(String.valueOf(-sc.engine().gravity()));
                }

                if (!(gravity.getText().length() == 0)) {
                    float g = Float.valueOf(gravity.getText());

                    sc.engine().setGravity(g);
                }

                gravity.setText(String.valueOf(sc.engine().gravity()));
            }
        });

        //#region

        //#endregion

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

                    selectedObj.setLocation(new vec3(x, y, z));
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

                    selectedObj.setLocation(new vec3(x, y, z));
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

                    selectedObj.setLocation(new vec3(x, y, z));
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

                    selectedObj.setLocation(new vec3(x, y, z));
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

                    selectedObj.setLocation(new vec3(x, y, z));
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

                    selectedObj.setLocation(new vec3(x, y, z));
                }

                locationZ.setText(String.valueOf(selectedObj.center().z));
            }
        });

        // #endregion

        // #endregion
        
        // #region Scales

        Label scaleLabel = new Label();
        scaleLabel.getStyleClass().add("small-label");
        scaleLabel.setText("Scale");
        scaleLabel.setTranslateX(-100);
        scaleLabel.setTranslateY(-50);
        objTransforms.getChildren().add(scaleLabel);

        TextField scaleX = new TextField();
        scaleX.getStyleClass().add("x-text");
        scaleX.setMaxSize(60, 15);
        scaleX.setTranslateX(-30);
        scaleX.setTranslateY(-50);
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
        scaleY.setTranslateY(-50);
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
        scaleZ.setTranslateY(-50);
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
        colorLabel.setTranslateX(-100);
        colorLabel.setTranslateY(15);
        objTransforms.getChildren().add(colorLabel);

        ColorPicker rgbPick = new ColorPicker();
        rgbPick.setId("RGBPICK");
        rgbPick.setMaxSize(200, 35);
        rgbPick.setTranslateX(35);
        rgbPick.setTranslateY(15);
        rgbPick.setValue(Color.rgb(128, 128, 128));
        objTransforms.getChildren().add(rgbPick);

        rgbPick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                    selectedObj.setColor(rgbPick.getValue());      
            }
        });

        ToggleButton origToggle = new ToggleButton();
        origToggle.setText("Show Origin");
        origToggle.getStyleClass().add("push-button");
        origToggle.setMaxSize(120, 30);
        origToggle.setTranslateX(-70);
        origToggle.setTranslateY(90);
        objTransforms.getChildren().add(origToggle);

        origToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (origToggle.isSelected()) {
                    selectedObj.showOrigins = true;
                }

                else {
                    selectedObj.showOrigins = false;
                }
            }
        });

        ToggleButton normToggle = new ToggleButton();
        normToggle.setText("Normals");
        normToggle.getStyleClass().add("push-button");
        normToggle.setMaxSize(120, 30);
        normToggle.setTranslateX(70);
        normToggle.setTranslateY(90);
        objTransforms.getChildren().add(normToggle);

        normToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (normToggle.isSelected()) {
                    selectedObj.drawNormals = true;
                }

                else {
                    selectedObj.drawNormals = false;
                }
            }
        });

        // #endregion

        // #region Physics Panel

        StackPane objPhys = new StackPane();
        objPhys.getStyleClass().add("second-panel");
        objPhys.setMaxSize(300, 485);
        objPhys.setTranslateY(180);
        objTabRoot.getChildren().add(objPhys);

        StackPane velPane = new StackPane();
        velPane.getStyleClass().add("second-panel");

        velPane.setMaxSize(280, 90);
        velPane.setTranslateY(-190);
        objPhys.getChildren().add(velPane);

        ToggleButton togActive = new ToggleButton();
        togActive.setText("Active");
        togActive.getStyleClass().add("push-button");
        togActive.setMaxSize(260, 50);
        togActive.setTranslateY(-5);

        velPane.getChildren().add(togActive);

        // #region Velocity

        Label massLabel = new Label();
        massLabel.getStyleClass().add("small-label");
        massLabel.setText("Mass");
        massLabel.setTranslateX(-100);
        massLabel.setTranslateY(-20);

        TextField mass = new TextField();
        mass.getStyleClass().add("s-text");
        mass.setMaxSize(60, 15);
        mass.setTranslateX(-30);
        mass.setTranslateY(-20);
        
        mass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d*([\\.]\\d*)?")) {
                    mass.setText(oldValue);
                }
            }
        });

        mass.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (!(mass.getText().length() == 0)) {
                    float m = Float.valueOf(mass.getText());

                    if (m < 0.001) {
                        m = 0.001f;
                    }

                    selectedObj.setMass(m);
                }

                mass.setText(String.valueOf(selectedObj.mass()));
            }
        });

        mass.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!(mass.getText().length() == 0)) {
                    float m = Float.valueOf(mass.getText());

                    if (m < 0.001) {
                        m = 0.001f;
                    }

                    selectedObj.setMass(m);
                }

                mass.setText(String.valueOf(selectedObj.mass()));
            }
        });

        Label velocityLabel = new Label();
        velocityLabel.getStyleClass().add("small-label");
        velocityLabel.setText("Velocity");
        velocityLabel.setTranslateX(-100);
        velocityLabel.setTranslateY(30);

        TextField velocityX = new TextField();
        velocityX.getStyleClass().add("x-text");
        velocityX.setMaxSize(60, 15);
        velocityX.setTranslateX(-30);
        velocityX.setTranslateY(30);
        
        velocityX.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    velocityX.setText(oldValue);
                }
            }
        });

        velocityX.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (velocityX.getText().length() == 1 && locationX.getText().contains("-")) {
                    velocityX.setText(String.valueOf(-selectedObj.velocity().x));
                }

                if (!(velocityX.getText().length() == 0)) {
                    float x = Float.valueOf(velocityX.getText());
                    float y = selectedObj.velocity().y;
                    float z = selectedObj.velocity().z;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityX.setText(String.valueOf(selectedObj.velocity().x));
            }
        });

        velocityX.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (velocityX.getText().length() == 1 && velocityX.getText().contains("-")) {
                    velocityX.setText(String.valueOf(-selectedObj.velocity().x));
                }

                if (!(velocityX.getText().length() == 0)) {
                    float x = Float.valueOf(velocityX.getText());
                    float y = selectedObj.velocity().y;
                    float z = selectedObj.velocity().z;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityX.setText(String.valueOf(selectedObj.velocity().x));
            }
        });

        TextField velocityY = new TextField();
        velocityY.getStyleClass().add("y-text");
        velocityY.setMaxSize(60, 15);
        velocityY.setTranslateX(35);
        velocityY.setTranslateY(30);
        
        velocityY.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    velocityY.setText(oldValue);
                }
            }
        });

        velocityY.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (velocityY.getText().length() == 1 && locationY.getText().contains("-")) {
                    velocityY.setText(String.valueOf(-selectedObj.velocity().y));
                }

                if (!(velocityY.getText().length() == 0)) {
                    float y = Float.valueOf(velocityY.getText());
                    float x = selectedObj.velocity().x;
                    float z = selectedObj.velocity().z;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityY.setText(String.valueOf(selectedObj.velocity().y));
            }
        });

        velocityY.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (velocityY.getText().length() == 1 && velocityY.getText().contains("-")) {
                    velocityY.setText(String.valueOf(-selectedObj.velocity().y));
                }

                if (!(velocityY.getText().length() == 0)) {
                    float y = Float.valueOf(velocityY.getText());
                    float x = selectedObj.velocity().x;
                    float z = selectedObj.velocity().z;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityY.setText(String.valueOf(selectedObj.velocity().y));
            }
        });

        TextField velocityZ = new TextField();
        velocityZ.getStyleClass().add("z-text");
        velocityZ.setMaxSize(60, 15);
        velocityZ.setTranslateX(100);
        velocityZ.setTranslateY(30);
        
        velocityZ.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("(-)?\\d*([\\.]\\d*)?")) {
                    velocityZ.setText(oldValue);
                }
            }
        });

        velocityZ.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ar0, Boolean oldValue, Boolean newValue) {

                if (velocityZ.getText().length() == 1 && locationZ.getText().contains("-")) {
                    velocityZ.setText(String.valueOf(-selectedObj.center().z));
                }

                if (!(velocityY.getText().length() == 0)) {
                    float y = Float.valueOf(velocityY.getText());
                    float x = selectedObj.velocity().x;
                    float z = selectedObj.velocity().z;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityZ.setText(String.valueOf(selectedObj.velocity().z));
            }
        });

        velocityZ.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                if (velocityZ.getText().length() == 1 && velocityY.getText().contains("-")) {
                    velocityZ.setText(String.valueOf(-selectedObj.velocity().z));
                }

                if (!(velocityY.getText().length() == 0)) {
                    float z = Float.valueOf(velocityZ.getText());
                    float x = selectedObj.velocity().x;
                    float y = selectedObj.velocity().y;

                    selectedObj.setVelocity(new vec3(x, y, z));
                }

                velocityZ.setText(String.valueOf(selectedObj.velocity().z));
            }
        });

        // #endregion

        ToggleButton velToggle = new ToggleButton();
        velToggle.setText("Velocity");
        velToggle.getStyleClass().add("push-button");
        velToggle.setMaxSize(120, 30);
        velToggle.setTranslateX(-70);
        velToggle.setTranslateY(90);

        ToggleButton accToggle = new ToggleButton();
        accToggle.setText("Acceleration");
        accToggle.getStyleClass().add("push-button");
        accToggle.setMaxSize(120, 30);
        accToggle.setTranslateX(70);
        accToggle.setTranslateY(90);

        velToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (velToggle.isSelected()) {
                    selectedObj.drawVelocity = true;
                }

                else {
                    selectedObj.drawVelocity = false;
                }
            }
        });

        accToggle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (accToggle.isSelected()) {
                    selectedObj.drawAcc = true;
                }

                else {
                    selectedObj.drawAcc = false;
                }
            }
        });

        togActive.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (togActive.isSelected()) {
                    selectedObj.setStatic(false);
                    velPane.setMaxSize(280, 250);
                    velPane.setTranslateY(-110);
                    togActive.setTranslateY(-85);
                    velPane.getChildren().add(massLabel);
                    velPane.getChildren().add(mass);
                    velPane.getChildren().add(velocityLabel);
                    velPane.getChildren().add(velocityX);
                    velPane.getChildren().add(velocityY);
                    velPane.getChildren().add(velocityZ);
                    velPane.getChildren().add(velToggle);
                    velPane.getChildren().add(accToggle);

                }

                else {
                    selectedObj.setStatic(true);
                    velPane.setMaxSize(280, 90);
                    velPane.setTranslateY(-190);
                    togActive.setTranslateY(-5);
                    selectedObj.drawAcc = false;
                    selectedObj.drawVelocity = false;
                    velPane.getChildren().remove(massLabel);
                    velPane.getChildren().remove(mass);
                    velPane.getChildren().remove(velocityLabel);
                    velPane.getChildren().remove(velocityX);
                    velPane.getChildren().remove(velocityY);
                    velPane.getChildren().remove(velocityZ);
                    velPane.getChildren().remove(velToggle);
                    velPane.getChildren().remove(accToggle);
                }
            }
        });

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
                        vec3 scl = selectedObj.scale();
                        vec3 vel = selectedObj.velocity();

                        float[] col = selectedObj.color();
                        locationX.setText(String.valueOf(loc.x));
                        locationY.setText(String.valueOf(loc.y));
                        locationZ.setText(String.valueOf(loc.z));

                        scaleX.setText(String.valueOf(scl.x));
                        scaleY.setText(String.valueOf(scl.y));
                        scaleZ.setText(String.valueOf(scl.z));

                        mass.setText(String.valueOf(selectedObj.mass()));
                        velocityX.setText(String.valueOf(vel.x));
                        velocityY.setText(String.valueOf(vel.x));
                        velocityZ.setText(String.valueOf(vel.x));

                        togActive.setSelected(!selectedObj.isStatic());

                        normToggle.setSelected(selectedObj.drawNormals);
                        origToggle.setSelected(selectedObj.showOrigins);

                        velToggle.setSelected(selectedObj.drawVelocity);
                        accToggle.setSelected(selectedObj.drawAcc);

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

    //#endregion

        Scene scene = new Scene(root, 1728, 972);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);

        stage.getScene().getStylesheets().setAll("assets/dark.css");
        stage.show();
    }
    //YEP SOCKERS
}
