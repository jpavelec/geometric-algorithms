package cz.muni.fi.pa093;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class Main extends Application {
    
    private List<Point> points = new ArrayList<>();
    
    private Canvas canvas = new Canvas(800,600);
    
    private static final int POINT_RADIUS = 4;
    private static final Color POINT_COLOR = Color.BLUE;
    private static final Color CANVAS_COLOR = Color.WHITE;
    private static final Color LINE_COLOR = Color.GREY;
    
    
    @Override
    public void start(Stage stage) {

        HBox root = new HBox();
        root.setSpacing(5);
        root.setPadding(new Insets(10));
        
        Group group = new Group(canvas);
        ToggleGroup radioButtonsAddDelete = new ToggleGroup();
                
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));
        RadioButton radioButtonAdd = new RadioButton("Add point");
        radioButtonAdd.setTooltip(new Tooltip("When is selected you may create " +
                                              "new point by clicking on canvas"));
        radioButtonAdd.setSelected(true);
        radioButtonAdd.setToggleGroup(radioButtonsAddDelete);
        RadioButton radioButtonDelete = new RadioButton("Delete");
        radioButtonDelete.setTooltip(new Tooltip("When is selected you may delete "+
                                                 "existing point by clicking on it"));
        radioButtonDelete.setToggleGroup(radioButtonsAddDelete);
        Button buttonClear = new Button("Clear");
        //buttonClear.setShape(new Circle(35));
        buttonClear.setTooltip(new Tooltip("Clear canvas"));
        Button buttonGenerate = new Button("Generate 5\nrandom points");
        Button buttonGW = new Button("Gift wrapping");
        Button buttonGraham = new Button("Graham scan");
        Button buttonTrian = new Button("Triangulation");
        Button buttonKD = new Button("K-D tree");
        Button buttonDelaunay = new Button("Delaunay");
        Button buttonVeronoi = new Button("Veronoi");
        vbox.getChildren().addAll(  radioButtonAdd,
                                    radioButtonDelete,
                                    new Separator(),
                                    buttonClear,
                                    buttonGenerate,
                                    new Separator(),
                                    buttonGW,
                                    buttonGraham,
                                    buttonTrian,
                                    buttonKD,
                                    buttonDelaunay,
                                    buttonVeronoi);
        
        buttonClear.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                points.clear();
                clearCanvas();
            }
            
        });
        
        buttonGenerate.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                generateRandomPoints(5);
                drawPoints();
            }
            
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (radioButtonAdd.isSelected()) {
                    points.add(new Point((float)event.getX(), (float)event.getY()));
                    drawPoints();
                }
                if (radioButtonDelete.isSelected()) {
                    Iterator<Point> it = points.iterator();
                    while (it.hasNext()) {
                        Point p = it.next();
                        if (event.getX()>p.getX()-POINT_RADIUS && event.getX()<p.getX()+POINT_RADIUS &&
                            event.getY()>p.getY()-POINT_RADIUS && event.getY()<p.getY()+POINT_RADIUS) {
                            points.remove(p);
                            break;
                        }
                    }
                    drawPoints();
                }
            }
        });
        
        buttonGW.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        buttonGraham.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        buttonTrian.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        buttonKD.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        buttonDelaunay.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        buttonVeronoi.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                notSupported();
            }
            
        });
        
        generateRandomPoints(15);
        drawPoints();
        
        root.getChildren().add(vbox);
        root.getChildren().add(group);
        
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void drawPoints() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        clearCanvas();
        gc.setFill(POINT_COLOR);
        for (Point p : points) {
            gc.fillOval(p.getX()-POINT_RADIUS, p.getY()-POINT_RADIUS, 2*POINT_RADIUS, 2*POINT_RADIUS);
        }
    }
    
    private void clearCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(CANVAS_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }
    
    private void generateRandomPoints(int countOfPoints) {
        Random random = new Random(System.currentTimeMillis());
        for (int i=0;i<countOfPoints;i++) {
            points.add(new Point(50+random.nextFloat()*(float)(canvas.getWidth()-100),
                                 50+random.nextFloat()*(float)(canvas.getHeight()-100)));
        }
    }
    
    //TODO
    private void notSupported() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeText("This should not happen", canvas.getWidth()/2, canvas.getHeight()/2);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
